package com.dragon.system.controller;

import com.dragon.logging.annotation.Log;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/echo")
public class EchoController {

    @Log("新增字典")
    @ApiOperation("测试日志")
    @GetMapping("/testLog")
    public ResponseEntity<Object> testLog() {
        return new ResponseEntity<>("testLog", HttpStatus.OK);
    }

}
