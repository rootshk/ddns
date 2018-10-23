package top.roothk.service.domaindns.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DemoController {

    /**
     * 手动更新DNS解析
     * @return
     */
    @GetMapping("/")
    public String index() {
        return "";
    }

}
