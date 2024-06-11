package com.example.comuneids2024.Controller;

import com.example.comuneids2024.Repository.ComuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class POIController {

    @Autowired
    private ComuneRepository comuneRepository;


}
