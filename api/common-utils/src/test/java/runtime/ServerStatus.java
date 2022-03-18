package runtime;

import cn.hutool.core.util.RandomUtil;
import com.tangzhangss.commonutils.utils.BaseUtil;
import com.tangzhangss.commonutils.utils.RuntimeUtil;
import org.junit.platform.commons.util.StringUtils;

import java.io.IOException;

public class ServerStatus {
    public static void main(String[] args) throws IOException, InterruptedException {
        String s = "root       48761  0.0  0.0 113416  1620 ?        S    1月06   0:00 /bin/sh /usr/local/mysql/bin/mysqld_safe --datadir=/data/mysqldata --pid-file=/data/mysqldata/localhost.localdomain.pid\r\n" +
                "mysql      49025  0.5  0.4 2633832 581640 ?      Sl   1月06 527:05 /usr/local/mysql/bin/mysqld --basedir=/usr/local/mysql --datadir=/data/mysqldata --plugin-dir=/usr/local/mysql/lib/plugin --user=mysql --log-error=localhost.localdomain.err --pid-file=/data/mysqldata/localhost.localdomain.pid --socket=/tmp/mysql.sock --port=3306";

        RuntimeUtil.parsePsAuxCmdRes(s);


    }
}
