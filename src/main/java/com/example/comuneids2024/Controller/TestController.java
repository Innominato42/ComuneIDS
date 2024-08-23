package com.example.comuneids2024.Controller;

import com.example.comuneids2024.Model.Comune;
import com.example.comuneids2024.Model.DTO.POIDTO;
import com.example.comuneids2024.Model.POI;
import com.example.comuneids2024.Model.Tipo;
import com.example.comuneids2024.Repository.ComuneRepository;
import com.example.comuneids2024.Repository.POIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
 public class TestController {

    @Autowired
     private ComuneRepository comuneRepository;

    @Autowired
     private POIRepository poiRepository;
     @PostMapping("/testPOI")
     public ResponseEntity<Object> testPOI(@RequestBody POIDTO poi) {
         if (poi.getTipo() == null) {

             return new ResponseEntity<>("null",HttpStatus.BAD_REQUEST);
         }
         return new ResponseEntity<>(poi, HttpStatus.OK);
     }

     @GetMapping("/testGetPOI")
     public POI testGetPOI(@RequestParam("idComune") String idComune, @RequestParam("idPOI") String idPOI)
     {
        Comune c=comuneRepository.findById(idComune).orElse(null);
         if(c==null)
         {
             throw  new NullPointerException();
         }
         return c.getPOI(idPOI);

     }
 }

