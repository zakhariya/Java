package ua.lpr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.lpr.service.TaskService;
import ua.lpr.service.UserService;
import ua.lpr.service.WorkplaceService;

@Controller
public class MainController {

	@Autowired
    private WorkplaceService workplaceService;

	@RequestMapping(value = { "/", "/welcome**" , "/hello**"}, method = RequestMethod.GET)
	public ModelAndView defaultPage() throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Главная");
		model.addObject("message", "Выберите свою должность:");
		model.addObject("workplaces", workplaceService.getAll());

		model.setViewName("main-page");

		return model;
	}

	@GetMapping("error")
	public String showError(){

		return "error-page";
	}

}