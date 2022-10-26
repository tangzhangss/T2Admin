package com.tangzhangss.commonutils.uidgenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test/UidGenerator")
public class UidGeneratorTestController {
    @Autowired
    private UidGeneratorService uidGenService;

    @GetMapping("/testuid")
    public String test() {
        return String.valueOf(uidGenService.getuid());
    }
}
