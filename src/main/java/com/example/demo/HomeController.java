package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;


    @RequestMapping("/")
    public String listMessages(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "basepage";
    }

    @GetMapping("/add")
    public String inputMessage(Model model){
        model.addAttribute("user", new User());
        return "messagecenter";
    }

    @PostMapping("/post")
    public String postMessage(@Valid User user, BindingResult result){
        if(result.hasErrors()){
            return "messagecenter";
        }
        userRepository.save(user);
        return "redirect:/";
    }

    @RequestMapping("/messagedetails/{id}")
    public String showMessage(@PathVariable("id") long id, Model model){
        model.addAttribute("user", userRepository.findOne(id));
        return "messagedetail";
    }

    @RequestMapping("/updatemessage/{id}")
    public String updateMessage(@PathVariable("id") long id, Model model){
        model.addAttribute("user", userRepository.findOne(id));
        return "messagecenter";
    }

    @RequestMapping("/deletemessage/{id}")
    public String deleteUser(@PathVariable("id") long id){
        userRepository.delete(id);
        return "redirect:/";
    }
}
