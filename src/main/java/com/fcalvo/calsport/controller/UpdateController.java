package com.fcalvo.calsport.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller is used to update database when needed
 */

@RestController
@RequestMapping("/rest/update")
public class UpdateController {


    @GetMapping("")
    public String updateDataBase(HttpServletResponse response){

        return "OK";
    }
    
}
