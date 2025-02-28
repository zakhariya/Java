package ua.lpr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.lpr.model.Client;
import ua.lpr.service.TaskService;
import ua.lpr.service.UserService;
import ua.lpr.service.WorkplaceService;

import java.util.List;

@Controller
public class MainController {

	@Autowired
    private WorkplaceService workplaceService;

	@Value("${settings.files.server.address}")
	private String fileServerAddr;

	@Value("${settings.files.server.logotypes.folder}")
	private String logoFilesDir;


	@RequestMapping(value = { "/", "/welcome**" , "/hello**"}, method = RequestMethod.GET)
	public ModelAndView defaultPage() throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Главная");
		model.addObject("message", "Выберите свою должность:");
		model.addObject("workplaces", workplaceService.getAll());

		model.setViewName("main-page");

		return model;
	}

	@GetMapping("logo_files_path")
	public ResponseEntity<String> getLogoFilesPath() {
		String sep = System.getProperty("file.separator");
		String path = sep + sep + fileServerAddr + sep + new String(logoFilesDir.getBytes());

		System.out.println(path);

		return new ResponseEntity<>(path, HttpStatus.OK);
	}

	@GetMapping("error")
	public String showError(){

		return "error-page";
	}

}