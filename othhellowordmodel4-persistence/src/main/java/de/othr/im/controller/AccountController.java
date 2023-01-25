package de.othr.im.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import de.othr.im.model.*;
import de.othr.im.repository.AccountRepository;
import de.othr.im.repository.CorporateRepository;
import de.othr.im.repository.StudentProfessorRepository;
import de.othr.im.repository.TransferRepository;
import de.othr.im.service.PaypalService;
import de.othr.im.util.I18nFunctions;
import de.othr.im.util.PaypalOrder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AccountController {

    @Autowired
    PaypalService service;
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
        DecimalFormat df = new DecimalFormat("0.00");
        model.addAttribute("credit", df.format(user.getAccount().getValue()));
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
        model.addAttribute("order", new PaypalOrder(0.00, "UniPay"));
        return "/account/account";
    }

    @PostMapping("/paypal")
    public String payment(@ModelAttribute("order") PaypalOrder order) {
        try {
            Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
                    order.getIntent(), order.getDescription(), "http://localhost:8080/charge-failed",
                    "http://localhost:8080/charge-successfull");
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return "redirect:"+link.getHref();
                }
            }

        } catch (PayPalRESTException e) {

            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/charge-failed")
    public String cancelPay(HttpServletRequest request) {
        getWeather(request);
        return "/account/charge-failed";
    }

    @GetMapping("/charge-successfull")
    public String successfullPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, HttpServletRequest request) {
        try {
            Payment payment = service.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                //find current user
                StudentProfessor currentUser = (StudentProfessor) request.getSession().getAttribute("studentSession");
                Account receiver = currentUser.getAccount();

                //find changed amount
                double amount = Double.parseDouble(payment.getTransactions().get(0).getAmount().getTotal());

                //change credit
                receiver.addValue(amount);

                //save changes
                accountRepository.save(receiver);

                //for transfer entry find paypal corporate account
                Corporate paypal = corporateRepository.findByName("Paypal Recharge").get();

                //create transfer entry
                MoneyTransfer transfer = new MoneyTransfer();
                transfer.setAmount(amount);
                transfer.setSender(paypal.getAccount());
                transfer.setReceiver(receiver);
                transfer.setDate(new Timestamp(System.currentTimeMillis()));

                transferRepository.save(transfer);
                return "redirect:/charge-success";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/charge-success";
    }

    @GetMapping("/charge-success")
    public String successPay(HttpServletRequest request) {
        getWeather(request);
        return "/account/charge-successful";
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


