package de.othr.im.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.othr.im.model.AcademicEvent;
import de.othr.im.model.Workshop;
import de.othr.im.repository.AcademicEventRepository;
import de.othr.im.repository.WorkshopRepository;

@Controller
@RequestMapping("/workshop")
public class WorkshopController {

	@Autowired 
	WorkshopRepository workshopRepository;
	@Autowired
	AcademicEventRepository academicEventRepository;
	
	
	@RequestMapping(value= "/findacademicevent")
	public String findAcademicEventByID(@RequestParam(value = "titleacademicevent") String titleevent, Model model) {
		
		System.out.println("Title to be searched ="+  titleevent);
		List<AcademicEvent> academicEvents = academicEventRepository.findByTitleContaining(titleevent);
		System.out.println("Number of events found ="+  academicEvents.size());
		
		
		model.addAttribute("academicevents", academicEvents);
		model.addAttribute("workshopForm", new Workshop());
				
		return "/workshop/workshop-add";
		
	}
	
	
	@RequestMapping(value= "/findworkshopsbyacademicevent/{idevent}")
	public String findWorkshopByAcademicEvent(@PathVariable Long idevent, Model model) {
	
		List<Workshop> workshops = workshopRepository.findWorkshopsByAcademicEvent(idevent);
		Optional<AcademicEvent> eventOpt = academicEventRepository.findById(idevent);
		model.addAttribute("academicevent",eventOpt.get() );
		model.addAttribute("workshops", workshops);
			
	return "/workshop/workshop-byacademicevent";
	
	}
	
	
	@RequestMapping(value="/add")
	public String showAddWorkshopForm( Model model) {
	
		model.addAttribute("workshopForm", new Workshop());
		model.addAttribute("academicevents", new ArrayList<AcademicEvent>());
		System.out.println("Showing the Add workshop Form...");
		return "/workshop/workshop-add";
	}
	
	
	
	@RequestMapping(value= "/add/process")
	public String addWorkshopProcess(@Valid @ModelAttribute("workshopForm") Workshop workshopForm,  Model model) {
	
		
		System.out.println("Titulo da palestra a ser add="+ workshopForm.getTitle());
		System.out.println("ID do evento da palestra="+ workshopForm.getAcademicevent().getId());
		
		Workshop workshop = workshopRepository.save(workshopForm);
			
		System.out.println("Id da palestra salva="+ workshop.getId());
		
		return "forward:/academicevent/all";
	}
	
}

	
	

