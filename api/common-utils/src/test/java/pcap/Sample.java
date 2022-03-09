package pcap;

import cn.hutool.log.StaticLog;
import com.tangzhangss.commonutils.utils.BaseUtil;
import io.pkts.Pcap;
import io.pkts.buffer.Buffer;
import io.pkts.buffer.ByteBuffer;
import io.pkts.packet.*;
import io.pkts.packet.impl.MACPacketImpl;
import io.pkts.packet.impl.TcpPacketImpl;
import io.pkts.protocol.Protocol;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;

public class Sample {

    public static void main(String[] args) throws IOException {
        String filePath="C:\\Users\\it_ta\\Desktop\\test.pcap";

        File file = new File(filePath);
        System.out.println("pcap文件大小:"+file.length());

        final Pcap pcap = Pcap.openStream(filePath);
        AtomicInteger packetNum = new AtomicInteger();//packet数量
        AtomicInteger packetDataSum = new AtomicInteger();//pcap包大小
        pcap.loop((packet)->{
            packetDataSum.getAndAdd(packet.getPayload().getReadableBytes());
            packetNum.getAndIncrement();

            if(packet.hasProtocol(Protocol.ARP)){
                System.out.println(".......ARP......");
            }
            if(packet.hasProtocol(Protocol.UDP)){
                System.out.println(".......UDP......");
            }
            //传输层TCP
            if (packet.hasProtocol(Protocol.TCP)) {
                TCPPacket tcpPacket = (TCPPacket) packet.getPacket(Protocol.TCP);
                System.out.println("--------------TCP["+packetNum+"]-----------------");
                printLog("32位确认号:",tcpPacket.getAcknowledgementNumber());
                printLog("16位校验和:",tcpPacket.getChecksum());
                printLog("16位紧急指针:",tcpPacket.getUrgentPointer());
                printLog("32位序号:",tcpPacket.getSequenceNumber());
                printLog("16位窗口大小:",tcpPacket.getWindowSize());
                printLog("6位保留:",tcpPacket.getReserved());
                printLog("16位目的端口号:",tcpPacket.getDestinationPort());
                printLog("16位源端口号:",tcpPacket.getSourcePort());
                printLog("4位头部长度:",tcpPacket.getHeaderLength());
                printLog("ArrivalTime:",tcpPacket.getArrivalTime());
            }
            //网络层
            if (packet.hasProtocol(Protocol.IPv4)) {
                IPv4Packet iPv4Packet = (IPv4Packet) packet.getPacket(Protocol.IPv4);
                System.out.println("--------------Ipv4["+packetNum+"]-----------------");
                Ipv4Info(iPv4Packet);
            }
            //数据链路层Ethernet 帧
            if (packet.hasProtocol(Protocol.ETHERNET_II)) {
                System.out.println("--------------Ethernet帧["+packetNum+"]-----------------");
                MACPacketImpl macPacket = (MACPacketImpl) packet.getPacket(Protocol.ETHERNET_II);
                System.out.println("     source:"+macPacket.getSourceMacAddress());
                System.out.println("destination:"+macPacket.getDestinationMacAddress());
            }
            return true;
        });
        System.out.println();
        System.out.println();

        System.out.println("packet数量:"+packetNum);
        System.out.println("pcap包大小（byte）:"+packetDataSum);
    }

    public static void Ipv4Info(IPv4Packet iPv4Packet){
        printLog("32位源IP:",iPv4Packet.getSourceIP());
        printLog("32位目IP:",iPv4Packet.getDestinationIP());
        printLog("13位片偏移:",iPv4Packet.getFragmentOffset());
        printLog("8位生存时间:",iPv4Packet.getTimeToLive());
        printLog("16位头部校验和:",iPv4Packet.getIpChecksum());
        printLog("4位头部长度(多少32bit字):",iPv4Packet.getHeaderLength());
        printLog("16位标识:",iPv4Packet.getIdentification());
        printLog("16位总长度:",iPv4Packet.getTotalIPLength());
        printLog("4位版本号:",iPv4Packet.getVersion());
        printLog("ArrivalTime:",iPv4Packet.getArrivalTime());
        try {
            Buffer headers = (Buffer) BaseUtil.readAttributeValue(iPv4Packet, "headers");
            /**
             * 8位协议位
             */
            short i = headers.getUnsignedByte(9);
            String protocol = null;
            if(i==1)protocol="ICMP";
            if(i==6)protocol="TCP";
            if(i==17)protocol="UDP";
            //这个字段定义了IP数据报的数据部分使用的协议类型。常用的协议及其十进制数值包括ICMP(1)、TCP(6)、UDP(17)。
            printLog("8位协议:",protocol);
            /**
             * 服务类型TOS
             * 包括一个 3 位的优先权字段（现在已经被忽略），
             * 4 位的 TOS 字段和 1 位保留字段（必须置 0）。
             * 4 位的 TOS 字段分别表示：最小延时，最大吞吐量，最高可靠性和最小费用。其中最多有一个能置为 1，
             * 应用程序应该根据实际需要来设置它。
             * 比如像 ssh 和 telnet 这样的登录程序需要的是最小延时的服务，而文件传输程序 ftp 则需要最大吞吐量的服务。
             */
            printLog("服务类型TOS:",headers.getUnsignedByte(1));
            /**
             * 3位标志位
             * 第一位保留。第二位（Don’t Fragment，DF）表示“禁止分片”。如果设置了这个位，IP 模块将不对数据报进行分片。
             * 在这种情况下，如果 IP 数据报长度超过 MTU 的话，IP 模块将丢弃该数据报并返回一个 ICMP 差错报文。
             * 第三位（More Fragment，MF）表示“更多分片”。除了数据报的最后一个分片外，其他分片都要把它置 1
             */
            final byte a = headers.getByte(6);
            short flag =  (short) (a>>5);
            printLog("3位标志位:",flag);
            if(flag!=0){
                System.out.println(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printLog(Object ...str){
        for (Object s : str) {
            System.out.print(s);
        }
        System.out.println();
    }
}
