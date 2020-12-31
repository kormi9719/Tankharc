import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class VerificationHandler implements HttpHandler {

    private String endpoint;

    public VerificationHandler(String endpoint){
        this.endpoint = endpoint;
		System.out.println("################################Passed endpoint: " + endpoint);
    }

    public void handle(HttpExchange t) throws IOException {

        DatabaseHandler.updateVerification(endpoint);

        String response = "It works";
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
