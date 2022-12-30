package de.othr.im.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import de.othr.im.model.*;
import de.othr.im.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Controller
public class HomeController {
	
	
	 @Autowired
	 private JavaMailSender javaMailSender;


    @Autowired
    UserRepository userRepository;

    @Autowired
    ManagerRepository managerRepository;


    @Autowired
    StudentProfessorRepository studentProfessorRepository;

   // @Autowired
   // StudentRepository studentRepository;
    @Autowired
    FriendRepository friendRepository;
    @Autowired
    TransferRepository transferRepository;

    @RequestMapping(value = {"/home", "/"})
    public ModelAndView home(HttpServletRequest request, Principal principal) {


        ModelAndView mv = new ModelAndView();

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        String myAuthorities = authorities.toString();


        //searching in the database by the Matrikelnummer...
        Optional<User> oLoggedUser = userRepository.findUserByMatrikelnummer(Integer.parseInt(principal.getName()));

        if (myAuthorities.contains("MANAGER")) {

            Optional<Manager> oManager;

            oManager = managerRepository.findManagerByIdUser(oLoggedUser.get().getId());

            request.getSession().setAttribute("managerSession", oManager.get());

            mv.setViewName("/manager");
            return mv;

        }
        if (myAuthorities.contains("STUDENT")) {
            Optional<StudentProfessor> optionalStudentRepository;

            optionalStudentRepository = studentProfessorRepository.findStudentByIdUser(oLoggedUser.get().getId());
            request.getSession().setAttribute("studentSession", optionalStudentRepository.get());
            mv.setViewName("/student");
            return mv;

        } else {
            mv.setViewName("/login");
            return mv;
        }
    }


    @GetMapping("/mainpage")
    public String mainpage(Model model) {
        // model.addAttribute("registration");
        return "mainpage";
    }

    @GetMapping("/activity")
    public String activity(Model model) {
        // model.addAttribute("registration");
        return "activity";
    }

    @RequestMapping("/friends")
    
    public String friends(Model model, HttpServletRequest request) {
        
    	
    	//Current User
    	StudentProfessor curUser = (StudentProfessor)request.getSession().getAttribute("studentSession");
    	System.out.println(curUser.getKontostand());

        List<Friend> currFriends = friendRepository.findByuserId(Long.valueOf(curUser.getUser().getId()));//1
        if (currFriends.isEmpty()) {
        	currFriends = friendRepository.findByfriendId(Long.valueOf(curUser.getUser().getId()));
        }
        //List<Student> studFriends = new ArrayList<Student>();
        List<User> studFriends = new ArrayList<User>();
        {
        }
        ;
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
        
    	StudentProfessor curUser = (StudentProfessor)request.getSession().getAttribute("studentSession");
    	
    	
        //model.addAttribute("students", studentRepository.findByNameContaining(friend.getName()));
    	model.addAttribute("students", userRepository.findByNameContaining(friend.getName()));
    	
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


        model.addAttribute("friends", friends);
        model.addAttribute("studfriends", studFriends);

        return "/friendSearch";
    }

    @RequestMapping(value = "/addFriend/{id}")
    public ModelAndView addFriend(@PathVariable("id") Long id, Model model, HttpServletRequest request,
                                  RedirectAttributes attributes) {
    	
    	StudentProfessor curUser = (StudentProfessor)request.getSession().getAttribute("studentSession");
    	
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
            
            
            SimpleMailMessage msgAddFriend = new SimpleMailMessage();
            msgAddFriend.setTo(curUser.getUser().getEmail());
           // System.out.println(friend.get().getUser().getName());
            msgAddFriend.setSubject( "UniPays INFO: Sie haben einen Freund hinzugefügt");
            msgAddFriend.setText("Sie haben "+friend.get().getUser().getNachname()+" " +friend.get().getUser().getNachname()+"als Freund hinzugefügt");
           
            javaMailSender.send(msgAddFriend);
            

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
    	
    	
    	StudentProfessor curUser = (StudentProfessor)request.getSession().getAttribute("studentSession");

    	//sendEmail();
    	
        long deletedFriends = friendRepository.deleteByuserIdAndFriendId(Long.valueOf(curUser.getUser().getId()), id);
        
        long deletedFriendsS = friendRepository.deleteByFriendIdAndUserId(Long.valueOf(curUser.getUser().getId()),id);
        
        Optional<StudentProfessor> friend = studentProfessorRepository.findStudentByIdUser(id);
        
        SimpleMailMessage msgRemoveFriend = new SimpleMailMessage();
        msgRemoveFriend.setTo(curUser.getUser().getEmail());
       // System.out.println(friend.get().getUser().getName());
        msgRemoveFriend.setSubject( "UniPays INFO: Sie haben einen Freund entfernt");
        msgRemoveFriend.setText("Sie haben "+friend.get().getUser().getNachname()+" " +friend.get().getUser().getNachname()+"als Freund entfernt");
       
        javaMailSender.send(msgRemoveFriend);
        
        String msg = "Freund entfernt";
        attributes.addFlashAttribute("deleted", msg);

        return new ModelAndView("redirect:/selectFriend");
    }

    @RequestMapping(value = "/sendMoney/{id}")
    public ModelAndView sendMoney(@ModelAttribute(name = "sendForm") MoneyTransfer transfer,
                                  @PathVariable("id") Long id, Model model, BindingResult result, HttpServletRequest request,
                                  RedirectAttributes attributes) {
        // model.addAttribute("transfer", transfer);

    	
    	StudentProfessor currentUser = (StudentProfessor)request.getSession().getAttribute("studentSession");

    	
        List<MoneyTransfer> transactions = transferRepository.findByFrom(Math.toIntExact(currentUser.getUser().getId()));
        System.out.print(transactions);
        List<Integer> directions = new ArrayList<Integer>();
        List<User> recStuds = new ArrayList<User>();
        for (MoneyTransfer m : transactions) {
        	
        	directions.add(1);
        	
            Optional<User> recStud = userRepository.findById(Long.valueOf(m.getTo()));
            User actStud = new User();
            if (recStud.isPresent()) {
                // value is present inside Optional
                actStud = recStud.get();
            }

            recStuds.add(actStud);

        }
        
        
        List<MoneyTransfer> transactionsRecieved = transferRepository.findByTo(Math.toIntExact(currentUser.getUser().getId()));
        
        for (MoneyTransfer m : transactionsRecieved) {
        	
        	directions.add(0);
        	
            Optional<User> recStud = userRepository.findById(Long.valueOf(m.getFrom()));
            User actStud = new User();
            if (recStud.isPresent()) {
                // value is present inside Optional
                actStud = recStud.get();
            }

            recStuds.add(actStud);

        }
        transactions.addAll(transactionsRecieved);
        model.addAttribute("directions", directions);
        model.addAttribute("transactions", transactions);
        model.addAttribute("recStuds", recStuds);
        attributes.addAttribute("transactions", transactions);


        System.out.println(transfer.getAmount());
        //StudentProfessor currentUser = new StudentProfessor();
        //Optional<StudentProfessor> optStudent = studentProfessorRepository.findById(Long.valueOf(currentUser.getUser().getId()));
        double oldKonto = 0;
        if (studentProfessorRepository.findById(Long.valueOf(currentUser.getId())).isPresent()) {
            //currentUser = optStudent.get();
            double availableAmount = currentUser.getKontostand();
            oldKonto = availableAmount;
            model.addAttribute("kontostand", availableAmount);
            if (availableAmount >= transfer.getAmount() && transfer.getAmount() > 0) {
                System.out.print("Updated Kontostand");
                System.out.print(transfer.getAmount());
                System.out.print("+");
                System.out.print(availableAmount);

                currentUser.setKontostand(oldKonto-transfer.getAmount());
                Optional<StudentProfessor> optTargetStudent = studentProfessorRepository.findStudentByIdUser(id);
                transfer.setFrom(Math.toIntExact(currentUser.getUser().getId()));

                StudentProfessor targetStudent = optTargetStudent.get();
                double oldKOntoTarget= targetStudent.getKontostand();
                targetStudent.setKontostand( oldKOntoTarget+transfer.getAmount());
                transfer.setTo(Math.toIntExact(targetStudent.getUser().getId()));
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                transfer.setDate(timestamp);

                transferRepository.save(transfer);
                studentProfessorRepository.save(targetStudent);
                studentProfessorRepository.save(currentUser);
                
                
                SimpleMailMessage msgSend = new SimpleMailMessage();
                msgSend.setTo(currentUser.getUser().getEmail());
               // System.out.println(friend.get().getUser().getName());
                msgSend.setSubject( "UniPays INFO: Sie haben Geld gesendet");
                msgSend.setText("Sie haben "+transfer.getAmount()+" Euro an "+targetStudent.getUser().getNachname()+" " +targetStudent.getUser().getNachname()+"gesendet");
               
                javaMailSender.send(msgSend);
                
                SimpleMailMessage msgRecieved = new SimpleMailMessage();
                msgRecieved.setTo(targetStudent.getUser().getEmail());
               // System.out.println(friend.get().getUser().getName());
                msgRecieved.setSubject( "UniPays INFO: Sie haben Geld empfangen");
                msgRecieved.setText("Sie haben "+transfer.getAmount()+" Euro von "+currentUser.getUser().getNachname()+" " +currentUser.getUser().getNachname()+"empfangen");
               
                javaMailSender.send(msgRecieved);
                


                if (oldKonto != currentUser.getKontostand()) {
                    return new ModelAndView("redirect:/sendMoney");
                }

            }

        }else {
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

    	
    	
    	StudentProfessor curUser = (StudentProfessor)request.getSession().getAttribute("studentSession");
    	
    	

        List<MoneyTransfer> transactions = transferRepository.findByFrom(Math.toIntExact(curUser.getUser().getId()));
        System.out.print(transactions);
        List<Integer> directions = new ArrayList<Integer>();
        List<User> recStuds = new ArrayList<User>();
        for (MoneyTransfer m : transactions) {
        	
        	directions.add(1);
        	
            Optional<User> recStud = userRepository.findById(Long.valueOf(m.getTo()));
            User actStud = new User();
            if (recStud.isPresent()) {
                // value is present inside Optional
                actStud = recStud.get();
            }

            recStuds.add(actStud);

        }
        model.addAttribute("directions", directions);
        model.addAttribute("transactions", transactions);
        model.addAttribute("recStuds", recStuds);
        attributes.addAttribute("transactions", transactions);
        
        
        List<MoneyTransfer> transactionsRecieved = transferRepository.findByTo(Math.toIntExact(curUser.getUser().getId()));
    
        for (MoneyTransfer m : transactionsRecieved) {
        	
        	directions.add(0);
        	
            Optional<User> recStud = userRepository.findById(Long.valueOf(m.getFrom()));
            User actStud = new User();
            if (recStud.isPresent()) {
                // value is present inside Optional
                actStud = recStud.get();
            }

            recStuds.add(actStud);

        }
        transactions.addAll(transactionsRecieved);
        model.addAttribute("directions", directions);
        model.addAttribute("transactions", transactions);
        model.addAttribute("recStuds", recStuds);
        attributes.addAttribute("transactions", transactions);

    	
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

   /* @GetMapping("/studentSearch")
    public String findAll(Model model) {
        // model.addAttribute("registration");

        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        // System.out.println(students.get(0).getName());
        return "students-list";
    }*/
    
   
	
	

}