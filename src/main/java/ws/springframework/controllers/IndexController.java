package ws.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
// âs toucher
@Controller
public class IndexController {
    @RequestMapping("/")
    String index() {
        return "index";
    }
}
