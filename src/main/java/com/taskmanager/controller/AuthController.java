package com.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taskmanager.model.Users;
import com.taskmanager.service.UserService;

@Controller
//@RequestMapping("/tasks")	
public class AuthController {

	 @Autowired
	    private UserService userService;

	    @GetMapping("/login")
	    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
	        return "login";
	    }

	    @GetMapping("/register")
	    public String showRegistrationForm(Model model) {
	        model.addAttribute("user", new Users());
	        return "register";
	    }
	    
		/*
		 * @PostMapping("/register") public String register(@ModelAttribute("user")
		 * Users users) { userService.register(users); return "redirect:/login?success";
		 * }
		 */
	    
	    @PostMapping("/register")
	    public String register(@ModelAttribute("user") Users user,
	                           @RequestParam("confirmPassword") String confirmPassword,
	                           Model model) {
	        if (!user.getPassword().equals(confirmPassword)) {
	            model.addAttribute("error", "Passwords do not match");
	            return "register";
	        }

	        if (userService.userExists(user.getUsername(), user.getEmail())) {
	            model.addAttribute("error", "Username or Email already exists");
	            return "register";
	        }

	        userService.save(user);
	        return "redirect:/login?registerSuccess";
	    }

}
