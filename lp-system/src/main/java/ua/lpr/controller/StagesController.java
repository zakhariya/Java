package ua.lpr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.lpr.model.Setting;
import ua.lpr.model.Stage;
import ua.lpr.model.User;
import ua.lpr.service.SettingService;
import ua.lpr.service.StageService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("**/stage")
public class StagesController {

    @Autowired
    private HttpSession session;

    @Autowired
    private StageService stageService;

    @Autowired
    private SettingService settingService;

    @Value("${settings.param.status}")
    private String paramStatus;

    @Value("${settings.param.stage.name}")
    private String paramStageName;


    @GetMapping("/statuses")
    public ResponseEntity<List<Setting>> getStatuses(){
        List<Setting> statuses = settingService.getByParam(paramStatus);

        List<Setting> stageStatuses = new ArrayList<>();

        for (Setting setting : statuses)
            if (setting.getObject().indexOf(paramStageName) > -1)
                stageStatuses.add(setting);

        return new ResponseEntity<>(stageStatuses, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Stage>> list(@RequestParam(name = "type") String type, @RequestParam(name="userName") String userName){

        User user =  (User) session.getAttribute("user");

        if(user == null
                || (userName.length() > 0 && !userName.equals(user.getName())))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        List<Stage> stages = null;

        if(type.equalsIgnoreCase("all") && userName.length() < 1)
            stages = stageService.getAll();
        else if(type.equalsIgnoreCase("in-progress") && userName.length() < 1)
            stages = stageService.getAllInProgress();
        else if(type.equalsIgnoreCase("all") && userName.length() > 0)
            stages = stageService.getAllByUserName(user.getName());
        else if(type.equalsIgnoreCase("in-progress") && userName.length() > 0)
            stages = stageService.getActualByUserName(user.getName());

        if(stages == null || stages.isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(stages, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity showStage(@PathVariable long id){
        Stage stage = stageService.getById(id);

        User user =  (User) session.getAttribute("user");

        if(stage == null
                || stage.isDeleted() == true)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        if(user == null
                || stage.getUserNames() == null
                || stage.getUserNames().isEmpty()
                || !stage.getUserNames().contains(user.getName()))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(stage, HttpStatus.OK);
    }

    @PostMapping("/state/{state}/{id}")
    public ResponseEntity changeStageState(@PathVariable String state, @PathVariable long id) {
        Stage stage = stageService.getById(id);

        User user =  (User) session.getAttribute("user");

        if(stage == null
                || stage.isDeleted() == true)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        if(user == null
                || stage.getUserNames() == null
                || stage.getUserNames().isEmpty()
                || !stage.getUserNames().contains(user.getName()))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if(state.equalsIgnoreCase("complete"))
            stageService.complete(id);
        else
            stageService.resume(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
