package runtime;

import cn.hutool.core.util.RandomUtil;
import com.tangzhangss.commonutils.utils.BaseUtil;
import com.tangzhangss.commonutils.utils.RuntimeUtil;
import org.junit.platform.commons.util.StringUtils;

import java.io.IOException;

public class ServerStatus {
    public static void main(String[] args) throws IOException, InterruptedException {
        String s = "tcp        0      0 192.168.1.172:22        192.168.1.81:49638      ESTABLISHED\r\n" +
                "tcp        0      0 192.168.1.172:22        192.168.1.81:42692      ESTABLISHED\r\n" +
                "tcp6       0      0 192.168.1.172:55972     192.168.1.195:3306      ESTABLISHED\r\n" +
                "tcp6       0      0 192.168.1.172:55976     192.168.1.195:3306      ESTABLISHED\r\n" +
                "tcp6       0      0 192.168.1.172:58398     192.168.1.195:3306      ESTABLISHED\r\n" +
                "tcp6       0      0 192.168.1.172:48882     192.168.1.195:3306      ESTABLISHED\r\n" +
                "tcp6       0      0 192.168.1.172:58396     192.168.1.195:3306      ESTABLISHED\r\n" +
                "tcp6       0      0 192.168.1.172:58318     192.168.1.195:3306      ESTABLISHED\r\n" +
                "tcp6       0      0 192.168.1.172:59992     192.168.1.115:11526     ESTABLISHED\r\n" +
                "tcp6       0      0 192.168.1.172:9092      192.168.1.115:36314     ESTABLISHED";

        System.out.println(RuntimeUtil.parseNetstatCmdRes(s));



    }
}
