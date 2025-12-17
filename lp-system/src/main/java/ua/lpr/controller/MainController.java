package ua.lpr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.lpr.service.SystemService;
import ua.lpr.service.WorkplaceService;

@Controller
public class MainController {

	@Autowired
    private WorkplaceService workplaceService;

	@Autowired
	private SystemService systemService;

	@Value("${security.token}")
	private String token;

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

	@GetMapping("shutdown")
	public @ResponseBody
	ResponseEntity<HttpStatus> shutdownPC(@RequestParam String token) {
		if (!this.token.equals(token)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		systemService.shutdownPC();

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("reboot")
	public @ResponseBody ResponseEntity<HttpStatus> rebootPC(@RequestParam String token) {
		if (!this.token.equals(token)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		systemService.rebootPC();

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("error")
	public String showError(){

		return "error-page";
	}

}