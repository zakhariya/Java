package ua.lpr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.lpr.model.Task;
import ua.lpr.model.User;
import ua.lpr.service.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private WorkplaceService workplaceService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private Environment env;

    @Autowired
    private HttpSession session;

    @PostConstruct
    private void runNotification(){
        notificationService.start(userService, taskService, settingService);
    }

    @GetMapping(value = "/{post}")
    public ModelAndView toLoginPage(@PathVariable(value = "post") String post){

        User sUser = (User) session.getAttribute("user");

        if(sUser != null && post.equals(sUser.getPost())){
            try {
                return new ModelAndView("redirect:"
                        + "/"
                        + URLEncoder.encode(sUser.getPost(), "UTF-8").replace("+", "%20")
                        + "/"
                        + URLEncoder.encode(sUser.getName(), "UTF-8").replace("+", "%20"));
            } catch (UnsupportedEncodingException | NullPointerException e) {
                return  new ModelAndView("redirect:/");
            }
        }

        List<User> usersByPost = userService.getAliveUsersByPost(post);

        if(usersByPost.size() < 1)
            return new ModelAndView("redirect:/");

        Map<Integer, String> users = new HashMap<Integer, String>();

        for(User user : usersByPost)
            users.put(user.getId(), user.getName());

        User user = new User();
        user.setPost(post);

        ModelAndView model = new ModelAndView("login-page", "command", user);
        model.addObject("title", "Вход");
        model.addObject("message", "Введите учетные данные:");
        model.addObject("usersMap", users);

        return model;
    }


//    @GetMapping(value = "/login")
//    public ModelAndView login(){
//
//        return new ModelAndView("redirect:/");
//    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user){
        session.removeAttribute("user");

        User dbUser = userService.getById(user.getId());

        if(dbUser == null
                || dbUser.isDead() == true
                || !dbUser.getName().equals(user.getName()))
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        if(userService.validateUser(user))
            user = userService.getByLogin(user.getName());
        else
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        session.setAttribute("user", user);
        userService.setLastLoginTime(user);

        User rUser = new User();

        rUser.setName(user.getName());
        rUser.setPost(user.getPost());

        return new ResponseEntity<> (rUser, HttpStatus.OK);
    }

    @GetMapping("/{post}/{name}")
    public ModelAndView showPage(@PathVariable("post") String post,
                                 @PathVariable("name") String name) throws UnsupportedEncodingException {

        ModelAndView model = new ModelAndView();

        User sUser = (User) session.getAttribute("user");

        User user = userService.getByLogin(name);

        if(!workplaceService.isPostExists(post)){

            model.setViewName("redirect:/");

        }else if((sUser == null | user == null)
                    | ((sUser != null & user != null)
                    && (user.getId() != sUser.getId() | !user.getName().equals(sUser.getName())
                    | !user.getPassword().equals(sUser.getPassword())
                    | !user.getPost().equals(post) | !user.getPost().equals(sUser.getPost())))){

            session.removeAttribute("user");
            model.setViewName("redirect:/" + URLEncoder.encode(post, "UTF-8").replace("+", "%20"));

        }else{
            try{
                String page = settingService.getByObjectAndParam(post, "page").getValue();
                String message = settingService.getByObjectAndParam(page, "message").getValue();

                model.addObject("title", user.getName());
                model.addObject("message", message);
                model.addObject("user", user);
                model.addObject("maxFileSize", env.getProperty("settings.files.maxImageSize"));
                model.addObject("plannedStatus", env.getProperty("settings.param.status.active"));
                model.addObject("activeStatus", env.getProperty("settings.param.status.planned"));
                model.addObject("completedStatus", env.getProperty("settings.param.status.completed"));

                model.setViewName("sections/" + page);

            }catch (NullPointerException ex){
                model.addObject("title", "Ошибка");
                model.addObject("message", "Не найдено подходящего представления для: " + user.getPost() + " " + user.getName());
                model.setViewName("error-page");
            }
        }

        return model;
    }

    @RequestMapping("/logout")
    public ModelAndView logout(){

        session.removeAttribute("user");

        return new ModelAndView("redirect:/");
    }
}
