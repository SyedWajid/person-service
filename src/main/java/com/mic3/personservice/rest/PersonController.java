package com.mic3.personservice.rest;


import com.mic3.personservice.service.IPersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PersonController {

    IPersonService personService;

    public PersonController(IPersonService personService){
        this.personService = personService;
    }
    //private final Logger logger = LogManager.getLogger(this.getClass());
    @GetMapping(value = "test")
    public String test(){
        log.info("testing2");
        log.info("persons are " + personService.getPersons());
        return "Hello World2!";
    }
}
