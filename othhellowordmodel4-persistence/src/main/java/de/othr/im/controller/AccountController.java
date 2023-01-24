package de.othr.im.controller;

import de.othr.im.model.*;
import de.othr.im.repository.AccountRepository;
import de.othr.im.repository.CorporateRepository;
import de.othr.im.repository.StudentProfessorRepository;
import de.othr.im.repository.TransferRepository;
import de.othr.im.util.I18nFunctions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
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
    public String account(Model model, HttpServletRequest request) {
        StudentProfessor user = (StudentProfessor) request.getSession().getAttribute("studentSession");
        List<MoneyTransfer> transfers = getAffiliatedTransactions(user);
        List<String> sender = new ArrayList<>(), receiver = new ArrayList<>(), date = new ArrayList<>();

        for(MoneyTransfer m : transfers) {
            sender.add(convertName(m.getSender()));
            receiver.add(convertName(m.getReceiver()));
            date.add(I18nFunctions.localizeDate(m.getDate(), LocaleContextHolder.getLocale()));
        }
        getWeather(request);
        model.addAttribute("date", date);
        model.addAttribute("transfers", transfers);
        model.addAttribute("sender", sender);
        model.addAttribute("receiver", receiver);
        return "/account/account";
    }

    private List<MoneyTransfer> getAffiliatedTransactions(StudentProfessor user) {
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

    private void getWeather(HttpServletRequest request) {
        String key = "d4d77f62e03de4155aeda1baf768bd49";
        String city = "Regensburg";
        String uri = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=" + key;

        RestTemplate rT = new RestTemplate();
        String result = rT.getForObject(uri, String.class);
        // System.out.print(result);
        JSONObject jo = new JSONObject(result);
        JSONObject wetObj = jo.getJSONArray("weather").getJSONObject(0);
        String wet = wetObj.getString("main");
        //System.out.println(wet);
        switch (wet) {
            case "Clouds":
                wet="http://openweathermap.org/img/wn/02d@2x.png";
                break;
            case "Thunderstorm":
                wet="http://openweathermap.org/img/wn/11d@2x.png";
                break;
            case "Drizzle":
                wet="http://openweathermap.org/img/wn/09d@2x.png";
                break;
            case "Rain":
                wet="http://openweathermap.org/img/wn/10d@2x.png";
                break;
            case "Snow":
                wet="http://openweathermap.org/img/wn/13d@2x.png";
                break;
            case "Clear":
                wet="http://openweathermap.org/img/wn/01d@2x.png";
                break;

            default:
                break;
        }
        request.getSession().setAttribute("wet", wet);
        double temp = jo.getJSONObject("main").getDouble("temp") - 273.15;
        double rf = jo.getJSONObject("main").getDouble("feels_like") - 273.15;
        String tempS = I18nFunctions.localizeTemperature(temp, LocaleContextHolder.getLocale());
        //String tempS = String.format("%.2f", temp);
        String rfS = I18nFunctions.localizeTemperature(rf, LocaleContextHolder.getLocale());
        //String rfS = String.format("%.2f", rf);
        request.getSession().setAttribute("temp", tempS);
        request.getSession().setAttribute("rf", rfS);


    }

}


