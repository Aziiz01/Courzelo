package tn.esprit.pidev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.pidev.services.Interfaces.IService;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    IService iService;

}
