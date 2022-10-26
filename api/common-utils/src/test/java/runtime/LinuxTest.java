package runtime;


import com.tangzhangss.commonutils.utils.runtime.RuntimeUtil;

import java.io.*;
import java.util.LinkedList;

public class LinuxTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        String s = RuntimeUtil.executeRuntimeCommand("tasklist /nh");
        System.out.println(RuntimeUtil.parseTasklistCmdRes(s));
    }

    public static String readInputStream(InputStream inputStream, String charset) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        byte[] getData =  bos.toByteArray();
        inputStream.read(getData);
        //关闭流
        bos.close();
        inputStream.close();

        return new String(getData,charset);
    }
}
