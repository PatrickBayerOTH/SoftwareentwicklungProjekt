package de.othr.im.controller;

import de.othr.im.repository.StudentProfessorRepository;
import de.othr.im.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Autowired
    StudentProfessorRepository studentProfessorRepository;

    @Autowired
    UserRepository userRepository;


    @RequestMapping(value = "/login")
    public ModelAndView showLoginForm() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/login");
        return mv;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/prelogout")
    public ModelAndView showPreLogout(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/prelogout");
        return mv;
    }

}
