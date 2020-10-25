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
    public  List<String> CreateKey(@RequestBody KeyGeneratorEntity key) {
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
            return list;
        }else{
            return list;
        }
       /* System.out.println(key.getTaille());
        return key.getTaille();*/



    }



    @PostMapping("/encrypt")
    public  String ChiffrementMessageSmetrique(@RequestBody KeyGeneratorEntity key) {


        return key.ChiffrementSymetriqueMessage(key.getClepriv(),key.getMessage(),key.getAlgorithme());


    }
    @PostMapping("/encryptasymetrique")
    public  String ChiffrementMessageAsymetrique(@RequestBody KeyGeneratorEntity key) {

        return key.ChiffrementAsymetriqueMessage(key.getMessage(),key.getClepriv(),key.getClepub(),key.getAlgorithme());

    }
    @PostMapping("/encryptasymetriquefile")
    public  String ChiffrementFichierAsymetrique(@RequestBody KeyGeneratorEntity key) {

        return key.ChiffrementFichierAsymetrique(key.getAlgorithme(),key.getFile(),key.getTaille() );

    }
    @PostMapping("/encryptfile")
    public  String ChiffrementFichierSmetrique(@RequestBody KeyGeneratorEntity key) {


        return key.ChiffrerFichierSymetrique(key.getAlgorithme(),key.getFile(),key.getTaille());




    }

    @PostMapping("/signature")
    public  String signerMessage(@RequestBody KeyGeneratorEntity key) throws Exception {
        return key.SignatureMeassage(key.getAlgorithme(),key.getHashingAlgo(),key.getSigningAlgo(),key.getClepriv(),key.getMessage());


    }

    @PostMapping("/signaturefichier")
    public  String signerFichier(@RequestBody KeyGeneratorEntity key) throws Exception {
        return key.SignatureDigitalFichier(key.getAlgorithme(),key.getTaille(),key.getFile(),key.getHashingAlgo(),key.getSigningAlgo());


    }

    @PostMapping("/dechiffrer")
    public  String Dechifrer(@RequestBody KeyGeneratorEntity key) throws Exception {
        return key.Dechiffrement(key.getAlgorithme(),key.getClepriv(),key.getTextCipher());


    }


















}