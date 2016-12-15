package spring.data.sample.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import spring.data.sample.entity.User;
import spring.data.sample.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/list")
	public String list(@RequestParam(value = "name", defaultValue = "") String name, Model model) {
		List<User> users = userService.list(name);
		model.addAttribute("users", users);
		return "list";
	}

	@RequestMapping(value = "/delete/{id}")
	@ResponseBody
	public Object delete(@PathVariable(value = "id") String id) {
		userService.delete(id);
		return null;
	}

	@RequestMapping(value = "/update/{id}")
	public String updateForm(@PathVariable("id") String id, Model model) {
		User user = userService.getUser(id);
		model.addAttribute("user", user);
		model.addAttribute("action", "update");
		model.addAttribute("actionName", "更新用户");
		return "userForm";
	}

	@RequestMapping(value = "/update")
	public String update(@ModelAttribute("user") User user) {
		userService.update(user);
		return "redirect:/list";
	}

	@RequestMapping(value = "/insert")
	public String insertForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("action", "insert");
		model.addAttribute("actionName", "添加用户");
		return "userForm";
	}

	@RequestMapping(value = "/insert")
	public Object insert(@ModelAttribute("user") User user) {
		user.setCreatedAt(new Date());
		userService.save(user);
		return "redirect:/list";
	}
}
