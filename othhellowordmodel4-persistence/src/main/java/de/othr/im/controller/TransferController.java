package de.othr.im.controller;


import de.othr.im.model.*;
import de.othr.im.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.Optional;

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

    @PostMapping("/account/{accountId}")
    public void createAccount(@PathVariable Long accountId, @RequestBody Account createAccount) {
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @GetMapping("/account/{accountId}")
    public Account readAccount(@PathVariable Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if(account.isPresent()) {
            return account.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The requested account does not exist");
    }

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

    @DeleteMapping("/account/{accountId}")
    public void deleteAccount(@PathVariable Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if(account.isPresent()) {
            accountRepository.deleteById(accountId);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The requested account does not exist");
    }

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

    @PostMapping("/corp")
    public void createCorporation(@RequestBody CorporateOut createCorporation) {
        Corporate corporate = new Corporate();
        //corporate.setId(createCorporation.getId());
        corporate.setName(createCorporation.getName());
        Account account = new Account();
        account.setValue(createCorporation.getValue());
        corporate.setAccount(account);
        corporateRepository.save(corporate);
    }
    @GetMapping("/corp/{corporateId}")
    public CorporateOut readCorporation(@PathVariable Long corporateId) {
        Optional<Corporate> corporate = corporateRepository.findById(corporateId);
        if(corporate.isPresent()) {

            CorporateOut out = new CorporateOut(corporate.get().getId(), corporate.get().getName(), corporate.get().getAccount().getValue());
            return out;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The requested Corporation does not exist");
    }

    @PutMapping("/corp/{corporateId}")
    public void updateCorporation(@PathVariable Long corporateId, @RequestBody CorporateOut updateCorporation) {
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
    }

    @DeleteMapping("/corp/{corporateId}")
    public void deleteCorporation(@PathVariable Long corporateId) {
        Optional<Corporate> corporate = corporateRepository.findById(corporateId);
        if(corporate.isPresent()) {
            Account account = corporate.get().getAccount();
            accountRepository.deleteById(account.getId());
            corporateRepository.deleteById(corporateId);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The requested Corporation does not exist");
    }

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

    @PostMapping("/transfer")
    public void createTransfer(@RequestBody TransferForm transferForm) {
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
    }

}
