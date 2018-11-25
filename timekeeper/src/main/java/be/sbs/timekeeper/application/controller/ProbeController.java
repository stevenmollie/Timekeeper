package be.sbs.timekeeper.application.controller;

import be.sbs.timekeeper.application.valueobjects.Probe;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/")
public class ProbeController {

    @GetMapping(path = "live", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Probe IsAlive(){
        return new Probe("service is alive");
    }

}
