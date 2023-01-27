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

/* von Abdallah Alsoudi Alsoudi erstellt
 * Diese Klasse ist für User Registrieren (Standard, mit Google), Login (Standard , mit Google), Account Verwalten, Email Verifizieren, Email Bestätigungen
 * */
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


    /* Abdallah Alsoudi

     * Methode zum Aufruf die /verwalten/student-add, um User zu registrieren
     */
    @RequestMapping(value = "/student/add", method = RequestMethod.GET)
    public ModelAndView addStudentForm() {

        StudentProfessor studentProfessor = new StudentProfessor();
        studentProfessor.setUser(new User());
        ModelAndView mv = new ModelAndView();

        mv.addObject("neuStudent", studentProfessor);
        mv.setViewName("/verwalten/student-add");

        return mv;
    }

    /* Abdallah Alsoudi

     * Methode: nach der Eingabe der Informationen, wird diese Methode die Response behandeln
     * Erst wird überprüft:
     * 1. ob ein Feld leer eingegeben wurde, dann ruft die Methode addStudentForm() nochmal, damit die Seite zur Eingabe wieder da ist.
     * 2. wenn irgendein Fehler oder das User (Account) schon existiert, dann führt zu einem Fehler mit mv.setViewName("/error-email");
     * 3. wenn die eingegebene E-Mail keiner E-Mail-Adresse der OTH entspricht, dann führt zu einem Fehler mit mv.setViewName("/error-email");
     * 4. wenn die eingegebene E-Mail einer OTH-Email-Adresse entspricht, dann führt zum Speichern der eingegebenen Daten in der DB
     * Das Account wird nicht aktiviert, da es ein Verifizierungsprozess über Email durchgeführt wird.
     * Wenn der User seine Email verifiziert, dann darf er sich einloggen und ganze Dienste verwenden.
     * Es wird ein User registriert und mit allen verbundenen Klassen durch Fremdschlüßel verbunden
     * Note: Password wird durch encoder() verschlüsselt

     */
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
            mv.setViewName("/error-email");
            return mv;
        }

        Optional<User> userDB = userRepository.findUserByEmail(studentProfessor.getUser().getEmail());


        if (userDB.isPresent()) {
            mv.setViewName("/error-email");
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
            studentProfessor.setAccount(new Account());
            studentProfessorRepository.save(studentProfessor);


            mv.addObject("name", studentProfessor.getUser().getName());
            mv.addObject("email", studentProfessor.getUser().getEmail());
            mv.setViewName("/verwalten/student-added");

            this.emailSender(user);

        } else {
            mv.setViewName("/error-email");
        }
        return mv;

    }


    /* Abdallah Alsoudi

     * Diese Methode wird aufgerufen, wenn der User sein Password vergisst und druckt auf Passwort vergessen in Frontend.
     * dann wird eine Seite geöffnet, in der der User seine Email für das Passwort-zurücksetzen eingibt.
     * */
    @RequestMapping(value = "/emailForPassword", method = RequestMethod.GET)
    private ModelAndView passwordEmailEingabe(User user) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("neuPassword", user);
        mv.setViewName("verwalten/password");
        return mv;
    }

    /* Abdallah Alsoudi

     * Password zurücksetzen
     * Es wird überprüft, ob der User bzw. die Email in der DB existiert.
     * Wenn der User bzw. die Email nicht existiert, dann führt zu einem Fehler.
     * Wenn der User bzw. die Email existiert, dann wird eine Verifizierungscode an seine Email geschickt, um das neue Password eingeben zu können.
     */
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

    /* Abdallah Alsoudi
     * Diese Methode wird aufgerufen, wenn der User auf dem Link für Password-zurücksetzen klickt.
     * Dann wird eine Seite aufgerufen, die der User der Eingabe eines neuen Password ermöglicht
     * */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    private ModelAndView restPassword(@RequestParam("email") String email, User user) {

        ModelAndView mv = new ModelAndView();
        mv.addObject("neuPasswordEingabe", user);
        mv.setViewName("verwalten/neuPassword");

        return mv;
    }


    /* Abdallah Alsoudi
     * Diese Methode wird aufgerufen, wenn der User sein neues Password eingibt, dann wird erst nach der Email in der DB gesucht,
     * Damit wird der User anhand der Email festgestellt und wird eine neue verschlüsselte Password durch encoder() festgelegt.
     * Sonst bei irgendeinem Problem führt zu Fehler
     * */
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


    /* Abdallah Alsoudi
     *
     * Diese Methode wird aufgerufen, wenn ein neues User sich registriert.
     * Diese Methode wird in User addStudentProcess() aufgerufen, dann wird eine Email nach Anmeldung mit Link zum verifizierung geschickt
     * Es wird ein neues Token erstellt und in der DB zum check gelegt.
     * */
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

    /* Abdallah Alsoudi
     * Diese Mehotde schickt eine Bestätigung, wenn der User gelöscht wird
     * */
    @RequestMapping(value = "/email-sender-by-delete")
    private void emailSenderByDeleteAccount(User user) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Löschen!");
        mailMessage.setText("Ihr account unter der Email " + user.getEmail() + " wurde vom System gelöscht");
        javaMailSender.send(mailMessage);

    }
    
    /*Abdallah Alsoudi
     * Wenn User aufm Link klickt zum Verifizieren
     * Es wird überprüft, ob der Token in der DB existiert.
     * Wenn Nein, dann führt zu einem Fehler.
     * Wenn Ja existiert, dann wird das User bzw. das Account aktiviert.
     */
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

    /* Abdallah Alsoudi
     * um User by ID zu finden, dass der User seine Daten im Account aktualisieren kann
     *
     * */

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

    /* Abdallah Alsoudi
     * Wenn der User bestimmte Daten aktualisieren will, außer Email, Matrikelnummer und Provide sind unveränderlich aus Sicherheitsgründen
     * Außerdem werden die Daten auch in andere Klassen angepasst
     * */
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


    /* Abdallah Alsoudi
     * hier wird eine Seite aufgerufen, ob der User sein Account wirklich löschen will.
     * */
    @GetMapping("/student/delete")
    public ModelAndView deleteUser() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/verwalten/student-delete");
        return mv;
    }
    
    /*Abdallah Alsoudi
     * Nach klicken auf wirklich löschen
     * Es wird überprüft, ob der User existiert.
     * Anhand Id vom User wird der User und seine Daten in allen verbundenen Klassen gelöscht
     * */
    @RequestMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id, Model model) {

        ModelAndView mv = new ModelAndView();
        Optional<StudentProfessor> optStudent = studentProfessorRepository.findStudentByIdUser(id);
        Optional<User> user = userRepository.findById(id);
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findStudentByIdUser(id);
        Optional<Account> account = accountRepository.findById(id);

        if (userRepository.existsById(id)) {
        	//Deleting friends and transfers of the user who deletes his accounts Patrick Bayer
            long deletedFriends = friendRepository.deleteByuserId(Long.valueOf(id));
            long deletedFriendsS = friendRepository.deleteByFriendId(Long.valueOf(id));
            transferRepository.deleteBySender(account.get());
            transferRepository.deleteByReceiver(account.get());

            accountRepository.delete(account.get());

            if (confirmationToken.isPresent()) {
                confirmationTokenRepository.delete(confirmationToken.get());
                studentProfessorRepository.delete(optStudent.get());
                userRepository.delete(user.get());
            }
            model.addAttribute("msgs", "Schade, dass du nicht mehr bei uns bist!");
            mv.setViewName("/verwalten/student-deleted");
            emailSenderByDeleteAccount(user.get());
        } else {
            model.addAttribute("errors", "Event not found!");
            mv.setViewName("redirect:/home");
        }
        return mv;

    }

    /* Abdallah Alsoudi
     * Tabelle von Daten von User, wie Name, nachname usw.
     * dadurch kann der User bestimmte Daten aktualisieren
     * */
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
    
    /*Abdallah Alsoudi
     * Methode in Verbund mit Google Registrieren.
     * wird in der Klasse onAuthenticationSuccess verwendet.
     * Wenn der Google-User existiert, dann kann der User sich anmelden, dann werden die Daten von Gmail übermittelt.
     * Falls Änderungen dann werden die Daten aktualisiert
     * */
    public void updateUserAfterOAuthLogin(User user, String firstname, String lastname, AuthenticationProvider authenticationProvider) {

        user.setNachname(lastname);
        user.setName(firstname);
        user.setAuthProvider(authenticationProvider);
        userRepository.save(user);
    }
    
    /*Abdallah Alsoudi
     * Dient zum Registrieren eines neuen Users mit Google-Account
     * wird in der Klasse onAuthenticationSuccess verwendet.
     * Es wird ein User registriert und mit allen verbundenen Klassen durch Fremdschlüßel verbunden
     * */
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
    
    /* Abdallah Alsoudi
    * Diese Klasse ist wichtig, wenn der Student sich mit Google-Account registrieren möchte.
    * Danach wird nach Matrikelnummer und Type gefragt.
    * sonst kann keine Person außer Studentinnen oder Professorinnen sich registrieren
    * */
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
            if (responseMatrikelnummer == null && responseType.isEmpty()) {
                mv.setViewName("redirect:/home");
                return mv;
            }

        }
        mv.addObject("matrikelnummer", userByEmail.get().getMatrikelnummer());
        mv.setViewName("verwalten/martikelnummer-added");
        return mv;

    }

    
    /*Abdallah Alsoudi
    * ruft die Accountverwalten-Seite anhand der IDs von User
    * */
    @GetMapping("/accountVerwalten/{id}")
    public ModelAndView accountVerwalten(@PathVariable("id") Long id) {

        ModelAndView mv = new ModelAndView();
        Optional<User> user = userRepository.findById(id);
        mv.setViewName("/verwalten/account-verwalten");
        return mv;
    }

}
