package com.example.projet_java;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("api")

public class Test {
    //@RequestMapping(value="/Produits", method= RequestMethod.GET)
    @GetMapping("/Produits")
    public String listeProduits() {
        return "Un exemple de produit";
    }
}
