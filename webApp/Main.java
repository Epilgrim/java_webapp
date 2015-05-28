
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

import com.epilgrim.app.AppKernel;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        AppKernel appKernel = new AppKernel();
        appKernel.init();
        server.createContext("/", appKernel);
        server.setExecutor(null);
        server.start();
    }
}
