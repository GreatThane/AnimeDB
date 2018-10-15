package org.thane.AnimeDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thane.AnimeDB.repositories.UserRepository;
import org.thane.AnimeDB.scrapers.UserScraper;

@Controller
public class ScrapeController {

    private UserScraper userScraper;
    private UserRepository userRepository;

    @Autowired
    public ScrapeController(UserScraper userScraper, UserRepository userRepository) {
        this.userScraper = userScraper;
        this.userRepository = userRepository;
    }

    @GetMapping("/scrapeusers")
    public String scrapeUsers(Model model) {
        userScraper.userPageScrape();
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }

}
