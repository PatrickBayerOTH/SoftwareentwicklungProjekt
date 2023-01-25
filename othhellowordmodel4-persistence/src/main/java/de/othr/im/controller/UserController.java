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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    FriendRepository friendRepository;
    @Autowired
    TransferRepository transferRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    StudentProfessorRepository studentProfessorRepository;

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

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

        if (bindingResult.hasErrors()) {
            mv.setViewName("/error-email");
            return mv;
        }

        if (studentProfessor.getUser().getEmail().isEmpty() || studentProfessor.getUser().getType().isEmpty() || studentProfessor.getUser().getName().isEmpty()
                || studentProfessor.getUser().getNachname().isEmpty() || studentProfessor.getUser().getPassword().isEmpty() || studentProfessor.getUser().getMatrikelnummer() == null) {

            mv.setViewName("/verwalten/student-add");
            mv.setViewName("redirect:/user/student/add");
            return mv;
        }

        User user = studentProfessor.getUser();

        Optional<User> userDB = userRepository.findUserByEmail(studentProfessor.getUser().getEmail());
        Optional<User> userDbToMtnr = userRepository.findUserMtnr(user.getMatrikelnummer());


        if (userDB.isPresent()) {
            bindingResult.rejectValue("user.email", "error.user", "An account already exists for this login.");
            mv.setViewName("/error-email");
            return mv;
        }

        if (userDbToMtnr.isPresent()) {
            bindingResult.rejectValue("user.matrikelnummer", "error.user", "An account already exists for this login.");
            mv.setViewName("/error-email");
            return mv;
        }

        String checkEmail = studentProfessor.getUser().getEmail();
        if (checkEmail.contains("@st.oth-regensburg.de")) {

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
            studentProfessor.setAccount(new Account());
            studentProfessorRepository.save(studentProfessor);

            user.setActive(1);
            userRepository.save(user);
            mv.setViewName("verwalten/accountVerified");

            mv.addObject("name", studentProfessor.getUser().getName());
            mv.addObject("email", studentProfessor.getUser().getEmail());
           // mv.setViewName("/verwalten/student-added");

           // this.emailSender(user);

        } else {
            mv.setViewName("/error-email");
        }
        return mv;

    }

    @RequestMapping(value = "/emailForPassword", method = RequestMethod.GET)
    private ModelAndView passwordEmailEingabe(User user) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("neuPassword", user);
        mv.setViewName("verwalten/password");
        return mv;
    }

    // Password: Email mit Link senden
    @RequestMapping(value = "/email-sender-password")
    private ModelAndView emailSenderPassword(@ModelAttribute("neuPassword") User user, Model model) {
        ModelAndView mv = new ModelAndView();

        String email = user.getEmail();
        Optional<User> userDb = userRepository.findUserByEmail(email);

        if (userDb.isPresent()) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject("Password zurücksetzen!");
            mailMessage.setText("To reset your Password, please click here : "
                    + "http://localhost:8080/user/resetPassword?email=" + user.getEmail());

            javaMailSender.send(mailMessage);

            mv.setViewName("verwalten/checkEmailForPassword");
        } else {
            mv.setViewName("error-email");
        }
        return mv;
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    private ModelAndView restPassword(@RequestParam("email") String email, User user) {

        ModelAndView mv = new ModelAndView();
        mv.addObject("neuPasswordEingabe", user);
        mv.setViewName("verwalten/neuPassword");

        return mv;
    }

    @RequestMapping(value = "/resetPasswordDone/{email}", method = {RequestMethod.GET, RequestMethod.POST})
    private ModelAndView restPassword(@ModelAttribute("neuPasswordEingabe") User user, @PathVariable("email") String email) {

        ModelAndView mv = new ModelAndView();

        Optional<User> userDb = userRepository.findUserByEmail(email);

        if (!user.getPassword().isEmpty() && userDb.isPresent()) {

            String neuPassword = user.getPassword();

            String encodedPassword = passwordEncoder.encode(neuPassword);

            userDb.get().setPassword(encodedPassword);

            userRepository.save(userDb.get());
            mv.setViewName("verwalten/Password-done");
        } else {
            mv.addObject("message", "The link is invalid or broken!");
            mv.setViewName("error");
        }
        return mv;
    }

    // Email nach Anmeldung mit Link senden
    @RequestMapping(value = "/email-sender")
    private void emailSender(User user) {

    	try {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8080/user/confirm-account?token=" + confirmationToken.getConfirmationToken());

        javaMailSender.send(mailMessage);
    	}catch (Exception e) {
			
		}
    }

    // Email senden nach löschen des Accounts
    @RequestMapping(value = "/email-sender-by-delete")
    private void emailSenderByDeleteAccount(User user) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Löschen!");
        mailMessage.setText("Ihr account unter der Email " + user.getEmail() + " wurde vom System gelöscht");
        javaMailSender.send(mailMessage);

    }

    // Wenn User aufm Link klickt zum verifizieren
    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    private ModelAndView confirmUserAccount(@RequestParam("token") String confirmationToken) {

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
        Optional<Account> account = accountRepository.findById(user.getId());


        List<Authority> myauthorities = new ArrayList<Authority>();
        myauthorities.add(new Authority(Constants.AUTHORITY_STUDENT));
        user.setMyauthorities(myauthorities);
        user.setActive(1);
        user.setEmail(email);
        user.setMatrikelnummer(mtnr);
        user.setNachname(user.getNachname());
        user.setName(user.getName());
        user.setType(user.getType());
        user.setAuthProvider(user.getAuthProvider());

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);


        userRepository.save(user);
        mv.setViewName("verwalten/student-updated");

        studentProfessor.setMatrikelnummer(mtnr);
        studentProfessor.setUser(user);
        studentProfessor.setAccount(account.get());
        studentProfessorRepository.save(studentProfessor);

        return mv;
    }

    @GetMapping("/student/delete")
    public ModelAndView deleteUser() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/verwalten/student-delete");
        return mv;
    }

    @Transactional
    @RequestMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id, Model model) {

        ModelAndView mv = new ModelAndView();
        Optional<StudentProfessor> optStudent = studentProfessorRepository.findStudentByIdUser(id);
        Optional<User> user = userRepository.findById(id);
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findStudentByIdUser(id);
        Optional<Account> account = accountRepository.findById(id);


        if (userRepository.existsById(id)) {
            long deletedFriends = friendRepository.deleteByuserId(Long.valueOf(id));
            long deletedFriendsS = friendRepository.deleteByFriendId(Long.valueOf(id));
            transferRepository.deleteBySender(account.get());
            transferRepository.deleteByReceiver(account.get());

            studentProfessorRepository.delete(optStudent.get());
            userRepository.delete(user.get());
            accountRepository.delete(account.get());

            if (confirmationToken.isPresent()) {
                confirmationTokenRepository.delete(confirmationToken.get());
            }
            model.addAttribute("msgs", "Schade, dass du nicht mehr bei uns bist!");
            mv.setViewName("/verwalten/student-deleted");
            try {
                emailSenderByDeleteAccount(user.get());
            } catch (Exception e) {
                System.out.print("Email senden fehlgeschlagen");
            }
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
        studentProfessor.setUser(new User());
        studentProfessor.setAccount(new Account());


        studentProfessor.getUser().setEmail(email);
        studentProfessor.getUser().setNachname(lastname);
        studentProfessor.getUser().setName(firstname);
        studentProfessor.getUser().setPassword(UUID.randomUUID().toString());

        List<Authority> myauthorities = new ArrayList<Authority>();
        myauthorities.add(new Authority(Constants.AUTHORITY_STUDENT));
        studentProfessor.getUser().setMyauthorities(myauthorities);
        studentProfessor.getUser().setAuthProvider(AuthenticationProvider.GOOGLE);
        studentProfessor.getUser().setActive(0);

        userRepository.save(studentProfessor.getUser());

        Account account = studentProfessor.getAccount();
        studentProfessor.setAccount(account);

        studentProfessorRepository.save(studentProfessor);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/matrikelNummer/process/{id}")
    public ModelAndView updateUserByMatrikelnummer(@ModelAttribute("neuMatrikelnummer") User getuser, @PathVariable("id") Long id, Authentication authentication) {

        ModelAndView mv = new ModelAndView();

        StudentProfessor studentProfessor = studentProfessorRepository.getReferenceById(id);

        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
        String email = oauthUser.getEmail();

        Integer responseMatrikelnummer = getuser.getMatrikelnummer();

        String responseType = getuser.getType();

        Optional<User> userDbToMtnr = userRepository.findUserMtnr(responseMatrikelnummer);
        Optional<User> userByEmail = userRepository.findUserByEmail(email);
        Optional<User> userById = userRepository.findById(id);

        if (userDbToMtnr.isPresent()) {
            mv.setViewName("/error-email");
            return mv;
        }

        if (userByEmail.isPresent() && userById.isPresent()) {

            List<Authority> myauthorities = new ArrayList<Authority>();
            myauthorities.add(new Authority(Constants.AUTHORITY_STUDENT));
            userByEmail.get().setMyauthorities(myauthorities);
            userByEmail.get().setActive(1);

            if (responseMatrikelnummer != null && !responseType.isEmpty()) {
                userByEmail.get().setMatrikelnummer(responseMatrikelnummer);
                userByEmail.get().setType(responseType);

                userRepository.save(userByEmail.get());


                studentProfessor.setUser(userByEmail.get());
                studentProfessor.setMatrikelnummer(userByEmail.get().getMatrikelnummer());

                studentProfessorRepository.save(studentProfessor);
            }

            if (responseMatrikelnummer == null || responseType.isEmpty()) {
                mv.setViewName("redirect:/home");
                return mv;
            }

        }
        mv.addObject("matrikelnummer", userByEmail.get().getMatrikelnummer());
        mv.setViewName("verwalten/martikelnummer-added");
        return mv;

    }

    @GetMapping("/accountVerwalten/{id}")
    public ModelAndView accountVerwalten(@PathVariable("id") Long id) {

        ModelAndView mv = new ModelAndView();
        Optional<User> user = userRepository.findById(id);
        mv.setViewName("/verwalten/account-verwalten");
        return mv;
    }

}
