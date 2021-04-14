package ua.lpr.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.lpr.functions.Functions;
import ua.lpr.model.Client;
import ua.lpr.model.Setting;
import ua.lpr.model.Task;
import ua.lpr.model.User;
import ua.lpr.service.ClientService;
import ua.lpr.service.SettingService;
import ua.lpr.service.TaskService;
import ua.lpr.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("**/task")
public class TaskController {

    @Autowired
    private Environment env;

    @Autowired
    private HttpSession session;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private ClientService clientService;

    @Value("${settings.param.status}")
    private String paramStatus;

    @Value("${settings.param.task.name}")
    private String paramTaskName;

    @Value("${settings.param.status.active}")
    private String paramStatusActive;

    @Value("${settings.param.status.planned}")
    private String paramStatusPlanned;

    @Value("${settings.param.status.completed}")
    private String paramStatusCompleted;

    @Value("${setting.files.maxImageSize}")
    private int maxImageSize;


    @GetMapping("/statuses")
    public ResponseEntity<List<Setting>> getStatuses(){
        List<Setting> statuses = settingService.getByParam(paramStatus);

        List<Setting> taskStatuses = new ArrayList<>();

        for (Setting setting : statuses)
            if (setting.getObject().contains(paramTaskName))
                taskStatuses.add(setting);


        return new ResponseEntity<>(taskStatuses, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Task>> listTasks(){
        User user = (User) session.getAttribute("user");

        if(user == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        List<Task> tasks = taskService.findHodiernalByUserName(user.getName());

        if (tasks.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity showTask(@PathVariable("id") long id){

        User user = (User) session.getAttribute("user");

        Task task = taskService.findById(id);

        if(task == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        else if(user == null
                || task.getUserName() == null
                || !task.getUserName().equals(user.getName()))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        return new ResponseEntity<Task>(task, HttpStatus.OK);
    }

    @PostMapping("/state/{state}/{id}")
    public ResponseEntity changeTaskStatus(@PathVariable("state") String status, @PathVariable("id") long id){
        Task task = taskService.findById(id);

        User user = (User) session.getAttribute("user");

        if(task == null
                || task.isDeleted())
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        if(user == null
                || task.getUserName() == null
                || !task.getUserName().contains(user.getName()))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        String param = "";

        if(status.equalsIgnoreCase("complete")){
            param = paramStatusCompleted;

            task.setEndTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        } else if(status.equalsIgnoreCase("resume")) {
            param = paramStatusPlanned;

            task.setEndTime(null);
        } else {
            param = paramStatusActive;

            task.setEndTime(null);
        }

        task.setUserName(user.getName());
        task.setStatus(settingService.getByObjectAndParam(param + " " + paramTaskName, paramStatus).getValue());

        taskService.update(task);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/")
    public ResponseEntity create(@RequestBody Task task){
        User user = (User) session.getAttribute("user");

        if(user == null)
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        Client client = clientService.getByName(task.getClientName());

        if(client != null)
            task.setClientId(client.getId());

        task.setUserName(user.getName());
        task.setAddedUser(user.getName());
        task.setStatus(settingService.getByObjectAndParam(paramStatusPlanned + " " + paramTaskName, paramStatus).getValue());
        task.setStartTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        try{
            if(task.getImageData() != null
                    && task.getImageData().length() > (long) (maxImageSize * 1024 * 1024))
                task.setImageData(null);
        }catch (SQLException | NumberFormatException ex){
            ex.printStackTrace();
        }

        taskService.create(task);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Task> update(@RequestBody Task task){
        Task sTask = null;

        if(task != null)
            sTask = taskService.findById(task.getId());

        if(sTask == null
                || sTask.isDeleted())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        User user = (User) session.getAttribute("user");

        if(user == null
                || sTask.getUserName() == null
                || !sTask.getUserName().contains(user.getName()))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Client client = clientService.getByName(task.getClientName());

        if(client != null)
            task.setClientId(client.getId());

        try{
            if(task.getImageData() != null
                    && task.getImageData().length() > (long) (maxImageSize * 1024 * 1024))
                task.setImageData(null);
        }catch (SQLException | NumberFormatException ex){
            ex.printStackTrace();
        }

        if (task.getUserName() == null)
            task.setUserName(sTask.getUserName());

        task.setAddedUser(sTask.getAddedUser());
        task.setStartTime(task.getStartTime());
        task.setPlaneTime(sTask.getPlaneTime());

        if (task.getStatus().equalsIgnoreCase(
                settingService.getByObjectAndParam(paramStatusActive + " " + paramTaskName, paramStatus).getValue()))
            task.setEndTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        taskService.update(task);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

//    @GetMapping({"/", "/complete", "/resume", "/activate"})
//    public ModelAndView redirect(HttpSession session) {
//        ModelAndView modelAndView = new ModelAndView();
//
//        User user = (User) session.getAttribute("user");
//
//        try {
//            modelAndView.setViewName("redirect:/"
//                    + URLEncoder.encode(user.getPost(), "UTF-8").replace("+", "%20")
//                    + "/"
//                    + URLEncoder.encode(user.getName(), "UTF-8").replace("+", "%20"));
//        } catch (UnsupportedEncodingException | NullPointerException e) {
//            modelAndView.setViewName("redirect:/");
//        }
//
//        return modelAndView;
//    }

    @GetMapping("/{id}/image")
    public String getImageAsByteArray(@PathVariable("id") long id,
                                      HttpServletResponse response,
                                      RedirectAttributes redirectAttributes) throws IOException, SQLException {

        User user = (User) session.getAttribute("user");

        if(userService.validateUser(user)){
            InputStream in = taskService.findById(id).getImageData().getBinaryStream();
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            IOUtils.copy(in, response.getOutputStream());
        }else{
            String url = Functions.getUrlByDeep(request.getHeader("referer"), 2);

            redirectAttributes.addFlashAttribute(
                    "message", "Сессия завершена. Необходима повторная авторизация.");
            redirectAttributes.addFlashAttribute("url", url);

            return "redirect:/error";
        }

        return null;
    }
}
