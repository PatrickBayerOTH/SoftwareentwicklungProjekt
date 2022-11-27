package de.othr.im.controller;


import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import de.othr.im.model.AcademicEvent;
import de.othr.im.model.Lecture;
import de.othr.im.model.Workshop;
import de.othr.im.repository.AcademicEventRepository;
import de.othr.im.repository.LectureRepository;

@Controller
@RequestMapping("/lecture")
public class LectureController {

	
	@Autowired
	AcademicEventRepository academicEventRepository;
	
	@Autowired
	LectureRepository lectureRepository;
	
	
	
	@RequestMapping("/all")
	public String findAll(Model model) {
		
		List<Lecture> lectures = lectureRepository.findAll();
		model.addAttribute("lectures", lectures);
		
		return "/lecture/lecture-list";
	}
	
	
	
	@RequestMapping("/add")
	public String showAddLectureForm(Model model, HttpServletRequest request) {
		
		
		AcademicEvent academicEvent = new AcademicEvent();
		Lecture lecture = new Lecture();
		lecture.setAcademicevent(academicEvent);
		model.addAttribute("lectureForm", lecture);
		
		request.getSession().setAttribute("lectureSession", lecture);
		
		
		
		return "/lecture/lecture-add";
	}
	
	//show a AcademicEVent search form
	@RequestMapping(value= "/selectacademicevent")
	public String showSelectAcademicEventByID(Model model) {
				
		AcademicEvent academicEvent = new AcademicEvent();
		academicEvent.setId((long) -1);
		model.addAttribute("academiceventForm", academicEvent);
		//model.addAttribute("academicevents", academicEvents);
				
		return "/lecture/lecture-selectcademicevent";
		
	}
	
	//search for the title	
	@RequestMapping(value= "/selectacademicevent2")
	public String searchAcademicEvent(@ModelAttribute(name = "academiceventForm") AcademicEvent academicEvent, Model model) {
		
		model.addAttribute("academicevents", academicEventRepository
				.findByTitleContaining(academicEvent.getTitle()));
		
	return "/lecture/lecture-selectcademicevent";
	}
	
    //receive the ID-AcademicEvent and goes back to the lecture-add Form 
	@RequestMapping(value = "/selectacademicevent/{id}")
	public String selectAcademicEventProcess(@PathVariable("id") int id, Model model, HttpServletRequest request) {
		
		Lecture lectureSession = (Lecture) request.getSession().getAttribute("lectureSession");
		
		Optional<AcademicEvent> academicEvent  = academicEventRepository.findById((long) id);
		lectureSession.setAcademicevent(academicEvent.get());
					
		request.getSession().setAttribute("lectureSession", lectureSession);
		model.addAttribute("lectureForm", lectureSession);
		
		return  "/lecture/lecture-add";
	}		
	
		
	@RequestMapping(value= "/add/process")
	public ModelAndView addLectureProcess(@Valid @ModelAttribute("lectureForm") Lecture lectureForm, BindingResult bindingResult,  Model model) {
	
		ModelAndView mv = new ModelAndView();
		
		System.out.println("Title of the Lecture="+ lectureForm.getTitle());
		
		
		System.out.println();
		
		if (bindingResult.hasErrors()) {
			
			mv.setViewName("/lecture/lecture-add");
			
			return mv;
		}
		
		Lecture lecture = lectureRepository.save(lectureForm);
			
		System.out.println("Id Saved="+ lecture.getId());
		
		mv.setViewName( "forward:/lecture/all");
		
		
		return mv;
	
		
	
	}
	
	
	@RequestMapping(value= "/update/{id}" )
	public String edit(@PathVariable("id") Long id, Model model) {
			
		Optional<Lecture> lectureOpt = lectureRepository.findById(id);
	
		model.addAttribute("lectureForm", lectureOpt.get());
		
		System.out.println(" Updating the Lecture..."+ lectureOpt.get().getId());
		
				
		if (!lectureOpt.isEmpty()) {
			return "/lecture/lecture-update";
		}else {
			model.addAttribute("errors","Lecture not found!");
			return "forward:/lecture/all";
		}
		
		    
	}
	
	@RequestMapping(value= "/update/process")
	public String update2(@ModelAttribute("lectureForm") Lecture lectureForm,
                  Model model
            ) {
	
		System.out.println("Updating the lecture "+ lectureForm.getId());
				
		lectureRepository.save(lectureForm);
		
		model.addAttribute("msgs", "Lecture updated!");
		
		return "forward:/lecture/all";
	}
	
		
	@RequestMapping(value= "/delete/{id}" , method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, Model model) {
				
		Optional<Lecture> lectureOpt = lectureRepository.findById(id);
				
		//Gibt es!
		if (!lectureOpt.isEmpty()) {
			
			lectureRepository.delete(lectureOpt.get());
			model.addAttribute("msgs", "Lecture deleted!");
		}
		else {
			model.addAttribute("errors","Lecture not found!");
			
		}
				
		return "forward:/lecture/all";
		
	}
	
	@RequestMapping(value= "/findlecturesbyacademicevent/{idevent}")
	public String findWorkshopByAcademicEvent(@PathVariable Long idevent, Model model) {
	
		List<Lecture> lectures = lectureRepository.findLecturesByAcademicEvent(idevent);
		Optional<AcademicEvent> eventOpt = academicEventRepository.findById(idevent);
		model.addAttribute("academicevent",eventOpt.get() );
		model.addAttribute("lectures",lectures );
			
	return "/lecture/lecture-byacademicevent";
	
	}
	
}
