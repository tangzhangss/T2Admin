package com.tangzhangss.commonservice.monitor;

import com.tangzhangss.commonutils.resultdata.Result;
import com.tangzhangss.commonutils.server.Server;
import com.tangzhangss.commonutils.utils.BaseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitor")
public class MonitorApi {
    @GetMapping("/system")
    public Result base() throws Exception {
        Server server = new Server();
        server.copyTo();

        return Result.ok().data(server);
    }
}
