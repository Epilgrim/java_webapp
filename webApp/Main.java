
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

import com.epilgrim.app.AppKernel;

public class Main
{
    public static void main(String[] args) throws Exception
    {
    	int port = 8000;
    	if (args.length > 0) {
    	    try {
    	        port = Integer.parseInt(args[0]);
    	    } catch (NumberFormatException e) {
    	        System.err.println("Port number must be an integer");
    	        System.exit(1);
    	    }
    	}
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        AppKernel appKernel = new AppKernel();
        appKernel.init();
        server.createContext("/", appKernel);
        server.setExecutor(null);
        server.start();
    }
}
