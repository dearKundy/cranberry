package com.kundy.justbattle.controller;

import com.kundy.justbattle.distributedlock.DbDistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kundy
 * @date 2019/8/20 9:28 AM
 */
@Slf4j
@RestController
public class MainController {

    @Value("${server.port}")
    private String port;

    @Autowired
    private DbDistributedLock dbDistributedLock;

    @GetMapping("/testDbDistributedLock")
    public String testDbDistributedLock() {
        if (this.dbDistributedLock.lockWithBlock()) {
            log.info("hey man , i come in.... 端口号：" + port);
            this.dbDistributedLock.unLock();
        }
        return "success";
    }

}
