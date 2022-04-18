package runtime;


import com.tangzhangss.commonutils.server.Sys;
import com.tangzhangss.commonutils.utils.BaseUtil;
import com.tangzhangss.commonutils.utils.RuntimeUtil;

import java.io.*;
import java.util.LinkedList;

public class LinuxTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        LinkedList<String> resBuffer = new LinkedList();
        RuntimeUtil.executeRuntimeCommand("java -jar D:\\DEVWORK\\nsmap_ccplatform\\nsmap_cyplatform_admin\\target\\nsmap_cyplatform_admin.jar"
                ,resBuffer);
        while(true){
            if(resBuffer.size()==0){
                Thread.sleep(100);
                continue;
            }
            String pop = resBuffer.pop();
            if(pop.equals("EOF")){
                break;
            }
            System.out.println(pop);
        }
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
