package com.wittgroup.kyn.profile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RefreshScope
public class HomeController {

    @Value("${test_property}")
    private String testProperty;

    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "Hello "+testProperty +"!";
    }

}
