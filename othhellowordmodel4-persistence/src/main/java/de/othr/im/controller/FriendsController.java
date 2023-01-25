package de.othr.im.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import de.othr.im.util.I18nFunctions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.othr.im.model.Account;
import de.othr.im.model.Corporate;
import de.othr.im.model.Friend;
import de.othr.im.model.HistoricFriendTransfer;
import de.othr.im.model.MoneyTransfer;
import de.othr.im.model.StudentProfessor;
import de.othr.im.model.User;
import de.othr.im.repository.AccountRepository;
import de.othr.im.repository.CorporateRepository;
import de.othr.im.repository.FriendRepository;
import de.othr.im.repository.HistoricFriendTransferRepository;
import de.othr.im.repository.ManagerRepository;
import de.othr.im.repository.StudentProfessorRepository;
import de.othr.im.repository.TransferRepository;
import de.othr.im.repository.UserRepository;
@Controller
public class FriendsController {
	

    @Autowired
    private JavaMailSender javaMailSender;

    
    @Autowired
    HistoricFriendTransferRepository hFRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ManagerRepository managerRepository;


    @Autowired
    StudentProfessorRepository studentProfessorRepository;

    @Autowired
    FriendRepository friendRepository;
    @Autowired
    TransferRepository transferRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CorporateRepository corporateRepository;

	  @RequestMapping("/friends")

	    public String friends(Model model, HttpServletRequest request) {


	        //Current User
	        StudentProfessor curUser = (StudentProfessor) request.getSession().getAttribute("studentSession");
	        System.out.println(curUser.getAccount().getValue());

	        List<Friend> currFriends = friendRepository.findByuserId(Long.valueOf(curUser.getUser().getId()));//1
	        if (currFriends.isEmpty()) {
	            currFriends = friendRepository.findByfriendId(Long.valueOf(curUser.getUser().getId()));
	        }
	        //List<Student> studFriends = new ArrayList<Student>();
	        List<User> studFriends = new ArrayList<User>();
	        {
	        }

	        for (Friend f : currFriends) {

	            //Optional<Student> studF = studentRepository.findById(f.getFriendId());
	            Optional<User> studF = userRepository.findById(f.getFriendId());

	            //Student actStud = new Student();
	            User actStud = new User();
	            if (studF.isPresent()) {
	                // value is present inside Optional
	                actStud = studF.get();
	            }

	            studFriends.add(actStud);

	        }
	       /* for (Student s : studFriends) {
	            System.out.print(s.getName());

	        }*/

	       /*  for (StudentProfessor s : studFriends) {
	             System.out.print(s.getUser().getName());

	         }
	        */


	        model.addAttribute("currfriends", currFriends);
	        model.addAttribute("studfriends", studFriends);
	        return "/friends";
	    }

	    @RequestMapping(value = "/selectFriend")
	    public String searchFriend(@ModelAttribute(name = "friendForm") User friend, Model model, HttpServletRequest request) {

	        StudentProfessor curUser = (StudentProfessor) request.getSession().getAttribute("studentSession");


	        //model.addAttribute("students", studentRepository.findByNameContaining(friend.getName()));
	        List<User> finds =  userRepository.findByNameContaining(friend.getName());
	        List<User> findsNachname =  userRepository.findByNachnameContaining(friend.getNachname());
	        finds.addAll(findsNachname);
	        model.addAttribute("students", finds);

	        List<Friend> friends = friendRepository.findByuserId(Long.valueOf(curUser.getUser().getId()));

	        if (friends.isEmpty()) {
	            friends = friendRepository.findByfriendId(Long.valueOf(curUser.getUser().getId()));
	        }

	        //List<Student> studFriends = new ArrayList<Student>();
	        List<User> studFriends = new ArrayList<User>();
	        {
	        }
	        ;
	        for (Friend f : friends) {

	            //Optional<Student> studF = studentRepository.findById(f.getFriendId());
	            Optional<User> studF = userRepository.findById(f.getFriendId());
	            //Student actStud = new Student();
	            User actStud = new User();
	            if (studF.isPresent()) {
	                // value is present inside Optional
	                actStud = studF.get();
	            }

	            studFriends.add(actStud);

	        }

	        getWeather(request);
	        model.addAttribute("friends", friends);
	        model.addAttribute("studfriends", studFriends);

	        return "/friendSearch";
	    }

	    @RequestMapping(value = "/addFriend/{id}")
	    public ModelAndView addFriend(@PathVariable("id") Long id, Model model, HttpServletRequest request,
	                                  RedirectAttributes attributes) {

	        StudentProfessor curUser = (StudentProfessor) request.getSession().getAttribute("studentSession");

	        if (friendRepository.findByuserIdAndFriendId(curUser.getUser().getId(), id) == null && id != curUser.getUser().getId()) {

	            //Optional<Student> friend = studentRepository.findById(id);

	            Optional<StudentProfessor> friend = studentProfessorRepository.findStudentByIdUser(id);//studentProfessorRepository.findById(id);
	            System.out.println(id);
	            List<Friend> friends = friendRepository.findByuserId(curUser.getUser().getId());
	            if (friends.isEmpty()) {
	                friends = friendRepository.findByfriendId(Long.valueOf(curUser.getUser().getId()));
	            }

	            model.addAttribute("friends", friends);
	            // System.out.println(friends.get(0).getFriendId());
	            // System.out.println(friends.get(1).getFriendId());
	            // System.out.println(friend.get().getName());
	            Friend newFriend = new Friend();
	            newFriend.setUserId(Long.valueOf(curUser.getUser().getId()));
	            newFriend.setFriendId(id);
	            // System.out.print(newFriend.getFriendId());
	            friendRepository.save(newFriend);


	            Friend newFriendReverse = new Friend();
	            newFriendReverse.setFriendId(Long.valueOf(curUser.getUser().getId()));
	            newFriendReverse.setUserId(id);
	            // System.out.print(newFriend.getFriendId());
	            friendRepository.save(newFriendReverse);

	            try {
	            SimpleMailMessage msgAddFriend = new SimpleMailMessage();
	            msgAddFriend.setTo(curUser.getUser().getEmail());
	             System.out.println(friend.get().getUser().getName());
	            msgAddFriend.setSubject("UniPays INFO: Sie haben einen Freund hinzugefügt");
	            msgAddFriend.setText("Sie haben " + friend.get().getUser().getNachname() + " " + friend.get().getUser().getNachname() + "als Freund hinzugefügt");

	            javaMailSender.send(msgAddFriend);
	            }catch (Exception e) {
	            	System.out.print("Email senden fehlgeschlagen");
				}


	            String msg = "Erfolgreich";
	            attributes.addFlashAttribute("success", msg);
	            // model.addAttribute("success",msg);
	            return new ModelAndView("redirect:/selectFriend");
	        } else {

	            String msg = "Bereits vorhanden";
	            attributes.addFlashAttribute("success", msg);
	            // System.out.print("Bereits vorhanden");
	            return new ModelAndView("redirect:/selectFriend");
	        }

	    }

	    @Transactional
	    @RequestMapping(value = "/removeFriend/{id}")
	    public ModelAndView removeFriend(@PathVariable("id") Long id, Model model, HttpServletRequest request,
	                                     RedirectAttributes attributes) {


	        StudentProfessor curUser = (StudentProfessor) request.getSession().getAttribute("studentSession");

	        //sendEmail();

	        long deletedFriends = friendRepository.deleteByuserIdAndFriendId(Long.valueOf(curUser.getUser().getId()), id);

	        long deletedFriendsS = friendRepository.deleteByFriendIdAndUserId(Long.valueOf(curUser.getUser().getId()), id);

	        Optional<StudentProfessor> friend = studentProfessorRepository.findStudentByIdUser(id);
	        try {
	        SimpleMailMessage msgRemoveFriend = new SimpleMailMessage();
	        msgRemoveFriend.setTo(curUser.getUser().getEmail());
	        // System.out.println(friend.get().getUser().getName());
	        msgRemoveFriend.setSubject("UniPays INFO: Sie haben einen Freund entfernt");
	        msgRemoveFriend.setText("Sie haben " + friend.get().getUser().getNachname() + " " + friend.get().getUser().getNachname() + "als Freund entfernt");

	        javaMailSender.send(msgRemoveFriend);
	        }catch (Exception e) {
	        	System.out.print("Email senden fehlgeschlagen");
			}

	        String msg = "Freund entfernt";
	        attributes.addFlashAttribute("deleted", msg);

	        return new ModelAndView("redirect:/selectFriend");
	    }

	    @RequestMapping(value = "/sendMoney/{id}")
	    public ModelAndView sendMoney(@ModelAttribute(name = "sendForm") MoneyTransfer transfer,
	                                  @PathVariable("id") Long id, Model model, BindingResult result, HttpServletRequest request,
	                                  RedirectAttributes attributes) {
	        // model.addAttribute("transfer", transfer);


	        StudentProfessor currentUser = (StudentProfessor) request.getSession().getAttribute("studentSession");
	        //List<MoneyTransfer> transactions = getAffiliatedTransactions(currentUser);
	        List<HistoricFriendTransfer> transactions = hFRepository.findBysenderId(currentUser.getUser().getId()); //Out
	        List<HistoricFriendTransfer> transactionsIn = hFRepository.findByreceiverId(currentUser.getUser().getId());
	        transactions.addAll(transactionsIn);
	        
	        List<String> sender = new ArrayList<>(), receiver = new ArrayList<>(), date = new ArrayList<>();
	        for(HistoricFriendTransfer m : transactions) {
	            //sender.add(convertName(m.getSender()));
	        	sender.add(m.getSender());
	            //receiver.add(convertName(m.getReceiver()));
	        	receiver.add(m.getReceiver());
				date.add(I18nFunctions.localizeDate(m.getDate(), LocaleContextHolder.getLocale()));
	        }
	        List<Integer> directions = new ArrayList<Integer>();
	        for (HistoricFriendTransfer m : transactions) {
	        	if(m.getSenderid() == currentUser.getAccount().getId()) {
	        		//System.out.printf("Sender:%s Current User:%s",m.getSender().getId(),currentUser.getAccount().getId());
	        		directions.add(1);
	        		
	        	}else {
	        		directions.add(0);
	        		
	        	}
	        	
	        }
	        model.addAttribute("directions", directions);
	        getWeather(request);
			model.addAttribute("date", date);
	        model.addAttribute("transactions", transactions);
	        model.addAttribute("sender", sender);
	        model.addAttribute("receiver", receiver);

	        attributes.addAttribute("transactions", transactions);


	        System.out.println(transfer.getAmount());
	        //StudentProfessor currentUser = new StudentProfessor();
	        //Optional<StudentProfessor> optStudent = studentProfessorRepository.findById(Long.valueOf(currentUser.getUser().getId()));
	        double oldKonto = 0;
	        if (studentProfessorRepository.findById(Long.valueOf(currentUser.getId())).isPresent()) {
	            //currentUser = optStudent.get();
	            double availableAmount = currentUser.getAccount().getValue();
	            oldKonto = availableAmount;
	            model.addAttribute("kontostand", availableAmount);
	            if (availableAmount >= transfer.getAmount() && transfer.getAmount() > 0) {
	                System.out.print("Updated Kontostand");
	                System.out.print(transfer.getAmount());
	                System.out.print("+");
	                System.out.print(availableAmount);

	                currentUser.getAccount().setValue(oldKonto - transfer.getAmount());
	                Optional<StudentProfessor> optTargetStudent = studentProfessorRepository.findStudentByIdUser(id);
	                transfer.setSender(currentUser.getAccount());

	                StudentProfessor targetStudent = optTargetStudent.get();
	                double oldKOntoTarget = targetStudent.getAccount().getValue();
	                targetStudent.getAccount().setValue(oldKOntoTarget + transfer.getAmount());
	                transfer.setReceiver(targetStudent.getAccount());
	                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	                transfer.setDate(timestamp);
	                
	                HistoricFriendTransfer newhFTransfer = new HistoricFriendTransfer();
	                long senderid= Long.valueOf(currentUser.getUser().getId());
	                newhFTransfer.setSenderid(senderid);
	                long receiverid= Long.valueOf(targetStudent.getUser().getId());
	                newhFTransfer.setReceiverid(receiverid);
	                newhFTransfer.setSender(currentUser.getUser().getName()+" " + currentUser.getUser().getNachname());
	                newhFTransfer.setReceiver(targetStudent.getUser().getName()+" " + targetStudent.getUser().getNachname());
	                newhFTransfer.setAmount(transfer.getAmount());
	                newhFTransfer.setMessage(transfer.getMessage());
	                newhFTransfer.setDate(timestamp);
	               
	                
	               
	                
	                hFRepository.save(newhFTransfer);
	                transferRepository.save(transfer);
	                studentProfessorRepository.save(targetStudent);
	                studentProfessorRepository.save(currentUser);

	                try {
	                SimpleMailMessage msgSend = new SimpleMailMessage();
	                msgSend.setTo(currentUser.getUser().getEmail());
	                // System.out.println(friend.get().getUser().getName());
	                msgSend.setSubject("UniPays INFO: Sie haben Geld gesendet");
	                msgSend.setText("Sie haben " + transfer.getAmount() + " Euro an " + targetStudent.getUser().getNachname() + " " + targetStudent.getUser().getNachname() + "gesendet");

	                javaMailSender.send(msgSend);

	                SimpleMailMessage msgRecieved = new SimpleMailMessage();
	                msgRecieved.setTo(targetStudent.getUser().getEmail());
	                // System.out.println(friend.get().getUser().getName());
	                msgRecieved.setSubject("UniPays INFO: Sie haben Geld empfangen");
	                msgRecieved.setText("Sie haben " + transfer.getAmount() + " Euro von " + currentUser.getUser().getNachname() + " " + currentUser.getUser().getNachname() + "empfangen");

	                javaMailSender.send(msgRecieved);
	                }catch (Exception e) {
						System.out.print("Email senden fehlgeschlagen");
					}


	                if (oldKonto != currentUser.getAccount().getValue()) {
	                    return new ModelAndView("redirect:/sendMoney");
	                }

	            }

	        } else {
	            System.out.println("Not found");
	        }

	        List<Friend> currFriends = friendRepository.findByuserId(Long.valueOf(currentUser.getUser().getId()));

	        List<User> studFriends = new ArrayList<User>();
	        {
	        }
	        ;
	        for (Friend f : currFriends) {
	            Optional<User> studF = userRepository.findById(f.getFriendId());
	            User actStud = new User();
	            if (studF.isPresent()) {
	                // value is present inside Optional
	                actStud = studF.get();
	            }

	            studFriends.add(actStud);

	        }

	        model.addAttribute("currfriends", currFriends);
	        model.addAttribute("studfriends", studFriends);

	        String msg = "targetfriend";

	        model.addAttribute("targetfriend", msg);

	        return new ModelAndView("/sendToFriend");
	    }

	    @RequestMapping(value = "/sendMoney")
	    public ModelAndView sendMoneywoF(Model model, HttpServletRequest request, RedirectAttributes attributes) {
	        StudentProfessor curUser = (StudentProfessor) request.getSession().getAttribute("studentSession");
	        List<HistoricFriendTransfer> transactions = hFRepository.findBysenderId(curUser.getUser().getId()); //Out
	        List<HistoricFriendTransfer> transactionsIn = hFRepository.findByreceiverId(curUser.getUser().getId());
	        transactions.addAll(transactionsIn);
	        List<String> sender = new ArrayList<>(), receiver = new ArrayList<>(), date = new ArrayList<>();
	        for(HistoricFriendTransfer m : transactions) {
	        	//sender.add(convertName(m.getSender()));
	        	sender.add(m.getSender());
	            //receiver.add(convertName(m.getReceiver()));
	        	receiver.add(m.getReceiver());
				date.add(I18nFunctions.localizeDate(m.getDate(), LocaleContextHolder.getLocale()));
	        }
			model.addAttribute("date", date);
	        model.addAttribute("transactions", transactions);
	        model.addAttribute("sender", sender);
	        model.addAttribute("receiver", receiver);
//	        List<MoneyTransfer> transactions = transferRepository.findByFrom(curUser.getAccount().getId();
//	        System.out.print(transactions);
	        List<Integer> directions = new ArrayList<Integer>();
	        for (HistoricFriendTransfer m : transactions) {
	        	if(m.getSenderid() == curUser.getAccount().getId()) {
	        		//System.out.printf("Sender:%s Current User:%s",m.getSender().getId(),currentUser.getAccount().getId());
	        		directions.add(1);
	        		
	        	}else {
	        		directions.add(0);
	        		
	        	}
	        	
	        }
	        model.addAttribute("directions", directions);
//	        List<User> recStuds = new ArrayList<User>();
//	        for (MoneyTransfer m : transactions) {
	//
//	            directions.add(1);
	//
//	            Optional<User> recStud = userRepository.findById(Long.valueOf(m.getTo()));
//	            User actStud = new User();
//	            if (recStud.isPresent()) {
//	                // value is present inside Optional
//	                actStud = recStud.get();
//	            }
	//
//	            recStuds.add(actStud);
	//
//	        }
//	        model.addAttribute("directions", directions);
//	        model.addAttribute("transactions", transactions);
//	        model.addAttribute("recStuds", recStuds);
//	        attributes.addAttribute("transactions", transactions);
	//
	//
//	        List<MoneyTransfer> transactionsRecieved = transferRepository.findByTo(Math.toIntExact(curUser.getUser().getId()));
	//
//	        for (MoneyTransfer m : transactionsRecieved) {
	//
//	            directions.add(0);
	//
//	            Optional<User> recStud = userRepository.findById(Long.valueOf(m.getFrom()));
//	            User actStud = new User();
//	            if (recStud.isPresent()) {
//	                // value is present inside Optional
//	                actStud = recStud.get();
//	            }
	//
//	            recStuds.add(actStud);
	//
//	        }
//	        transactions.addAll(transactionsRecieved);
//	        model.addAttribute("directions", directions);
//	        model.addAttribute("transactions", transactions);
//	        model.addAttribute("recStuds", recStuds);
//	        attributes.addAttribute("transactions", transactions);


	        List<Friend> currFriends = friendRepository.findByuserId(Long.valueOf(curUser.getUser().getId()));
	        // System.out.print("No Friend recieved");
	        List<User> studFriends = new ArrayList<User>();
	        {
	        }
	        ;
	        for (Friend f : currFriends) {
	            Optional<User> studF = userRepository.findById(f.getFriendId());
	            User actStud = new User();
	            if (studF.isPresent()) {
	                // value is present inside Optional
	                actStud = studF.get();
	            }

	            studFriends.add(actStud);

	        }
	        // for (Student s : studFriends) {
	        // System.out.print(s.getName());

	        // }

	        // System.out.print(currFriends);

	        // System.out.print(currFriends);
	        model.addAttribute("currfriends", currFriends);
	        model.addAttribute("studfriends", studFriends);
	        String msg = "notargetfriend";
	        // attributes.addFlashAttribute("targetfriend", msg);
	        model.addAttribute("targetfriend", msg);
	        return new ModelAndView("/sendToFriend");
	    }


	    private List<MoneyTransfer> getAffiliatedTransactions(StudentProfessor user) {
	        //get the account related to the user
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


	    @GetMapping("/mainpage")
		public String mainpage(Model model, HttpServletRequest request) {
		  getWeather(request);
		  return "mainpage";
		}
	    
	    public void getWeather(HttpServletRequest request) {
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
