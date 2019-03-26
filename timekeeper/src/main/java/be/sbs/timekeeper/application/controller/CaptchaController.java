package be.sbs.timekeeper.application.controller;


import be.sbs.timekeeper.application.exception.ForbiddenException;
import be.sbs.timekeeper.application.service.CaptchaService;
import be.sbs.timekeeper.application.valueobjects.CaptchaInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Autowired
    CaptchaService captchaService;

    @PostMapping
    public String validateCaptcha(@RequestBody CaptchaInput input) {
        Boolean isValidCaptcha = captchaService.validateCaptcha(input.getCaptchaResponse());
        if (!isValidCaptcha) {
            throw new ForbiddenException("Captcha is not valid");
        }
        return input.getCaptchaResponse();
    }
}
