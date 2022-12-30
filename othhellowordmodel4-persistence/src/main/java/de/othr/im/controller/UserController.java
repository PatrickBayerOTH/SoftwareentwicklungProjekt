package de.othr.im.controller;

import de.othr.im.model.*;
import de.othr.im.repository.*;
import de.othr.im.service.EmailService;
import de.othr.im.util.Constants;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    StudentProfessorRepository studentProfessorRepository;

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

/*    @Autowired
    UserEmailConfirmationRepository userEmailConfirmationRepository;*/

/*    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;*/


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

        User user = studentProfessor.getUser();

        Optional<User> userDB = userRepository.findUserByMatrikelnummer(user.getMatrikelnummer());
        if (userDB.isPresent()) {
            System.out.println("User already exists!");

            // bindingResult.rejectValue("user.matrikelnummer", "error", "An account already exists for this login.");
            //mv.setViewName("redirect:/user/student/add");
            mv.addObject("message", "An account already exists for this login.");
            mv.setViewName("/error");
            return mv;
        } else {

            List<Authority> myauthorities = new ArrayList<Authority>();
            myauthorities.add(new Authority(Constants.AUTHORITY_STUDENT));
            user.setMyauthorities(myauthorities);
            user.setActive(1);

            user = userRepository.save(user);
/*


            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(studentProfessor.getUser().getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setText("To confirm your account, please click here : "
                    + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());

            emailService.sendEmail(mailMessage);
*/

            studentProfessor.setMatrikelnummer(user.getMatrikelnummer());
            studentProfessor.setUser(user);
            studentProfessorRepository.save(studentProfessor);

            mv.addObject("emailId", studentProfessor.getUser().getEmail());
            mv.addObject("name", studentProfessor.getUser().getName());
            mv.addObject("email", studentProfessor.getUser().getEmail());

            mv.setViewName("/verwalten/student-added");

            return mv;
        }

    }

  /*  // Wenn User aufm Link klickt
    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView mv, @RequestParam("token") String confirmationToken) {

        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token != null) {
            User user = userEmailConfirmationRepository.findByEmailIdIgnoreCase(token.getUser().getEmail());
            user.setActive(1);
            userRepository.save(user);
            mv.setViewName("verwalten/accountVerified");
        } else {
            mv.addObject("message", "The link is invalid or broken!");
            mv.setViewName("verwalten/emailError");
        }

        return mv;
    }*/



  /*
    // send verification Email
    private void sendverificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String subject = "Please verify your registration";
        String senderName = "UniPay Team";
        String mailContent = "Hallo" + user.getName() + user.getNachname() + ",</p>";
        mailContent += "<p>Please click the link below to verify to your registration:</p>";

        String verifyURL = siteURL + "/verify?code" + user.getVerificationCode();
        mailContent += "<h3><a herf=\"" + verifyURL + "\">VERIFY</a></h3>";

        mailContent += "<p>Thank you<br>The UniPay Team";


        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setFrom("abdallah.alsoudi@st.oth-regensburg.de", senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);

        mailSender.send(mimeMessage);

    }*/


    //TODO: PASSWORD UPDATE
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

        mv.addObject("password", user.getPassword());
        List<Authority> myauthorities = new ArrayList<Authority>();
        myauthorities.add(new Authority(Constants.AUTHORITY_STUDENT));
        user.setMyauthorities(myauthorities);
        user.setActive(1);

        userRepository.save(user);
        mv.setViewName("verwalten/student-updated");

        studentProfessor.setMatrikelnummer(user.getMatrikelnummer());
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
        Optional<StudentProfessor> optStudent = studentProfessorRepository.findById(id);
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

        mv.setViewName("/verwalten/student-list");
        return mv;

    }

}
