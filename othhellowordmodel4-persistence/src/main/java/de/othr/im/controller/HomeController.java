package de.othr.im.controller;


import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import de.othr.im.model.Student;
import de.othr.im.repository.StudentRepository;

@Controller
public class HomeController {
	
	@Autowired
	StudentRepository studentRepository;

    @GetMapping("/")
    public String hello(){
    	System.out.println("JOOOOOO Start");
        return "start";
    }
    
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/registration")
    public String message(Model model) {
       // model.addAttribute("registration");
        return "registration";
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
    @GetMapping("/friends")
    public String friends(Model model) {
       // model.addAttribute("registration");
        return "friends";
    }
    
    
    @RequestMapping(value= "/selectFriend")
	public String searchFriend(@ModelAttribute(name = "friendForm") Student friend, Model model) {
    //	Student student = new Student();
    //	request.getSession().setAttribute("studentSession", student);
		model.addAttribute("students", studentRepository
				.findByNameContaining(friend.getName()));
		
	return "/friendSearch";
	}
    
  
    @RequestMapping(value = "/addFriend/{id}")
	public String addFriend(@PathVariable("id") Long id,  Model model, HttpServletRequest request) {
    	Optional<Student> friend = studentRepository.findById(id);
    	System.out.println(friend.get().getName());
		
	return "/lecture/lecture-add";
	}
    
   
    
    @GetMapping("/studentSearch")
    public String findAll(Model model) {
      // model.addAttribute("registration");
    	
    	List<Student> students = studentRepository.findAll();
    	model.addAttribute("students", students);
    	System.out.println(students.get(0).getName());
        return "students-list";
    }
    
}