package de.othr.im.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.othr.im.model.Friend;
import de.othr.im.model.MoneyTransfer;
import de.othr.im.model.Student;
import de.othr.im.repository.FriendRepository;
import de.othr.im.repository.StudentRepository;
import de.othr.im.repository.TransferRepository;

@Controller
public class HomeController {

	@Autowired
	StudentRepository studentRepository;
	@Autowired
	FriendRepository friendRepository;
	@Autowired
	TransferRepository transferRepository;

	@GetMapping("/")
	public String hello() {
		System.out.println("JOOOOOO Start");
		return "start";
	}

	@GetMapping("/login")
	public String login() {
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

	@RequestMapping("/friends")
	public String friends(Model model) {
		// model.addAttribute("registration");

		List<Friend> currFriends = friendRepository.findByuserId(Long.valueOf(1));

		List<Student> studFriends = new ArrayList<Student>();
		{
		}
		;
		for (Friend f : currFriends) {
			Optional<Student> studF = studentRepository.findById(f.getFriendId());
			Student actStud = new Student();
			if (studF.isPresent()) {
				// value is present inside Optional
				actStud = studF.get();
			}

			studFriends.add(actStud);

		}
		for (Student s : studFriends) {
			System.out.print(s.getName());

		}

		// System.out.print(currFriends);

		// System.out.print(currFriends);
		model.addAttribute("currfriends", currFriends);
		model.addAttribute("studfriends", studFriends);
		return "/friends";
	}

	@RequestMapping(value = "/selectFriend")
	public String searchFriend(@ModelAttribute(name = "friendForm") Student friend, Model model) {
		// Student student = new Student();
		// request.getSession().setAttribute("studentSession", student);
		model.addAttribute("students", studentRepository.findByNameContaining(friend.getName()));
		List<Friend> friends = friendRepository.findByuserId(Long.valueOf(1));

		List<Student> studFriends = new ArrayList<Student>();
		{
		}
		;
		for (Friend f : friends) {
			Optional<Student> studF = studentRepository.findById(f.getFriendId());
			Student actStud = new Student();
			if (studF.isPresent()) {
				// value is present inside Optional
				actStud = studF.get();
			}

			studFriends.add(actStud);

		}
		// for (Student s : studFriends) {
		// System.out.print(s.getName());

		// }

		// System.out.print(friends);

		model.addAttribute("friends", friends);
		model.addAttribute("studfriends", studFriends);

		return "/friendSearch";
	}

	@RequestMapping(value = "/addFriend/{id}")
	public ModelAndView addFriend(@PathVariable("id") Long id, Model model, HttpServletRequest request,
			RedirectAttributes attributes) {
		if (friendRepository.findByuserIdAndFriendId(id, id) == null) {

			Optional<Student> friend = studentRepository.findById(id);
			List<Friend> friends = friendRepository.findByuserId(id);
			model.addAttribute("friends", friends);
			// System.out.println(friends.get(0).getFriendId());
			// System.out.println(friends.get(1).getFriendId());
			// System.out.println(friend.get().getName());
			Friend newFriend = new Friend();
			newFriend.setUserId(Long.valueOf(1));
			newFriend.setFriendId(id);
			// System.out.print(newFriend.getFriendId());
			friendRepository.save(newFriend);

			String msg = "Erfolgreich";
			attributes.addFlashAttribute("success", msg);
			// model.addAttribute("success",msg);
			return new ModelAndView("redirect:/selectFriend");
		} else {

			String msg = "Bereits vorhanden";
			attributes.addFlashAttribute("success", msg);
			// System.out.print("Bereits vorhanden");
			return new ModelAndView("redirect:/selectFriend");
		}

	}

	@Transactional
	@RequestMapping(value = "/removeFriend/{id}")
	public ModelAndView removeFriend(@PathVariable("id") Long id, Model model, HttpServletRequest request,
			RedirectAttributes attributes) {
		long deletedFriends = friendRepository.deleteByuserIdAndFriendId(Long.valueOf(1), id);

		String msg = "Freund entfernt";
		attributes.addFlashAttribute("deleted", msg);

		return new ModelAndView("redirect:/selectFriend");
	}

	@RequestMapping(value = "/sendMoney/{id}")
	public ModelAndView sendMoney(@ModelAttribute(name = "sendForm") MoneyTransfer transfer,
			@PathVariable("id") Long id, Model model, BindingResult result, HttpServletRequest request,
			RedirectAttributes attributes) {
		// model.addAttribute("transfer", transfer);
		
		List<MoneyTransfer> transactions = transferRepository.findByFrom(1);
		System.out.print(transactions);
		List<Student> recStuds = new ArrayList<Student>();
		for (MoneyTransfer m : transactions) {
			Optional<Student> recStud = studentRepository.findById(Long.valueOf(m.getTo()));
			Student actStud = new Student();
			if (recStud.isPresent()) {
				// value is present inside Optional
				actStud = recStud.get();
			}

			recStuds.add(actStud);

		}
		model.addAttribute("transactions", transactions);
		model.addAttribute("recStuds", recStuds);
		attributes.addAttribute("transactions", transactions);
		
		
		
		
		System.out.println(transfer.getAmount());
		Student currentUser = new Student();
		Optional<Student> optStudent = studentRepository.findById(Long.valueOf(1));
		float oldKonto = 0;
		if (studentRepository.findById(Long.valueOf(1)).isPresent()) {
			currentUser = optStudent.get();
			float availableAmount = currentUser.getKontostand();
			oldKonto = availableAmount;
			model.addAttribute("kontostand", availableAmount);
			if (availableAmount >= transfer.getAmount() && transfer.getAmount() > 0) {
				System.out.print("Updated Kontostand");
				System.out.print(transfer.getAmount());
				System.out.print("+");
				System.out.print(availableAmount);

				currentUser.setKontostand(-transfer.getAmount());
				Optional<Student> optTargetStudent = studentRepository.findById(id);
				transfer.setFrom(Math.toIntExact(currentUser.getId()));

				Student targetStudent = optTargetStudent.get();
				targetStudent.setKontostand(transfer.getAmount());
				transfer.setTo(Math.toIntExact(targetStudent.getId()));
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				transfer.setDate(timestamp);

				transferRepository.save(transfer);
				studentRepository.save(targetStudent);
				studentRepository.save(currentUser);
				
				
				

				if (oldKonto != currentUser.getKontostand()) {
					return new ModelAndView("redirect:/sendMoney");
				}

			}

		}

		List<Friend> currFriends = friendRepository.findByuserId(Long.valueOf(1));

		List<Student> studFriends = new ArrayList<Student>();
		{
		}
		;
		for (Friend f : currFriends) {
			Optional<Student> studF = studentRepository.findById(f.getFriendId());
			Student actStud = new Student();
			if (studF.isPresent()) {
				// value is present inside Optional
				actStud = studF.get();
			}

			studFriends.add(actStud);

		}

		model.addAttribute("currfriends", currFriends);
		model.addAttribute("studfriends", studFriends);

		String msg = "targetfriend";

		model.addAttribute("targetfriend", msg);

		return new ModelAndView("/sendToFriend");
	}

	@RequestMapping(value = "/sendMoney")
	public ModelAndView sendMoneywoF(Model model, HttpServletRequest request, RedirectAttributes attributes) {

		List<Friend> currFriends = friendRepository.findByuserId(Long.valueOf(1));
		// System.out.print("No Friend recieved");
		List<Student> studFriends = new ArrayList<Student>();
		{
		}
		;
		for (Friend f : currFriends) {
			Optional<Student> studF = studentRepository.findById(f.getFriendId());
			Student actStud = new Student();
			if (studF.isPresent()) {
				// value is present inside Optional
				actStud = studF.get();
			}

			studFriends.add(actStud);

		}
		// for (Student s : studFriends) {
		// System.out.print(s.getName());

		// }

		// System.out.print(currFriends);

		// System.out.print(currFriends);
		model.addAttribute("currfriends", currFriends);
		model.addAttribute("studfriends", studFriends);
		String msg = "notargetfriend";
		// attributes.addFlashAttribute("targetfriend", msg);
		model.addAttribute("targetfriend", msg);
		return new ModelAndView("/sendToFriend");
	}

	@GetMapping("/studentSearch")
	public String findAll(Model model) {
		// model.addAttribute("registration");

		List<Student> students = studentRepository.findAll();
		model.addAttribute("students", students);
		// System.out.println(students.get(0).getName());
		return "students-list";
	}

}