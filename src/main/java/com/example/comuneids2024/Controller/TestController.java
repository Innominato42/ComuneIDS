package com.example.comuneids2024.Controller;

import com.example.comuneids2024.Model.DTO.POIDTO;
import com.example.comuneids2024.Model.POI;
import com.example.comuneids2024.Model.Tipo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
 @RestController
 public class TestController {
     @PostMapping("/testPOI")
     public ResponseEntity<Object> testPOI(@RequestBody POIDTO poi) {
         if (poi.getTipo() == null) {

             return new ResponseEntity<>("null",HttpStatus.BAD_REQUEST);
         }
         return new ResponseEntity<>(poi, HttpStatus.OK);
     }
 }

