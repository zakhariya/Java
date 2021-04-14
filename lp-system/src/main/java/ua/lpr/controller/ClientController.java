package ua.lpr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.lpr.model.Client;
import ua.lpr.model.User;
import ua.lpr.service.ClientService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("**/client")
public class ClientController {

    @Autowired
    HttpSession session;

    @Autowired
    ClientService clientService;


    @GetMapping("/list")
    public ResponseEntity listClients(){
        User user = (User) session.getAttribute("user");

        if(user == null)
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        List<Client> clients = clientService.getAll();

        if(clients.isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);

        return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
    }

    @GetMapping("/list-limit")
    public ResponseEntity listClients(@RequestParam int limit, @RequestParam int offset){

        List<Client> clients = clientService.getLimited(limit, offset);

        if(clients.isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);

        return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
    }

    @GetMapping("/list-like")
    public ResponseEntity listClients(@RequestParam(name = "partsOfName") String[] partsOfName){
        User user = (User) session.getAttribute("user");

        if(user == null)
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        List<Client> clients = clientService.getByNameLike(partsOfName);

        if(clients.isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);

        return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
    }
}
