package pcap;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONObject;
import com.tangzhangss.commonutils.utils.PcapUtil;

import java.io.File;
import java.io.IOException;

public class PcapUtilsTest {
    public static void main(String[] args) throws Exception {
        String filePath="C:\\Users\\it_ta\\Desktop\\test.pcap";
        File file = new File(filePath);

        int packetNum = PcapUtil.getPacketNum(file);

        for (int i = 5; i < 8; i++) {
            String hexS = PcapUtil.readToHex(file,i);
            byte[] bytes = PcapUtil.hexToPcap(hexS);
            String path = "C:\\Users\\it_ta\\Desktop\\test"+i+".pcap";
            FileUtil.writeBytes(bytes,path);
            String res = PcapUtil.pcapToJson("D:\\DEVEVN\\Wireshark\\", new File(path));
            JSONObject pcapLayersObj = PcapUtil.getPcapLayersJsonObj(res).getJSONObject(0);

            System.out.println(PcapUtil.parsePcapLayersData(pcapLayersObj));
        }
        /*

        String hexS2 = PcapUtil.readToHex(file,0,2);
        byte[] bytes2 = PcapUtil.hexToPcap(hexS2);
        FileUtil.writeBytes(bytes2,"C:\\Users\\it_ta\\Desktop\\test2.pcap");*/
    }
}
