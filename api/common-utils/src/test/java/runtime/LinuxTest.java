package runtime;


import com.tangzhangss.commonutils.utils.BaseUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class LinuxTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        String s = BaseUtil.executeRuntimeCommand("ls -l");
        System.out.println("运行结果:"+s);
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
