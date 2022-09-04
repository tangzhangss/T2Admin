package io.report.modules.ser.excontroller.conn;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.report.common.utils.R;
import io.report.common.utils.Target;

/**
 * 测试服务网是否启动
 * @author JIZH
 */
@RestController
@RequestMapping("/ex/server")
public class TestConnectionController {
    @RequestMapping("/testconnection")
    public R testConnection(@RequestParam Map<String, Object> params){
        return R.ok();
    }
    @RequestMapping("/pid")
    public R getProcessID(@RequestParam Map<String, Object> params){
    	int pid = Target.getProcessID();
        return R.ok().put("pid", pid);
    }
}
