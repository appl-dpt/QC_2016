package com.softserve.hotels.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softserve.hotels.dto.ApartmentPaymentDto;
import com.softserve.hotels.dto.PaginationInfoDto;
import com.softserve.hotels.model.Configs;
import com.softserve.hotels.model.Convenience;
import com.softserve.hotels.model.LinkPhoto;
import com.softserve.hotels.model.PaymentMethod;
import com.softserve.hotels.model.PhotoExtention;
import com.softserve.hotels.model.Role;
import com.softserve.hotels.model.User;
import com.softserve.hotels.service.ConfigsService;
import com.softserve.hotels.service.ConvenienceService;
import com.softserve.hotels.service.PaymentMethodService;
import com.softserve.hotels.service.PgDumper;
import com.softserve.hotels.service.PhotoExtentionService;
import com.softserve.hotels.service.UserService;

@Controller
@ControllerAdvice

@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private PhotoExtentionService photoExtentionService;

	@Autowired
	private ConvenienceService convenienceService;

	@Autowired
	private ConfigsService configsService;

	@Autowired
	private PaymentMethodService paymentMethodService;

	@Autowired
	@Qualifier("pgDumper")
	private PgDumper pgDumper;

	@ModelAttribute
	public void addAttributes(Model model) {
		List<User> userList = userService.findUserRenterUnapproved(Role.RENTER);
		int count = userList.size();
		model.addAttribute("count", count);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getAllUsers(Model model, @RequestParam(value = "size", defaultValue = "5") Integer pageSize,
			@RequestParam(value = "page", defaultValue = "1") Integer currentPage) {
		return "redirect:/admin/allUsers";
	}

	@RequestMapping(value = "/User", method = RequestMethod.GET)
	public String newUser(Model model) {
		User u = new User();
		model.addAttribute("user", u);
		model.addAttribute("roleList", Role.values());
		return "UserForm";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveUserByAdmin(@Valid User user, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/admin/User";
		}
		user.setEnabled(true);
		userService.create(user);
		return "redirect:/admin/allUsers";
	}

	@RequestMapping(value = { "allUsers", "*/allUsers" }, method = RequestMethod.GET)
	public String searchUserByEmail(Model model, @RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "userRole", defaultValue = "") Role role,
			@RequestParam(value = "size", defaultValue = "5") Integer pageSize,
			@RequestParam(value = "page", defaultValue = "1") Integer currentPage) {
		PaginationInfoDto userListPageInfo = new PaginationInfoDto(pageSize, currentPage);
		List<User> userList = userService.findUserLikeEmailAndByRolePageable(email, role, userListPageInfo);
		model.addAttribute("userList", userList);
		model.addAttribute("roleList", Role.values());
		model.addAttribute("email", email);
		model.addAttribute("selectedRole", role);
		model.addAttribute("lastPageIndex", userListPageInfo.getPageCount());
		model.addAttribute("currentPage", userListPageInfo.getCurrentPage());
		return "UserList";
	}

	@ResponseBody
	@RequestMapping(value = "/adminChange", method = RequestMethod.POST)
	public String updateUsers(@RequestParam(value = "enabled", required = false) Boolean enabled,
			@RequestParam(value = "role", required = false) Role role, @RequestParam(value = "email") String email) {
		User u = userService.findUserByEmail(email);
		if (role != null) {
			u.setRole(role);
		}
		if (enabled != null) {
			u.setEnabled(enabled);
		}
		userService.update(u);
		return "OK";
	}

	@RequestMapping(value = "/adminConfiguration", method = RequestMethod.GET)
	public String updateConfigsInfo(Model model) {

		List<Configs> configs = configsService.findAll();
		model.addAttribute("dtoList", configs);
		model.addAttribute("dto", new Configs());

		return "adminConfiguration";
	}

	@RequestMapping(value = "/adminConfigurationSave", method = RequestMethod.POST)
	public String updateSettings(@ModelAttribute("dto") Configs configs) {
		Configs configs1 = configsService.getConfigsByFeature(configs.getFeatures());
		configs1.setParameter(configs.getParameter());
		configsService.update(configs1);
		return "redirect:/admin/adminConfiguration";
	}

	@RequestMapping(value = "/adminExtention", method = RequestMethod.GET)
	public String getExtentions(Model model) {
		List<PhotoExtention> photoExtentions = photoExtentionService.findAll();
		model.addAttribute("photoExtentions", photoExtentions);
		model.addAttribute("deleteExt", new PhotoExtention());
		model.addAttribute("addExt", new PhotoExtention());
		return "allExtentions";
	}

	@RequestMapping(value = "/deleteExtention", method = RequestMethod.POST)
	public String deleteExtention(@ModelAttribute("deleteExt") PhotoExtention photoExtention) {
		List<PhotoExtention> photoExtentions = photoExtentionService.findAll();
		if (photoExtentions.size() > 1) {
			photoExtentionService.deleteById(photoExtention.getId());
		} else
			photoExtentionService.update(photoExtention);
		return "redirect:/admin/adminExtention";
	}

	@RequestMapping(value = "/addExtention", method = RequestMethod.POST)
	public String addExtention(@ModelAttribute("addExt") PhotoExtention photoExtention) {
		photoExtentionService.create(photoExtention);
		return "redirect:/admin/adminExtention";
	}

	@RequestMapping(value = "/adminConvenience", method = RequestMethod.GET)
	public String getConvenience(Model model) {
		List<Convenience> conveniences = convenienceService.findAll();
		model.addAttribute("conveniences", conveniences);
		model.addAttribute("deleteConvenience", new Convenience());
		model.addAttribute("addConvenience", new Convenience());
		return "allConveniences";
	}

	@RequestMapping(value = "/deleteConvenience", method = RequestMethod.POST)
	public String deleteConvenience(@ModelAttribute("deleteConvenience") Convenience conveniences) {
		convenienceService.deleteById(conveniences.getId());
		return "redirect:/admin/adminConvenience";
	}

	@RequestMapping(value = "/addConvenience", method = RequestMethod.POST)
	public String addConvenience(@ModelAttribute("addConvenience") Convenience conveniences) {
		convenienceService.create(conveniences);
		return "redirect:/admin/adminConvenience";
	}

	@RequestMapping(value = "/backup", method = RequestMethod.GET)
	public String backup(Model model) {
		model.addAttribute("photo", new LinkPhoto());
		return "backup";
	}

	@RequestMapping(value = "/dump", method = RequestMethod.GET)
	public String dump() {
		pgDumper.dump();
		return "redirect:/admin/backup";
	}

	@RequestMapping(value = "/restore", method = RequestMethod.GET)
	public String restore() {
		pgDumper.restore();
		return "redirect:/admin/backup";
	}

	@RequestMapping(value = "/compress", method = RequestMethod.GET)
	public String compress() {
		pgDumper.compress();
		return "redirect:/admin/backup";
	}

	@RequestMapping(value = "/decompress", method = RequestMethod.GET)
	public String decompress() {
		pgDumper.decompress();
		return "redirect:/admin/backup";
	}

	@RequestMapping(value = "/fullBackup", method = RequestMethod.GET)
	public String fullBackup() {
		pgDumper.fullBackup();
		return "redirect:/admin/backup";
	}

	@RequestMapping(value = "/fullRestore", method = RequestMethod.GET)
	public String fullRestore() {
		pgDumper.fullRestore();
		return "redirect:/admin/backup";
	}

	@RequestMapping(value = "/adminRenterInfo", method = RequestMethod.GET)
	public String getUnapprovedRenters(Model model, final RedirectAttributes redirectAttributes) {
		List<User> userList = userService.findUserRenterUnapproved(Role.RENTER);
		model.addAttribute("userList", userList);
		model.addAttribute("roleList", Role.values());
		int count = userList.size();
		redirectAttributes.addFlashAttribute("count", count);
		return "RenterList";
	}

	@RequestMapping(value = "/adminPayment", method = RequestMethod.GET)
	public String getPayments(Model model) {
		List<PaymentMethod> paymentMethods = paymentMethodService.findAll();
		model.addAttribute("paymentMethods", paymentMethods);
		model.addAttribute("updatePayment", new PaymentMethod());
		return "allPayments";
	}

	@RequestMapping(value = "/adminPayment/update", method = RequestMethod.POST)
	public String updatePayments(@RequestParam(value = "enabled", required = false) Boolean enabled,
	        @RequestParam(value = "payName") String paymentName) {
	    System.out.println("Payment name: " + paymentName);
		PaymentMethod pay = paymentMethodService.findPaymentMethodByName(paymentName);
		if (enabled != null) {
			pay.setEnabled(enabled);
		}
		paymentMethodService.update(pay);
		return "redirect:/admin/adminPayment";

	}
}
