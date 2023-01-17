package de.othr.im.controller;

import de.othr.im.model.*;
import de.othr.im.repository.AccountRepository;
import de.othr.im.repository.CorporateRepository;
import de.othr.im.repository.StudentProfessorRepository;
import de.othr.im.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AccountController {

    @Autowired
    TransferRepository transferRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    StudentProfessorRepository studentProfessorRepository;
    @Autowired
    CorporateRepository corporateRepository;


    @RequestMapping("/account")
    public ModelAndView account(Model model, HttpServletRequest request) {
        StudentProfessor user = (StudentProfessor) request.getSession().getAttribute("studentSession");
        List<MoneyTransfer> transfers = getAffiliatedTransactions(user);
        List<String> sender = new ArrayList<String>(), receiver = new ArrayList<String>();
        for(MoneyTransfer m : transfers) {
            sender.add(convertName(m.getFrom()));
            receiver.add(convertName(m.getTo()));
        }
        model.addAttribute("transfers", transfers);
        model.addAttribute("sender", sender);
        model.addAttribute("receiver", receiver);
        return new ModelAndView("/account/account.jsp");
    }

    private List<MoneyTransfer> getAffiliatedTransactions(StudentProfessor user) {
        //get the account related to the user
        //Optional<StudentProfessor> studentProfessor = studentProfessorRepository.findStudentByIdUser(user.getId());
        //if(studentProfessor.isEmpty()) {
        //    return null;
        //}
        Optional<Account> account = accountRepository.findById(user.getAccount().getId());
        if(account.isEmpty()) {
            return null;
        }
        //get inbound Transactions
        List<MoneyTransfer> transfers = transferRepository.findByTo(account.get().getId());
        //get outbound Transactions
        transfers.addAll(transferRepository.findByFrom(account.get().getId()));
        return transfers;
    }

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
