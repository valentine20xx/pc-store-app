package de.niko.pcstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectController {
    public static final String CONTEXT_TO_REDIRECT_1 = "/api";

    @RequestMapping({
            CONTEXT_TO_REDIRECT_1
    })
    public String redirect2swaggeruiindexhtml() {
        return "redirect:/swagger-ui/index.html";
    }
//    @RequestMapping({
//            "/index.html",
//            "pc-store/",
//            "pc-store/pcs/**",
//    })
//    public String indexhtml() {
//        return "forward:/pc-store/index.html";
//    }
}