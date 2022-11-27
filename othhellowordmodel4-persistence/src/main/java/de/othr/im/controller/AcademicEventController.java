package de.othr.im.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import de.othr.im.model.AcademicEvent;
import de.othr.im.repository.AcademicEventRepository;

@Controller
@RequestMapping("/academicevent")
public class AcademicEventController {

	@Autowired
	AcademicEventRepository academicEventRepository;
	
	//conversion of text to date
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
		
		
	@RequestMapping("/add")
	public String showAddAcademicEventForm(Model model) {
				
		AcademicEvent academicEvent = new AcademicEvent();
		model.addAttribute("academiceventForm", academicEvent);
		
		return "/academicevent/academicevent";
	}
	
	@RequestMapping("/add/process")
	public ModelAndView processAddAcademicEvent(@Valid @ModelAttribute(name = "academiceventForm") AcademicEvent academicevent,  BindingResult bindingResult) {
	
		ModelAndView mv = new ModelAndView();
		
		if (bindingResult.hasErrors()) {
			mv.setViewName("/academicevent/academicevent");
			return mv;
			
		}
		
		mv.addObject("academiceventForm", academicevent);
			
		academicEventRepository.save(academicevent);
		mv.setViewName("forward:/academicevent/all");
		return mv;
		
	}
	
	@RequestMapping(value= "/update/{id}" )
	public String edit(@PathVariable("id") Long id, Model model) {
	
		
		Optional<AcademicEvent> academicEventOpt = academicEventRepository.findById(id);
		
		
		
		model.addAttribute("academiceventForm", academicEventOpt.get());
		
		System.out.println(" Updating the Event..."+ academicEventOpt.get().getId());
		
				
		if (!academicEventOpt.isEmpty()) {
			return "/academicevent/academicevent-update";
		}else {
			model.addAttribute("errors","Academic Event not found!");
			return "forward:/academicevent/all";
		}
		
		    
	}
	
	@RequestMapping(value= "/update/process")
	public String update2(@ModelAttribute("academiceventForm") AcademicEvent academicEVentForm,
                  Model model
            ) {
	
		System.out.println("Updating the event "+ academicEVentForm.getId());
		
		
		academicEventRepository.save(academicEVentForm);
		
		model.addAttribute("msgs", "Event updated!");
		
		return "forward:/academicevent/all";
	}
	
	@RequestMapping(value= "/delete/{id}" , method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, Model model) {
				
		Optional<AcademicEvent> event = academicEventRepository.findById(id);
				
		if (!event.isEmpty()) {
			
			System.out.println("Deleting the event "+ event.get().getId());
			academicEventRepository.delete(event.get());
			model.addAttribute("msgs", "Event deleted!");
		}
		else {
			model.addAttribute("errors","Event not found!");
			
		}
				
		return "forward:/academicevent/all";
		
	}

	
	@RequestMapping("/all")
	public String findAll(Model model) {
		
		List<AcademicEvent> academicEvents = academicEventRepository.findAll();
		System.out.println("size of workshops of academicevent 0="+academicEvents.get(0).getWorkshops().size());
		model.addAttribute("academicevents", academicEvents);
		
		return "/academicevent/academicevent-list";
	}
}
