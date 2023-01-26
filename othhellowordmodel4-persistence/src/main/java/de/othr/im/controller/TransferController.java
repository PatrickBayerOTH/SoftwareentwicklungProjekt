package de.othr.im.controller;


import de.othr.im.model.*;
import de.othr.im.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
Outbound API Controller that allows:
- retrieval and adjustments of account information
- retrieval, creation and adjustments of corporate user accounts
- creation of transfers TO corporations
Written by Tobias Mooshofer
 */
@RestController
@RequestMapping("/api")
public class TransferController {
    @Autowired
    TransferRepository transferRepository;
    @Autowired
    StudentProfessorRepository studentProfessorRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CorporateRepository corporateRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    /*
    Creating an account without an associated corporation is currently not allowed
     */
    @PostMapping("/account/{accountId}")
    public void createAccount(@PathVariable Long accountId, @RequestBody Account createAccount) {
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    /*
    Reads the information of the account defined by accountID
    usefull to retrieve a customers current balance
     */
    @GetMapping("/account/{accountId}")
    public Account readAccount(@PathVariable Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if(account.isPresent()) {
            return account.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The requested account does not exist");
    }

    /*
    Updates an account defined by accountId with an Account from the requestbody
    usefull to apply refunds or change account values without creating a transaction
    can currently brick the database, if the new accountID is not the one stated in the request
    */
    @PutMapping("/account/{accountId}")
    public void updateAccount(@PathVariable Long accountId, @RequestBody Account updateAccount) {
        Optional<Account> account = accountRepository.findById(accountId);
        if(account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The requested account does not exist");
        }
        //set the new values transmitted by the request
        Account editAccount = account.get();
        editAccount.setId(updateAccount.getId());
        editAccount.setValue(updateAccount.getValue());
        accountRepository.save(editAccount);
    }

    /*
    Deletes an account without deleting the associated user
    will in most cases brick the database
     */
    @DeleteMapping("/account/{accountId}")
    public void deleteAccount(@PathVariable Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if(account.isPresent()) {
            accountRepository.deleteById(accountId);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The requested account does not exist");
    }

    /*
    DTO class representing a corporate account
    used to resolve the internally handled accountId to its value
     */
    class CorporateOut {
        private long id;
        private String name;

        private double value;
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }



        public CorporateOut(long id, String name, double value) {
            this.id = id;
            this.name = name;
            this.value = value;
        }
    }


    /*
    Creates a new corporation from the specified name
    Returns a representation of the created corporation
     */
    @PostMapping("/corp")
    public CorporateOut createCorporation(@RequestBody String createCorporation) {
        Corporate corporate = new Corporate();
        //corporate.setId(createCorporation.getId());
        corporate.setName(createCorporation);
        Account account = new Account();
        account.setValue(0);
        corporate.setAccount(account);
        corporateRepository.save(corporate);
        CorporateOut corporateOut = new CorporateOut(corporate.getId(), corporate.getName(), corporate.getAccount().getValue());
        return corporateOut;
    }

    /*
    Returns the available information about the corporation specified by corporateID
     */
    @GetMapping("/corp/{corporateId}")
    public CorporateOut readCorporation(@PathVariable Long corporateId) {
        Optional<Corporate> corporate = corporateRepository.findById(corporateId);
        if(corporate.isPresent()) {

            CorporateOut out = new CorporateOut(corporate.get().getId(), corporate.get().getName(), corporate.get().getAccount().getValue());
            return out;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The requested Corporation does not exist");
    }

    /*
    Applies the changes specified by the request body to the corporation specified by corporateId
    Mirrors the updated corporation object back to the client
     */
    @PutMapping("/corp/{corporateId}")
    public CorporateOut updateCorporation(@PathVariable Long corporateId, @RequestBody CorporateOut updateCorporation) {
        Optional<Corporate> corporate = corporateRepository.findById(corporateId);
        if(corporate.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The requested Corporation does not exist");
        }
        //set the new values transmitted by the request
        Corporate editcorporate = corporate.get();
        //editcorporate.setId(updateCorporation.getId());
        editcorporate.setName(updateCorporation.getName());
        editcorporate.getAccount().setValue(updateCorporation.getValue());
        corporateRepository.save(editcorporate);
        return updateCorporation;
    }

    /*
    Removes the corporation defined by corporateID from the system
    Returns a representation of the deleted corporation
     */
    @DeleteMapping("/corp/{corporateId}")
    public CorporateOut deleteCorporation(@PathVariable Long corporateId) {
        Optional<Corporate> corporate = corporateRepository.findById(corporateId);
        if(corporate.isPresent()) {
            Corporate toDelete = corporate.get();
            CorporateOut corporateOut = new CorporateOut(toDelete.getId(), toDelete.getName(), toDelete.getAccount().getValue());
            Account account = corporate.get().getAccount();
            accountRepository.deleteById(account.getId());
            corporateRepository.deleteById(corporateId);
            return corporateOut;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The requested Corporation does not exist");
    }

    /*
    DTO Class used to create a new transaction/transfer
    Specifies an userId as sender, a corporateId as receiver and a value
     */
    class TransferForm {
        private Long from;
        private Long to;
        private double value;

        public TransferForm(Long from, Long to, double value) {
            this.from = from;
            this.to = to;
            this.value = value;
        }

        public Long getFrom() {
            return from;
        }

        public void setFrom(Long from) {
            this.from = from;
        }

        public Long getTo() {
            return to;
        }

        public void setTo(Long to) {
            this.to = to;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }

    /*
    Creates and executes the transfer transmitted in the request body
    Returns a representation of the created transaction
     */
   @PostMapping("/transfer")
    public TransferReport createTransfer(@RequestBody TransferForm transferForm) {
        //get sender object
        Optional<StudentProfessor> studentProfessor = studentProfessorRepository.findStudentByIdUser(transferForm.getFrom());
        if (studentProfessor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "the sender was not found. sender needs to be a student/professor");
        }
        //get receiver object
        Optional<Corporate> corporate = corporateRepository.findById(transferForm.getTo());
        if (corporate.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "the receiver was not found. receiver needs to be a corporation");
        }
        //change sender & receiver account values
        StudentProfessor sender = studentProfessor.get();
        Account senderAccount = sender.getAccount();
        Corporate receiver = corporate.get();
        Account receiverAccount = receiver.getAccount();
        senderAccount.setValue(senderAccount.getValue()-transferForm.getValue());
        receiverAccount.setValue(senderAccount.getValue()+transferForm.getValue());
        //save changes
        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);
        //create transfer entry
        MoneyTransfer transfer = new MoneyTransfer();
        transfer.setAmount((double)transferForm.getValue());
        transfer.setSender(senderAccount);
        transfer.setReceiver(receiverAccount);
        transfer.setDate(new Timestamp(System.currentTimeMillis()));
        transferRepository.save(transfer);
        //send mail to the user
        try {
           SimpleMailMessage msgAddFriend = new SimpleMailMessage();
           msgAddFriend.setTo(sender.getUser().getEmail());
           msgAddFriend.setSubject("UniPays INFO: Sie haben einen Einkauf getätigt!");
           String msg = "Sehr geehrte/r " + sender.getUser().getName() + " " + sender.getUser().getNachname() + " \n";
           msg += "Ihr konto wurde gerade von " + receiver.getName() + " mit einem Betrag von " + transfer.getAmount() + "€ belastet.\n";
           msg += "Ihr verbleibender Kontostand: " + senderAccount.getValue() + "€";
           msgAddFriend.setText(msg);
           javaMailSender.send(msgAddFriend);
        }catch (Exception e) {
           System.out.print("Email senden fehlgeschlagen");
        }

        //create return object
       Actor senderActor = new Actor(senderAccount.getId(), convertName(senderAccount));
       Actor receiverActor = new Actor(receiverAccount.getId(), convertName(receiverAccount));
       TransferReport transferReport = new TransferReport(senderActor, receiverActor, transfer.getAmount(), transfer.getDate());
       return transferReport;
    }

    /*
    Returns a representation of all transactions associated with the specified corporateId
     */
    @GetMapping("/transfer/{corporateId}")
    public TransferList getTransfers(@PathVariable Long corporateId) {
        Optional<Corporate> corporate = corporateRepository.findById(corporateId);
        if(corporate.isPresent()) {
            Corporate client  = corporate.get();
            Account account = client.getAccount();
            List<TransferReport> transfers = new ArrayList<>();
            //create transfer list
            List<MoneyTransfer> base = getAffiliatedTransactions(client);
            for(MoneyTransfer m : base) {
                Actor sender = new Actor(m.getSender().getId(), convertName(m.getSender()));
                Actor receiver = new Actor(m.getReceiver().getId(), convertName(m.getReceiver()));
                TransferReport transferForm = new TransferReport(sender, receiver, m.getAmount(), m.getDate());
                transfers.add(transferForm);
            }

            return new TransferList(client.getId(), client.getName(), transfers);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The requested Corporation does not exist");
    }

    /*
    DTO Class representing a list of transactionReports
     */
    class TransferList {
        long corporation_id;
        String corporation_name;
        List<TransferReport> transfers;

        public long getCorporation_id() {
            return corporation_id;
        }

        public void setCorporation_id(long corporation_id) {
            this.corporation_id = corporation_id;
        }

        public String getCorporation_name() {
            return corporation_name;
        }

        public void setCorporation_name(String corporation_name) {
            this.corporation_name = corporation_name;
        }

        public List<TransferReport> getTransfers() {
            return transfers;
        }

        public void setTransfers(List<TransferReport> transfers) {
            this.transfers = transfers;
        }

        public TransferList(long corporation_id, String corporation_name, List<TransferReport> transfers) {
            this.corporation_id = corporation_id;
            this.corporation_name = corporation_name;
            this.transfers = transfers;
        }
    }

    /*
    DTO Class representing a single recorded transaction
     */
    class TransferReport {

        Actor sender;
        Actor receiver;
        double value;
        Timestamp date;

        public Actor getSender() {
            return sender;
        }

        public void setSender(Actor sender) {
            this.sender = sender;
        }

        public Actor getReceiver() {
            return receiver;
        }

        public void setReceiver(Actor receiver) {
            this.receiver = receiver;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public Timestamp getDate() {
            return date;
        }

        public void setDate(Timestamp date) {
            this.date = date;
        }


        public TransferReport(Actor sender, Actor receiver, double value, Timestamp date) {
            this.sender = sender;
            this.receiver = receiver;
            this.value = value;
            this.date = date;
        }
    }

    /*
    DTO Class representing a transactions actor
    Name and AccountID representation of either a corporation or an user
     */
    class Actor {
        long account_id;
        String name;

        public long getAccount_id() {
            return account_id;
        }

        public void setAccount_id(long account_id) {
            this.account_id = account_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Actor(long account_id, String name) {
            this.account_id = account_id;
            this.name = name;
        }
    }

    /*
    Helper function that finds all Transactions in which user is sender or receiver
    returns a list of transfers
     */
    private List<MoneyTransfer> getAffiliatedTransactions(Corporate user) {
        Optional<Account> accountOptional = accountRepository.findById(user.getAccount().getId());
        if(accountOptional.isEmpty()) {
            return null;
        }
        Account account = accountOptional.get();
        //get inbound Transactions
        List<MoneyTransfer> transfers = transferRepository.findByReceiver(account.getId());
        //get outbound Transactions
        transfers.addAll(transferRepository.findBySender(account.getId()));
        return transfers;
    }

    /*
    Helper function converting an account to the name of its owner
     */
    private String convertName(Account account) {
        //check if account is student
        Optional<StudentProfessor> studentProfessor = studentProfessorRepository.findByAccount(account.getId());
        if(studentProfessor.isPresent()) {
            String out =  studentProfessor.get().getUser().getName() + " " + studentProfessor.get().getUser().getNachname();
            return out;
        }
        Optional<Corporate> corporate = corporateRepository.findByAccount(account.getId());
        if(corporate.isPresent()) {
            return corporate.get().getName();
        }
        return "";
    }

}
