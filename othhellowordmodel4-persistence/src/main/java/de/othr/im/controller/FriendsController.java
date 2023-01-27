package de.othr.im.controller;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import de.othr.im.util.I18nFunctions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

import de.othr.im.model.Account;
import de.othr.im.model.Corporate;
import de.othr.im.model.Friend;
import de.othr.im.model.HistoricFriendTransfer;
import de.othr.im.model.MoneyTransfer;
import de.othr.im.model.StudentProfessor;
import de.othr.im.model.User;
import de.othr.im.repository.AccountRepository;
import de.othr.im.repository.CorporateRepository;
import de.othr.im.repository.FriendRepository;
import de.othr.im.repository.HistoricFriendTransferRepository;
import de.othr.im.repository.ManagerRepository;
import de.othr.im.repository.StudentProfessorRepository;
import de.othr.im.repository.TransferRepository;
import de.othr.im.repository.UserRepository;

/*
Controller that handles requests related to Friends
- Searching friends in user database
- Adding a user as a friend
- Removing a user as a friend
- Sending money to a friend
- Email communication related to friends like money sent notification

 
Written by Patrick Bayer
 */

@Controller
public class FriendsController {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	HistoricFriendTransferRepository hFRepository;

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

	
	/*
    Returns the main friend page
    Shows all friends if any exist
     */
	@RequestMapping("/friends")
	public String friends(Model model, HttpServletRequest request) {

		StudentProfessor curUser = (StudentProfessor) request.getSession().getAttribute("studentSession");
		System.out.println(curUser.getAccount().getValue());

		List<Friend> currFriends = friendRepository.findByuserId(Long.valueOf(curUser.getUser().getId()));
		if (currFriends.isEmpty()) {
			currFriends = friendRepository.findByfriendId(Long.valueOf(curUser.getUser().getId()));
		}

		List<User> studFriends = new ArrayList<User>();
		{
		}

		for (Friend f : currFriends) {

			Optional<User> studF = userRepository.findById(f.getFriendId());

			User actStud = new User();
			if (studF.isPresent()) {

				actStud = studF.get();
			}

			studFriends.add(actStud);

		}

		model.addAttribute("currfriends", currFriends);
		model.addAttribute("studfriends", studFriends);
		return "/friends";
	}

	
	/*
    Returns the search page for searching for friend in user db
    The returned model contains the letters or names the user want to search for
    Show all results and all existing friends
     */
	@RequestMapping(value = "/selectFriend")
	public String searchFriend(@ModelAttribute(name = "friendForm") User friend, Model model,
			HttpServletRequest request) {

		StudentProfessor curUser = (StudentProfessor) request.getSession().getAttribute("studentSession");

		List<User> finds = userRepository.findAllByNameContaining(friend.getName());

		List<User> findsNachname = userRepository.findAllByNachnameContaining(friend.getName());
		List<User> listNachnameCopy = new ArrayList<>(findsNachname);
		listNachnameCopy.removeAll(finds);
		System.out.printf("CurUserID: %d", curUser.getId());
		finds.addAll(listNachnameCopy);
		finds.removeIf(u -> u.getId() == curUser.getId());

		model.addAttribute("students", finds);

		List<Friend> friends = friendRepository.findByuserId(Long.valueOf(curUser.getUser().getId()));

		if (friends.isEmpty()) {
			friends = friendRepository.findByfriendId(Long.valueOf(curUser.getUser().getId()));
		}

		List<User> studFriends = new ArrayList<User>();
		{
		}
		;
		for (Friend f : friends) {

			Optional<User> studF = userRepository.findById(f.getFriendId());

			User actStud = new User();
			if (studF.isPresent()) {

				actStud = studF.get();
			}

			studFriends.add(actStud);

		}

		getWeather(request);
		model.addAttribute("friends", friends);
		model.addAttribute("studfriends", studFriends);

		return "/friendSearch";
	}

	/*
    After clicking a result in the search page the new friends will be added.
    Returns search page with updated existing friends.
    A confirmation mail gets send to the user.
    
     */
	@RequestMapping(value = "/addFriend/{id}")
	public ModelAndView addFriend(@PathVariable("id") Long id, Model model, HttpServletRequest request,
			RedirectAttributes attributes) {

		StudentProfessor curUser = (StudentProfessor) request.getSession().getAttribute("studentSession");

		if (friendRepository.findByuserIdAndFriendId(curUser.getUser().getId(), id) == null
				&& id != curUser.getUser().getId()) {

			Optional<StudentProfessor> friend = studentProfessorRepository.findStudentByIdUser(id);// studentProfessorRepository.findById(id);
			System.out.println(id);
			List<Friend> friends = friendRepository.findByuserId(curUser.getUser().getId());
			if (friends.isEmpty()) {
				friends = friendRepository.findByfriendId(Long.valueOf(curUser.getUser().getId()));
			}

			model.addAttribute("friends", friends);

			Friend newFriend = new Friend();
			newFriend.setUserId(Long.valueOf(curUser.getUser().getId()));
			newFriend.setFriendId(id);

			friendRepository.save(newFriend);

			Friend newFriendReverse = new Friend();
			newFriendReverse.setFriendId(Long.valueOf(curUser.getUser().getId()));
			newFriendReverse.setUserId(id);

			friendRepository.save(newFriendReverse);

			try {
				SimpleMailMessage msgAddFriend = new SimpleMailMessage();
				msgAddFriend.setTo(curUser.getUser().getEmail());
				System.out.println(friend.get().getUser().getName());
				msgAddFriend.setSubject("UniPays INFO: Sie haben einen Freund hinzugefügt");
				msgAddFriend.setText("Sie haben " + friend.get().getUser().getNachname() + " "
						+ friend.get().getUser().getName() + "als Freund hinzugefügt");

				javaMailSender.send(msgAddFriend);
			} catch (Exception e) {
				System.out.print("Email senden fehlgeschlagen");
			}

			String msg = "Erfolgreich";
			attributes.addFlashAttribute("success", msg);

			return new ModelAndView("redirect:/selectFriend");
		} else {

			String msg = "Bereits vorhanden";
			attributes.addFlashAttribute("success", msg);

			return new ModelAndView("redirect:/selectFriend");
		}

	}
	
	/*
    When clicked on the remove button of a friend the friendship will be deleted.
    Returns the search page with updated existing friends
    A confirmation mail gets send to the user.
     */

	@Transactional
	@RequestMapping(value = "/removeFriend/{id}")
	public ModelAndView removeFriend(@PathVariable("id") Long id, Model model, HttpServletRequest request,
			RedirectAttributes attributes) {

		StudentProfessor curUser = (StudentProfessor) request.getSession().getAttribute("studentSession");

		long deletedFriends = friendRepository.deleteByuserIdAndFriendId(Long.valueOf(curUser.getUser().getId()), id);

		long deletedFriendsS = friendRepository.deleteByFriendIdAndUserId(Long.valueOf(curUser.getUser().getId()), id);

		Optional<StudentProfessor> friend = studentProfessorRepository.findStudentByIdUser(id);
		try {
			SimpleMailMessage msgRemoveFriend = new SimpleMailMessage();
			msgRemoveFriend.setTo(curUser.getUser().getEmail());

			msgRemoveFriend.setSubject("UniPays INFO: Sie haben einen Freund entfernt");
			msgRemoveFriend.setText("Sie haben " + friend.get().getUser().getNachname() + " "
					+ friend.get().getUser().getNachname() + "als Freund entfernt");

			javaMailSender.send(msgRemoveFriend);
		} catch (Exception e) {
			System.out.print("Email senden fehlgeschlagen");
		}

		String msg = "Freund entfernt";
		attributes.addFlashAttribute("deleted", msg);

		return new ModelAndView("redirect:/selectFriend");
	}

	/*
    When clicked on the sendMoney button of a friend the user sends money to the user corresponding to the id
    Returns the sendMoney view with updated transfer history
    The model attribute transfer contains amount and message of the transfer
    A confirmation mail gets send to the user and the receiving user.
     */
	@RequestMapping(value = "/sendMoney/{id}")
	public ModelAndView sendMoney(@ModelAttribute(name = "sendForm") MoneyTransfer transfer,
			@PathVariable("id") Long id, Model model, BindingResult result, HttpServletRequest request,
			RedirectAttributes attributes) {

		StudentProfessor currentUser = (StudentProfessor) request.getSession().getAttribute("studentSession");

		List<HistoricFriendTransfer> transactions = hFRepository.findBysenderId(currentUser.getUser().getId()); // Out
		List<HistoricFriendTransfer> transactionsIn = hFRepository.findByreceiverId(currentUser.getUser().getId());
		transactions.addAll(transactionsIn);

		List<String> sender = new ArrayList<>(), receiver = new ArrayList<>(), date = new ArrayList<>();
		for (HistoricFriendTransfer m : transactions) {

			sender.add(m.getSender());

			receiver.add(m.getReceiver());
			date.add(I18nFunctions.localizeDate(m.getDate(), LocaleContextHolder.getLocale()));
		}
		List<Integer> directions = new ArrayList<Integer>();
		for (HistoricFriendTransfer m : transactions) {
			if (m.getSenderid() == currentUser.getAccount().getId()) {

				directions.add(1);

			} else {
				directions.add(0);

			}

		}
		model.addAttribute("directions", directions);
		getWeather(request);
		model.addAttribute("date", date);
		model.addAttribute("transactions", transactions);
		model.addAttribute("sender", sender);
		model.addAttribute("receiver", receiver);

		attributes.addAttribute("transactions", transactions);

		System.out.println(transfer.getAmount());

		double oldKonto = 0;
		if (studentProfessorRepository.findById(Long.valueOf(currentUser.getId())).isPresent()) {

			double availableAmount = currentUser.getAccount().getValue();
			oldKonto = availableAmount;
			model.addAttribute("kontostand", availableAmount);
			if (availableAmount >= transfer.getAmount() && transfer.getAmount() > 0) {
				System.out.print("Updated Kontostand");
				System.out.print(transfer.getAmount());
				System.out.print("+");
				System.out.print(availableAmount);

				currentUser.getAccount().setValue(oldKonto - transfer.getAmount());
				Optional<StudentProfessor> optTargetStudent = studentProfessorRepository.findStudentByIdUser(id);
				transfer.setSender(currentUser.getAccount());

				StudentProfessor targetStudent = optTargetStudent.get();
				double oldKOntoTarget = targetStudent.getAccount().getValue();
				targetStudent.getAccount().setValue(oldKOntoTarget + transfer.getAmount());
				transfer.setReceiver(targetStudent.getAccount());
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				transfer.setDate(timestamp);

				HistoricFriendTransfer newhFTransfer = new HistoricFriendTransfer();
				long senderid = Long.valueOf(currentUser.getUser().getId());
				newhFTransfer.setSenderid(senderid);
				long receiverid = Long.valueOf(targetStudent.getUser().getId());
				newhFTransfer.setReceiverid(receiverid);
				newhFTransfer.setSender(currentUser.getUser().getName() + " " + currentUser.getUser().getNachname());
				newhFTransfer
						.setReceiver(targetStudent.getUser().getName() + " " + targetStudent.getUser().getNachname());
				newhFTransfer.setAmount(transfer.getAmount());
				newhFTransfer.setMessage(transfer.getMessage());
				newhFTransfer.setDate(timestamp);

				hFRepository.save(newhFTransfer);
				transferRepository.save(transfer);
				studentProfessorRepository.save(targetStudent);
				studentProfessorRepository.save(currentUser);

				try {
					SimpleMailMessage msgSend = new SimpleMailMessage();
					msgSend.setTo(currentUser.getUser().getEmail());

					msgSend.setSubject("UniPays INFO: Sie haben Geld gesendet");
					msgSend.setText(
							"Sie haben " + transfer.getAmount() + " Euro an " + targetStudent.getUser().getNachname()
									+ " " + targetStudent.getUser().getName() + "gesendet");

					javaMailSender.send(msgSend);

					SimpleMailMessage msgRecieved = new SimpleMailMessage();
					msgRecieved.setTo(targetStudent.getUser().getEmail());

					msgRecieved.setSubject("UniPays INFO: Sie haben Geld empfangen");
					msgRecieved.setText(
							"Sie haben " + transfer.getAmount() + " Euro von " + currentUser.getUser().getNachname()
									+ " " + currentUser.getUser().getName() + "empfangen");

					javaMailSender.send(msgRecieved);
				} catch (Exception e) {
					System.out.print("Email senden fehlgeschlagen");
				}

				if (oldKonto != currentUser.getAccount().getValue()) {
					return new ModelAndView("redirect:/sendMoney");
				}

			}

		} else {
			System.out.println("Not found");
		}

		List<Friend> currFriends = friendRepository.findByuserId(Long.valueOf(currentUser.getUser().getId()));

		List<User> studFriends = new ArrayList<User>();
		{
		}
		;
		for (Friend f : currFriends) {
			Optional<User> studF = userRepository.findById(f.getFriendId());
			User actStud = new User();
			if (studF.isPresent()) {

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

	/*
    
    Returns a view with the historic transfers and friends to choose from to send money
    
     */
	@RequestMapping(value = "/sendMoney")
	public ModelAndView sendMoneywoF(Model model, HttpServletRequest request, RedirectAttributes attributes) {
		StudentProfessor curUser = (StudentProfessor) request.getSession().getAttribute("studentSession");
		List<HistoricFriendTransfer> transactions = hFRepository.findBysenderId(curUser.getUser().getId()); // Out
		List<HistoricFriendTransfer> transactionsIn = hFRepository.findByreceiverId(curUser.getUser().getId());
		transactions.addAll(transactionsIn);
		List<String> sender = new ArrayList<>(), receiver = new ArrayList<>(), date = new ArrayList<>();
		for (HistoricFriendTransfer m : transactions) {

			sender.add(m.getSender());

			receiver.add(m.getReceiver());
			date.add(I18nFunctions.localizeDate(m.getDate(), LocaleContextHolder.getLocale()));
		}
		model.addAttribute("date", date);
		model.addAttribute("transactions", transactions);
		model.addAttribute("sender", sender);
		model.addAttribute("receiver", receiver);

		List<Integer> directions = new ArrayList<Integer>();
		for (HistoricFriendTransfer m : transactions) {
			if (m.getSenderid() == curUser.getAccount().getId()) {

				directions.add(1);

			} else {
				directions.add(0);

			}

		}
		model.addAttribute("directions", directions);

		List<Friend> currFriends = friendRepository.findByuserId(Long.valueOf(curUser.getUser().getId()));

		List<User> studFriends = new ArrayList<User>();
		{
		}
		;
		for (Friend f : currFriends) {
			Optional<User> studF = userRepository.findById(f.getFriendId());
			User actStud = new User();
			if (studF.isPresent()) {

				actStud = studF.get();
			}

			studFriends.add(actStud);

		}

		model.addAttribute("currfriends", currFriends);
		model.addAttribute("studfriends", studFriends);
		String msg = "notargetfriend";

		model.addAttribute("targetfriend", msg);
		return new ModelAndView("/sendToFriend");
	}

	private List<MoneyTransfer> getAffiliatedTransactions(StudentProfessor user) {

		Optional<Account> accountOptional = accountRepository.findById(user.getAccount().getId());
		if (accountOptional.isEmpty()) {
			return null;
		}
		Account account = accountOptional.get();

		List<MoneyTransfer> transfers = transferRepository.findByReceiver(account.getId());

		transfers.addAll(transferRepository.findBySender(account.getId()));
		return transfers;
	}

	private String convertName(Account account) {

		Optional<StudentProfessor> studentProfessor = studentProfessorRepository.findByAccount(account.getId());
		if (studentProfessor.isPresent()) {
			String out = studentProfessor.get().getUser().getName() + " "
					+ studentProfessor.get().getUser().getNachname();
			return out;
		}
		Optional<Corporate> corporate = corporateRepository.findByAccount(account.getId());
		if (corporate.isPresent()) {
			return corporate.get().getName();
		}
		return "";
	}

	@GetMapping("/mainpage")
	public String mainpage(Model model, HttpServletRequest request) {
		getWeather(request);
		return "mainpage";
	}

	/*
   Function which gets the current weather in Regensburg from OpenWeather API.
     */
	public void getWeather(HttpServletRequest request) {
		String key = "d4d77f62e03de4155aeda1baf768bd49";
		String city = "Regensburg";
		String uri = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=" + key;

		RestTemplate rT = new RestTemplate();
		String result = rT.getForObject(uri, String.class);

		JSONObject jo = new JSONObject(result);
		JSONObject wetObj = jo.getJSONArray("weather").getJSONObject(0);
		String wet = wetObj.getString("main");

		switch (wet) {
		case "Clouds":
			wet = "http://openweathermap.org/img/wn/02d@2x.png";
			break;
		case "Thunderstorm":
			wet = "http://openweathermap.org/img/wn/11d@2x.png";
			break;
		case "Drizzle":
			wet = "http://openweathermap.org/img/wn/09d@2x.png";
			break;
		case "Rain":
			wet = "http://openweathermap.org/img/wn/10d@2x.png";
			break;
		case "Snow":
			wet = "http://openweathermap.org/img/wn/13d@2x.png";
			break;
		case "Clear":
			wet = "http://openweathermap.org/img/wn/01d@2x.png";
			break;

		default:
			break;
		}
		request.getSession().setAttribute("wet", wet);
		double temp = jo.getJSONObject("main").getDouble("temp") - 273.15;
		double rf = jo.getJSONObject("main").getDouble("feels_like") - 273.15;
		String tempS = I18nFunctions.localizeTemperature(temp, LocaleContextHolder.getLocale());

		String rfS = I18nFunctions.localizeTemperature(rf, LocaleContextHolder.getLocale());

		request.getSession().setAttribute("temp", tempS);
		request.getSession().setAttribute("rf", rfS);

	}

}
