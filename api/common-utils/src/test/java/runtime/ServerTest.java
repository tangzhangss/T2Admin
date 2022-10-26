package runtime;

import com.tangzhangss.commonutils.server.Server;

public class ServerTest {
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.copyTo();

        System.out.println(server.getCpu().getUsed());
        System.out.println(server.getCpu().getTotal());
    }
}
