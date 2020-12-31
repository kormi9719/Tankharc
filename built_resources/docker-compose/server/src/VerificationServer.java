import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

public class VerificationServer {

    private final HttpServer server;

    public VerificationServer() throws Exception {

        this.server = HttpServer.create(new InetSocketAddress(22222), 0);
        this.server.setExecutor(null);
        this.server.start();

    }

    public void createVerificationEndpoint(String endpoint){
		
		System.out.println("New endpoint created");
        this.server.createContext(endpoint, new VerificationHandler(endpoint));

    }
}
