package org.thane.AnimeDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thane.AnimeDB.entities.UserAnime;
import org.thane.AnimeDB.model.SuggestionRequest;
import org.thane.AnimeDB.repositories.UserRepository;

@Controller
public class HomepageController {

    private UserRepository repository;

    @Autowired
    public HomepageController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String index(@ModelAttribute SuggestionRequest suggestionRequest, Model model) {
        model.addAttribute("suggestionRequest", suggestionRequest);
        return "homepage";
    }

    @PostMapping("/suggestions")
    public String postSuggestions(Model model) {
        SuggestionRequest suggestionRequest = (SuggestionRequest) model.asMap().get("suggestionRequest");
        model.addAttribute("accountName", suggestionRequest.getMalusername() + "'s Suggestions");
        model.addAttribute("suggestion", new UserAnime().create(suggestionRequest));
        return "suggestions";
    }

    @GetMapping("/users")
    public String test(Model model) {
        model.addAttribute("users", repository.findAll());
        return "users";
    }
}
