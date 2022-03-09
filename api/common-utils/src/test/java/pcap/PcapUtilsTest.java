package pcap;

import com.tangzhangss.commonutils.utils.BaseUtil;
import com.tangzhangss.commonutils.utils.FileUtil;
import com.tangzhangss.commonutils.utils.PcapUtil;
import compare.Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

public class PcapUtilsTest {
    public static void main(String[] args) throws IOException {
        String filePath="C:\\Users\\it_ta\\Desktop\\test.pcap";
        File file = new File(filePath);

        System.out.println(PcapUtil.pcapToJson(file));


        /*String hexS = PcapUtil.readToHex(file);
        byte[] bytes = PcapUtil.hexToPcap(hexS);
        FileUtil.writeBytes(bytes,"C:\\Users\\it_ta\\Desktop\\test1.pcap");

        String hexS2 = PcapUtil.readToHex(file,0,2);
        byte[] bytes2 = PcapUtil.hexToPcap(hexS2);
        FileUtil.writeBytes(bytes2,"C:\\Users\\it_ta\\Desktop\\test2.pcap");*/
    }
}
