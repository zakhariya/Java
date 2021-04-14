package com.comodo.usertesttask.controller;

import com.comodo.usertesttask.model.User;
import com.comodo.usertesttask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/count")
    @ResponseBody
    public ResponseEntity getCount(){
        return new ResponseEntity<Long>(userService.getCount(), HttpStatus.OK);
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity listUsers(@RequestParam int limit, @RequestParam int offset){

        List<User> users = userService.listUsers(limit, offset);

        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable("id") long id) {

        User user = userService.getById(id);
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity createUser(@RequestBody User user) {//, UriComponentsBuilder ucBuilder) {
        if(user.getFirstName().length() < 1 | user.getLastName().length() < 1 | user.getGender().length() < 1
                | user.getBirthDay().getTime() > Calendar.getInstance().getTimeInMillis()){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        if(userService.userExists(user))
           return new ResponseEntity(HttpStatus.CONFLICT);

        userService.addUser(user);

 //       HttpHeaders headers = new HttpHeaders();
 //       headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity updateUser(@RequestBody User user) {
        if (userService.getById(user.getUserId()) == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        userService.updateUser(user);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable long id){
        User user = userService.getById(id);
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        userService.deleteUser(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
}
