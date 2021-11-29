package com.codegym.cms.controller;

import com.codegym.cms.model.Customer;
import com.codegym.cms.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class CustomerController {

    @Autowired
    private ICustomerService customerService;
    @GetMapping("/customers")
    public ModelAndView showList(){
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        modelAndView.addObject("customerList",customerService.findAll());
        return modelAndView;
    }

    @GetMapping("/create-customer")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }
@GetMapping("/{id}/edit-customer")
public ModelAndView showEditForm(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("/customer/edit");
        modelAndView.addObject("customer",customerService.findById(id));
        return modelAndView;
}
@GetMapping("/{id}/delete-customer")
public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        customerService.remove(id);
        redirectAttributes.addFlashAttribute("success","Customer was deleted");
        return "redirect:/customers";
}
@PostMapping("/edit-customer")
public ModelAndView update(@ModelAttribute Customer customer){
        ModelAndView modelAndView = new ModelAndView("/customer/edit");
        customerService.save(customer);
        modelAndView.addObject("customer",customer);
        modelAndView.addObject("message","Customer was updated!");
        return modelAndView;
}
    @PostMapping("/create-customer")
    public ModelAndView saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("message", "New customer created successfully");
        return modelAndView;
    }
}