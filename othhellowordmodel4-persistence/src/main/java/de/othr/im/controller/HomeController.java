package de.othr.im.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.othr.im.model.*;
import de.othr.im.model.oauthUser.CustomOAuth2User;
import de.othr.im.repository.*;

import de.othr.im.util.I18nFunctions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    ManagerRepository managerRepository;
    
    @Autowired
    StudentProfessorRepository studentProfessorRepository;

    @Autowired
    FriendRepository friendRepository;
    @Autowired
    TransferRepository transferRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CorporateRepository corporateRepository;
    
    
    /* Abdallah Alsoudi 
    gleich Prinzip von der Home Methode aus der Vorlesung mit spezifischen Anpassungen
    * Wenn STUDENT exisitert dann meldet sich einfach an.
    * Beim Google anmelden, Wenn Studierenden/ Professorinnen noch keine Matrikelnummer hinterlegt haben, dann Anmeldung ist nicht möglich
    * wenn schon die Matrikelnummer übeinstimmt dann ist eine Anmeldung möglich
    */
    @RequestMapping(value = {"/home", "/"})
    public ModelAndView home(HttpServletRequest request, Principal principal, Authentication authentication) {


        ModelAndView mv = new ModelAndView();


        List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        String myAuthorities = authorities.toString();


        //searching in the database by the EMail...
        Optional<User> oLoggedUser = userRepository.findUserByEmail(principal.getName());


        if (myAuthorities.contains("MANAGER")) {

            Optional<Manager> oManager;

            oManager = managerRepository.findManagerByIdUser(oLoggedUser.get().getId());

            request.getSession().setAttribute("managerSession", oManager.get());

            mv.setViewName("/manager");
            return mv;

        }
        if (myAuthorities.contains("STUDENT")) {
            Optional<StudentProfessor> optionalStudentRepository;

            optionalStudentRepository = studentProfessorRepository.findStudentByIdUser(oLoggedUser.get().getId());
            request.getSession().setAttribute("studentSession", optionalStudentRepository.get());
            getWeather(request);
            mv.setViewName("/student");
            return mv;
        }

        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
        Optional<User> googleUser = userRepository.findUserByEmail(oauthUser.getEmail());
        if (googleUser.isPresent()) {
            if (googleUser.get().getMatrikelnummer() == null) {
                mv.addObject("neuMatrikelnummer", googleUser.get());
                Optional<StudentProfessor> studentProfessor;
                studentProfessor = studentProfessorRepository.findStudentByIdUser(googleUser.get().getId());
                request.getSession().setAttribute("studentSession", studentProfessor.get());
                mv.setViewName("/verwalten/matrikelnummer");
            }

            if (googleUser.get().getMatrikelnummer() != null) {
                Optional<StudentProfessor> studentProfessor;
                studentProfessor = studentProfessorRepository.findStudentByIdUser(googleUser.get().getId());
                request.getSession().setAttribute("studentSession", studentProfessor.get());
                getWeather(request);
                mv.setViewName("/student");
                return mv;
            }

        } else {
            mv.setViewName("/login");
        }
        return mv;

    }

  
    public void getWeather(HttpServletRequest request) {
    	String key = "d4d77f62e03de4155aeda1baf768bd49";
		String city = "Regensburg";
		String uri = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=" + key;

		RestTemplate rT = new RestTemplate();
		String result = rT.getForObject(uri, String.class);
		// System.out.print(result);
		JSONObject jo = new JSONObject(result);
		JSONObject wetObj = jo.getJSONArray("weather").getJSONObject(0);
		String wet = wetObj.getString("main");
		//System.out.println(wet);
		switch (wet) {
		case "Clouds":
			wet="http://openweathermap.org/img/wn/02d@2x.png";
			break;
		case "Thunderstorm":
			wet="http://openweathermap.org/img/wn/11d@2x.png";
			break;
		case "Drizzle":
			wet="http://openweathermap.org/img/wn/09d@2x.png";
			break;
		case "Rain":
				wet="http://openweathermap.org/img/wn/10d@2x.png";
			break;
		case "Snow":
			wet="http://openweathermap.org/img/wn/13d@2x.png";
			break;
		case "Clear":
			wet="http://openweathermap.org/img/wn/01d@2x.png";
			break;

		default:
			break;
		}
		request.getSession().setAttribute("wet", wet);
		double temp = jo.getJSONObject("main").getDouble("temp") - 273.15;
		double rf = jo.getJSONObject("main").getDouble("feels_like") - 273.15;
        String tempS = I18nFunctions.localizeTemperature(temp, LocaleContextHolder.getLocale());
        //String tempS = String.format("%.2f", temp);
        String rfS = I18nFunctions.localizeTemperature(rf, LocaleContextHolder.getLocale());
        //String rfS = String.format("%.2f", rf);
		request.getSession().setAttribute("temp", tempS);
		request.getSession().setAttribute("rf", rfS);
    	
    	
    }


}