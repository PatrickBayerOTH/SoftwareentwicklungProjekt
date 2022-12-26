
package de.othr.im.controller;


import de.othr.im.model.StudentProfessor;
import de.othr.im.model.User;
import de.othr.im.repository.ManagerRepository;
import de.othr.im.repository.StudentProfessorRepository;
import de.othr.im.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    StudentProfessorRepository studentProfessorRepository;


    @RequestMapping(value = {"/home","/"})
    public ModelAndView home(HttpServletRequest request, Principal principal) {


        ModelAndView mv = new ModelAndView();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        String myAuthorities = authorities.toString();

        System.out.println(
                "Authorities of the logged user " + Integer.parseInt(principal.getName()) + " is/are" + " = " +
                        myAuthorities
        );

        //searching in the database by the login....
        Optional<User> oLoggedUser = userRepository.findUserByMatrikelnummer(Integer.parseInt(principal.getName()));
       /*     if (myAuthorities.contains("ADMIN")) {

            Optional<Manager> oManager;

            oManager = managerRepository.findStudentByIdUser(oLoggedUser.get().getId());

            request.getSession().setAttribute("managerSession", oManager.get());

            mv.setViewName("/manager");
        }*/
        if (myAuthorities.contains("STUDENT")) {
            Optional<StudentProfessor> oStudent;

            oStudent = studentProfessorRepository.findStudentByIdUser(oLoggedUser.get().getId());
            request.getSession().setAttribute("studentSession", oStudent.get());
            mv.setViewName("/student");
            return mv;

        }
 /*       if (myAuthorities.contains("PROFESSOR")) {
            Optional<Professor> oProfessor;

            oProfessor = professorRepository.findStudentByIdUser(oLoggedUser.get().getId());

            request.getSession().setAttribute("professorSession", oProfessor.get());

            mv.setViewName("/professor");
            return mv;

        }*/
        else {
            mv.setViewName("/error");

            return mv;
        }
    }

}

