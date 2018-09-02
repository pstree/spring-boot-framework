package org.qian.business.order.controller;


import io.swagger.annotations.Api;
import org.qian.business.order.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Test")
@Api(description = "测试")
public class TestController {

    @Autowired
    private TestService testService;

}