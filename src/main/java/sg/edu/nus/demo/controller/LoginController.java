package sg.edu.nus.demo.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import sg.edu.nus.demo.model.Administrator;
import sg.edu.nus.demo.model.Employee;
import sg.edu.nus.demo.model.Manager;
import sg.edu.nus.demo.repo.AdministratorRepository;
import sg.edu.nus.demo.repo.EmployeeRepository;
import sg.edu.nus.demo.repo.ManagerRepository;
import sg.edu.nus.demo.service.LoginService;

@Controller
public class LoginController {
	
	
	@Autowired
	LoginService loginService;
	
	@Resource
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ManagerRepository managerRepository;
	
	@Autowired
	private AdministratorRepository administratorRepository;
	
	//Employee login
	@GetMapping("/login/employee")
	public String loginEmployee(Model model) {
		
		model.addAttribute("employee", new Employee());
	    return "loginemployee"; //to see login page
	}
	
	@PostMapping("/authenticateEmployee")
	public String authenticateEmployee(Model model, @Valid @ModelAttribute Employee employee, BindingResult bindingResult) {
		
		// After keying in the username and password
		boolean loginTrueFalse = loginService.authenticateEmployee(employee.getUserId(), employee.getPassword());

		if(loginTrueFalse) {
			Employee emp = employeeRepository.findEmployeeByUserId(employee.getUserId());		
			model.addAttribute("employee", emp);
			return "index";
		}
		else if (bindingResult.hasErrors()) {
            return "loginemployee";
		}	
		else {
			model.addAttribute("logError","logError");
			return "loginemployee";
		}
	}
	
	//Manager login
	@GetMapping("/login/manager")
	public String loginManager(Model model) {
		
		model.addAttribute("manager", new Manager());
	    return "loginmanager";
	}
	
	@PostMapping("/authenticateManager")
	public String authenticateManager(Model model, @Valid @ModelAttribute Manager manager, BindingResult bindingResult) {
		// After keying in the username and password
		boolean loginTrueFalse = loginService.authenticateManager(manager.getUserid(), manager.getPassword());

		if(loginTrueFalse) {
			Manager mgr = managerRepository.findManagerByUserId(manager.getUserid());
			model.addAttribute("manager", mgr);
			return "index_mgr";
		}
		else if (bindingResult.hasErrors()) {
            return "loginmanager";
		}	
		else {
			model.addAttribute("logError","logError");
			return "loginmanager";
		}
	}
	
	//Administrator login
	@GetMapping("/login/administrator")
	public String loginAdministrator(Model model) {
		
		model.addAttribute("administrator", new Administrator());
	    return "loginadministrator";
	}
	
	@PostMapping("/authenticateAdministrator")
	public String authenticateAdministator(Model model, @Valid @ModelAttribute Administrator administrator, BindingResult bindingResult) {
		// After keying in the username and password
		boolean loginTrueFalse = loginService.authenticateAdministrator(administrator.getUserId(), administrator.getPassword());

		if(loginTrueFalse) {
			Administrator admin = administratorRepository.findAdministratorByUserId(administrator.getUserId());
			model.addAttribute("administrator", administrator);
			return "admin_homepage";
		}
		else if (bindingResult.hasErrors()) {
            return "loginadministrator";
		}	
		else {
			model.addAttribute("logError","logError");
			return "loginadministrator";
		}
	}
}
