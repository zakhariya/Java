package ua.lpr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.lpr.functions.Functions;
import ua.lpr.service.NotificationService;
import ua.lpr.service.SystemService;
import ua.lpr.service.WorkplaceService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {
/*
	Header Forgery: Be cautious with X-Forwarded-For as it can be forged if not coming from a trusted proxy.
	Spring Boot Config: You can enable automatic handling of proxy headers by
	adding server.forward-headers-strategy=native to your application.properties

	Spring MVC: Use HttpServletRequest.getRemoteAddr().
	Spring WebFlux: Use ServerHttpRequest.getRemoteAddress().
	Security: Do not trust these headers for security-critical operations (like authentication) unless your server
	is behind a trusted proxy that overwrites these headers; otherwise, they can be easily spoofed by the client
	*/
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private NotificationService notificationService;

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

	@Value("${settings.shutdown.delay}")
	private int shutdownDelay; //minutes

	@Value("${settings.reboot.delay}")
	private int rebootDelay; //minutes


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

		boolean shutdown = systemService.shutdownPC();

		if(!shutdown) {
			return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
		}

//		String requestInfo = "Request host: " + request.getHeader("host")
//				+ "\nLocal name: " + request.getLocalName()
//				+ "\nLocal address: " + request.getLocalAddr()
//				+ "\nRemote user: " + request.getRemoteUser()
//				+ "\nRemote host: " + request.getRemoteHost()
//				+ "\nRemote address: " + request.getRemoteAddr()
//				+ "\nRemote port: " + request.getRemotePort();
//
//		System.out.println(requestInfo);

		String ip = Functions.getClientIp(request);
		notificationService.notifyAdmin("System shutdown terminate in " + shutdownDelay + " minute(s). Request from: " + ip);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("reboot")
	public @ResponseBody ResponseEntity<HttpStatus> rebootPC(@RequestParam String token) {
		if (!this.token.equals(token)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		boolean reboot = systemService.rebootPC();

		if(!reboot) {
			return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
		}

		String ip = Functions.getClientIp(request);
		notificationService.notifyAdmin("System reboot terminate in " + rebootDelay + " minute(s). Request from: " + ip);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("error")
	public String showError(){

		return "error-page";
	}

}