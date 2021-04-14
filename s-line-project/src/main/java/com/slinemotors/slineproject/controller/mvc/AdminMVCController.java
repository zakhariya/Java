package com.slinemotors.slineproject.controller.mvc;

import com.slinemotors.slineproject.entity.Employee;
import com.slinemotors.slineproject.entity.Position;
import com.slinemotors.slineproject.entity.Role;
import com.slinemotors.slineproject.entity.User;
import com.slinemotors.slineproject.service.EmployeeService;
import com.slinemotors.slineproject.service.PositionService;
import com.slinemotors.slineproject.service.RoleService;
import com.slinemotors.slineproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminMVCController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PositionService positionService;

    @GetMapping
    public String mainPage(Model model) {
        model.addAttribute("title", "admin panel | Main");

        return "admin/index";
    }

    @GetMapping("users")
    public String usersPage(Model model) {
        List<User> users = userService.findAll();
        List<Employee> employees = employeeService.findAll();
        List<Role> roles = roleService.findAll();

        model.addAttribute("title", "admin panel | Users");
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        model.addAttribute("employees", employees);

        return "admin/users";
    }

    @GetMapping("employees")
    public String employeesPage(Model model) {
        List<Employee> employees = employeeService.findAll();
        List<Position> positions = positionService.findAll();

        model.addAttribute("title", "admin panel | Employees");
        model.addAttribute("employees", employees);
        model.addAttribute("positions", positions);

        return "admin/employees";
    }

    @GetMapping("works")
    public String worksPage(Model model) {
        model.addAttribute("title", "admin panel | Works");

        return "admin/works";
    }
}
