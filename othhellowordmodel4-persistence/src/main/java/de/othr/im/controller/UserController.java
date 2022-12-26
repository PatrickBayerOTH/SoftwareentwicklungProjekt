package de.othr.im.controller;

import de.othr.im.model.Authority;
import de.othr.im.model.Manager;
import de.othr.im.model.StudentProfessor;
import de.othr.im.model.User;
import de.othr.im.repository.ManagerRepository;
import de.othr.im.repository.StudentProfessorRepository;
import de.othr.im.repository.UserRepository;
import de.othr.im.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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


    @RequestMapping("/student/add")
    public ModelAndView addStudentForm() {


        StudentProfessor studentProfessor = new StudentProfessor();
        studentProfessor.setUser(new User());
        ModelAndView mv = new ModelAndView();

        mv.addObject("neuStudent", studentProfessor);
        mv.setViewName("/verwalten/student-add");

        return mv;
    }


    @RequestMapping("/student/add/process")
    public ModelAndView addStudentProcess(@ModelAttribute("neuStudent")
                                          StudentProfessor studentProfessor,
                                          BindingResult bindingResult, RedirectAttributes redirectAttributes
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

        Optional<User> userDB = userRepository.findUserByMatrikelnummer(studentProfessor.getUser().getMatrikelnummer());

        if (userDB.isPresent()) {
            System.out.println("User already exists!");
            mv.setViewName("verwalten/student-add");
            bindingResult.rejectValue("user.matrikelnummer", "error", "An account already exists for this login.");

            return mv;

        }


        User user = studentProfessor.getUser();

        //TODO: Warum schlägt immer fehler mit myauthorities

/*

        List<Authority> myauthorities = new ArrayList<Authority>();
        //I could get this value from the view

        myauthorities.add(new Authority(Constants.AUTHORITY_STUDENT));

        user.setMyauthorities(myauthorities);
*/


        user.setActive(1);
        user = userRepository.save(user);

        studentProfessor.setMatrikelnummer(user.getMatrikelnummer());
        studentProfessor.setUser(user);
        studentProfessorRepository.save(studentProfessor);

        mv.addObject("name", studentProfessor.getUser().getName());
        mv.addObject("email", studentProfessor.getUser().getEmail());
        mv.setViewName("/verwalten/student-added");
        return mv;

    }

    //TODO: NAME ODER PASSWORD UPDATE
    @RequestMapping(value = "/update/{id}")
    public ModelAndView edit(@PathVariable("id") Long iduser, Model model) {

        ModelAndView mv = new ModelAndView();

        mv.setViewName("/student");

        Optional<Manager> oManager;

        oManager = managerRepository.findManagerByIdUser(iduser);
        if (oManager.isPresent()) {
            mv.setViewName("/user/user-manager-update");
            mv.addObject("managerForm", oManager.get());
            System.out.println("Updating the manager LOGIN " + oManager.get().getUser().getMatrikelnummer());
            return mv;
        }

        Optional<StudentProfessor> oStudent;
        oStudent = studentProfessorRepository.findStudentByIdUser(iduser);
        if (oStudent.isPresent()) {
            mv.setViewName("/user/user-student-update");
            mv.addObject("studentForm", oStudent.get());
            System.out.println("Updating the student LOGIN " + oStudent.get().getUser().getMatrikelnummer());
            return mv;
        }

        return mv;
    }

    @RequestMapping(value = "/update/process", method = RequestMethod.POST)
    public ModelAndView update2(@ModelAttribute("studentForm")
                                StudentProfessor studentProfessor) {


        ModelAndView mv = new ModelAndView();


        mv.setViewName("verwalten/student-updated");

        studentProfessorRepository.save(studentProfessor);


        return mv;
    }

    //TODO: ACCOUNT LÖSCHEN
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable("id") Long id, Model model) {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/verwalten/student-deleted");
        Optional<StudentProfessor> optStudent = studentProfessorRepository.findById(id);

        if (!optStudent.isEmpty()) {
            studentProfessorRepository.delete(optStudent.get());
            model.addAttribute("msgs", "Event deleted!");
        } else {
            model.addAttribute("errors", "Event not found!");
        }
        return mv;
    }


}
