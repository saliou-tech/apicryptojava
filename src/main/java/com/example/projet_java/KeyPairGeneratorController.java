package com.example.projet_java;

import Entity.KeyGeneratorEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api")

public class KeyPairGeneratorController {
    @GetMapping("/")
    public String  getKeyPairGenerator(){
        KeyGeneratorEntity key = new KeyGeneratorEntity();
        if(key.loadkey()){
            return "la cle est charge avec succes ";
        }else{
            return  "une erreur est survenue lors du chargement de la cle";
        }
       /* List<String> list = key.createKeys("RSA", "SunRsaSign", 1024);
        System.out.println(list.get(0)+list.get(1));*/

     /*   if(key.saveKey(list.get(0),list.get(1))){
            return  "cle sauvegarde avec succes";
        }else{
            return  "une erreur es survenue";
        }*/


    }

    //ajouter un produit
    @PostMapping("/generate")
    public  String CreateKey(@RequestBody KeyGeneratorEntity key) {
        List<String> list = null;
        System.out.println( "algo recu "+key.getAlgorithme());
        if (key.getAlgorithme().equals("RSA")||key.getAlgorithme().equals("DSA")||key.getAlgorithme().equals("DH"))
        {
            System.out.println(("c'est asymetrique"));

             list = key.createKeys(key.getAlgorithme(), key.getProvider(), key.getTaille(),true);
        }
        else {
            System.out.println(("c'est symetrique"));
            list = key.createKeys(key.getAlgorithme(), key.getProvider(), key.getTaille(),false);
        }



        System.out.println(list.get(0)+list.get(1));

        if(key.saveKey(list.get(0),list.get(1))){
            return list.get(0);
        }else{
            return "une erreur est survenue lors de la generation des clés";
        }
       /* System.out.println(key.getTaille());
        return key.getTaille();*/



    }



    @PostMapping("/encrypt")
    public  String ChiffrementMessageSmetrique(@RequestBody KeyGeneratorEntity key) {


       return key.ChiffrementSymetriqueMessage(key.getClepriv(),key.getMessage(),key.getAlgorithme());




    }




}