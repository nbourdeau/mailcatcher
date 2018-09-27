package com.github.nbourdeau.mailcatcher.web;

import com.github.nbourdeau.mailcatcher.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class MessagesController {

    @Autowired
    private MessageRepository messageRepository;

    @RequestMapping("")
    public String showMessages(@RequestParam(required = false, defaultValue = "0") Integer page, Model model) {
        model.addAttribute("messages", messageRepository.findAll(PageRequest.of(page, 100)).getContent());
        model.addAttribute("page", page);
        return "messages";
    }


}
