package de.othr.im.controller;

import de.othr.im.model.*;
import de.othr.im.model.AuthenticationProvider;
import de.othr.im.model.oauthUser.CustomOAuth2User;
import de.othr.im.repository.*;
import de.othr.im.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    StudentProfessorRepository studentProfessorRepository;

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserEmailConfirmationRepository userEmailConfirmationRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @RequestMapping(value = "/student/add", method = RequestMethod.GET)
    public ModelAndView addStudentForm() {

        StudentProfessor studentProfessor = new StudentProfessor();
        studentProfessor.setUser(new User());
        ModelAndView mv = new ModelAndView();

        mv.addObject("neuStudent", studentProfessor);
        mv.setViewName("/verwalten/student-add");

        return mv;
    }


    @RequestMapping(value = "/student/add/process", method = RequestMethod.POST)
    public ModelAndView addStudentProcess(@ModelAttribute("neuStudent")
                                          StudentProfessor studentProfessor,
                                          BindingResult bindingResult
    ) {

        ModelAndView mv = new ModelAndView();

        if (studentProfessor.getUser().getMatrikelnummer() == null) {
            bindingResult.rejectValue("user.matrikelnummer", "error", "Matrikelnummer Ein Feld ist leer eingegeben.");
            mv.setViewName("redirect:/user/student/add");
            return mv;
        }
        if (studentProfessor.getUser().getEmail().isEmpty()) {
            bindingResult.rejectValue("user.email", "error", "Email Feld ist leer eingegeben.");
            mv.setViewName("redirect:/user/student/add");
            return mv;
        }

        if (studentProfessor.getUser().getEmail().isEmpty()) {
            bindingResult.rejectValue("user.email", "error", "Email Feld ist leer eingegeben.");
            mv.setViewName("redirect:/user/student/add");
            return mv;
        }
        if (studentProfessor.getUser().getPassword().isEmpty()) {
            bindingResult.rejectValue("user.password", "error", "Password Feld ist leer eingegeben.");
            mv.setViewName("redirect:/user/student/add");
            return mv;
        }
        if (studentProfessor.getUser().getNachname().isEmpty()) {
            bindingResult.rejectValue("user.nachname", "error", "Nachname Feld ist leer eingegeben.");
            mv.setViewName("redirect:/user/student/add");
            return mv;
        }
        if (studentProfessor.getUser().getName().isEmpty()) {
            bindingResult.rejectValue("user.name", "error", "Name Feld ist leer eingegeben.");
            mv.setViewName("redirect:/user/student/add");
            return mv;
        }
        if (studentProfessor.getUser().getType().isEmpty()) {
            bindingResult.rejectValue("user.type", "error", "Type Feld ist leer eingegeben.");
            mv.setViewName("redirect:/user/student/add");
            return mv;
        }

        if (bindingResult.hasErrors()) {
            mv.setViewName("redirect:/user/student/add");
            return mv;
        }

        Optional<User> userDB = userRepository.findUserByEmail(studentProfessor.getUser().getEmail());

        if (userDB.isPresent()) {
            System.out.println("User already exists!");

            //bindingResult.rejectValue("user.matrikelnummer", "error", "An account already exists for this login.");
            mv.addObject("message", "An account already exists for this login.");
            mv.setViewName("redirect:/user/student/add");
            return mv;
        }
        String checkEmail = studentProfessor.getUser().getEmail();
        if (checkEmail.contains("@st.oth-regensburg.de")) {

            User user = studentProfessor.getUser();

            List<Authority> myauthorities = new ArrayList<Authority>();
            myauthorities.add(new Authority(Constants.AUTHORITY_STUDENT));
            user.setMyauthorities(myauthorities);

            user.setActive(0);
            user.setAuthProvider(AuthenticationProvider.LOCAL);

            String encodedPassword = passwordEncoder.encode(studentProfessor.getUser().getPassword());
            user.setPassword(encodedPassword);

            userRepository.save(user);

            studentProfessor.setMatrikelnummer(user.getMatrikelnummer());
            studentProfessor.setUser(user);
            studentProfessorRepository.save(studentProfessor);


            mv.addObject("name", studentProfessor.getUser().getName());
            mv.addObject("email", studentProfessor.getUser().getEmail());
            mv.setViewName("/verwalten/student-added");

            this.emailSender(user);

            return mv;
        } else {
            mv.setViewName("/error");
            return mv;
        }

    }

    // Email mit Link senden
    @RequestMapping(value = "/email-sender")
    private void emailSender(User user) {

        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8080/user/confirm-account?token=" + confirmationToken.getConfirmationToken());

        javaMailSender.send(mailMessage);

    }

    // Wenn User aufm Link klickt
    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(@RequestParam("token") String confirmationToken) {

        ModelAndView mv = new ModelAndView();
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token != null && token.getUser().getId() != null) {
            User user = userEmailConfirmationRepository.findByEmailIgnoreCase(token.getUser().getEmail());
            user.setActive(1);
            userRepository.save(user);
            mv.setViewName("verwalten/accountVerified");
            return mv;
        } else {
            mv.addObject("message", "The link is invalid or broken!");
            mv.setViewName("verwalten/emailError");
            return mv;
        }
    }


    @RequestMapping(value = "/update/{id}")
    public ModelAndView update1(@PathVariable("id") Long iduser) {

        ModelAndView mv = new ModelAndView();
        Optional<User> user = userRepository.findById(iduser);

        if (userRepository.existsById(iduser)) {
            mv.addObject("studentForm", user.get());
            mv.setViewName("/verwalten/student-update");
            return mv;
        }

        Optional<Manager> oManager = managerRepository.findById(iduser);
        if (managerRepository.existsById(iduser)) {
            mv.setViewName("/verwalten/manager-update");
            mv.addObject("managerForm", oManager.get().getId());

            return mv;
        } else {
            mv.addObject("errors", "Event not Found");
            mv.setViewName("/error");
            return mv;
        }


    }

    @RequestMapping(value = "/update/process", method = RequestMethod.POST)
    public ModelAndView update2(@ModelAttribute("studentForm") User user, StudentProfessor studentProfessor) {

        ModelAndView mv = new ModelAndView();

        Integer mtnr = user.getMatrikelnummer();
        String email = user.getEmail();

        List<Authority> myauthorities = new ArrayList<Authority>();
        myauthorities.add(new Authority(Constants.AUTHORITY_STUDENT));
        user.setMyauthorities(myauthorities);
        user.setActive(1);
        user.setEmail(email);
        user.setMatrikelnummer(mtnr);
        user.setPassword(user.getPassword());
        user.setNachname(user.getNachname());
        user.setName(user.getName());
        user.setType(user.getType());
        user.setAuthProvider(user.getAuthProvider());


        userRepository.save(user);
        mv.setViewName("verwalten/student-updated");

        studentProfessor.setMatrikelnummer(mtnr);
        studentProfessor.setUser(user);
        studentProfessorRepository.save(studentProfessor);

        return mv;
    }


    @GetMapping("/student/delete")
    public ModelAndView deleteUser() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/verwalten/student-delete");
        return mv;

    }

    @RequestMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id, Model model) {

        ModelAndView mv = new ModelAndView();
        Optional<StudentProfessor> optStudent = studentProfessorRepository.findStudentByIdUser(id);
        Optional<User> user = userRepository.findById(id);

        if (userRepository.existsById(id)) {
            studentProfessorRepository.delete(optStudent.get());
            userRepository.delete(user.get());
            model.addAttribute("msgs", "Schade, dass du nicht mehr bei uns bist!");
            mv.setViewName("/verwalten/student-deleted");
        } else {
            model.addAttribute("errors", "Event not found!");
            mv.setViewName("redirect:/home");
        }
        return mv;

    }

    @RequestMapping("/all/{id}")
    public ModelAndView listById(@PathVariable("id") Long id) {

        ModelAndView mv = new ModelAndView();
        Optional<User> user = userRepository.findById(id);

        mv.addObject("user", user.get().getEmail());
        mv.addObject("user", user.get().getMatrikelnummer());
        mv.addObject("user", user.get().getNachname());
        mv.addObject("user", user.get().getName());
        mv.addObject("user", user.get().getType());
        mv.addObject("user", user.get().getAuthProvider());

        mv.setViewName("/verwalten/student-list");
        return mv;

    }


    public void updateUserAfterOAuthLogin(User user, String firstname, String lastname, AuthenticationProvider authenticationProvider) {

        user.setNachname(lastname);
        user.setName(firstname);
        user.setAuthProvider(authenticationProvider);
        userRepository.save(user);
    }

    @RequestMapping("/userWithGoogle")
    public void processOAuthPostLogin(String email, String firstname, String lastname) {

        StudentProfessor studentProfessor = new StudentProfessor();
        User user = new User();

        user.setEmail(email);
        user.setNachname(lastname);
        user.setName(firstname);
        user.setPassword(UUID.randomUUID().toString());
        user.setType("Student");

        List<Authority> myauthorities = new ArrayList<Authority>();
        myauthorities.add(new Authority(Constants.AUTHORITY_STUDENT));
        user.setMyauthorities(myauthorities);
        user.setAuthProvider(AuthenticationProvider.GOOGLE);
        user.setActive(0);


        userRepository.save(user);

        studentProfessor.setUser(user);
        studentProfessorRepository.save(studentProfessor);

    }


    @RequestMapping(method = RequestMethod.POST, value = "/matrikelNummer/process/{id}")
    public ModelAndView updateUserByMatrikelnummer(@ModelAttribute("neuMatrikelnummer") User getuser, StudentProfessor studentProfessor, @PathVariable("id") Long id, Authentication authentication) {
        ModelAndView mv = new ModelAndView();
        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
        String email = oauthUser.getEmail();

        Integer user1 = getuser.getMatrikelnummer();
        Optional<User> user = userRepository.findUserByEmail(email);

        mv.addObject("matrikelnummer", user.get().getMatrikelnummer());

        if (user.isPresent()) {
            List<Authority> myauthorities = new ArrayList<Authority>();
            myauthorities.add(new Authority(Constants.AUTHORITY_STUDENT));
            user.get().setMyauthorities(myauthorities);
            user.get().setActive(1);
            mv.addObject("matrikelnummer", user.get().getMatrikelnummer());
            user.get().setMatrikelnummer(user1);


            userRepository.save(user.get());

            studentProfessor.setUser(user.get());
            studentProfessor.setMatrikelnummer(user.get().getMatrikelnummer());
            studentProfessorRepository.save(studentProfessor);
        }

        mv.setViewName("verwalten/martikelnummer-added");
        return mv;

    }

}
