import java.util.*;

public class User {

    private String username;
    private String password;
    private String email;
    private String endpoint;
    private boolean verified;

    public User(String username, String password, String email, String endpoint, boolean verified){

        this.username = username;
        this.password = password;
        this.email = email;
        this.endpoint = endpoint;
        this.verified = verified;
    }
    
    public String getUsername(){
        return this.username;
    }
	
	public String getEmail(){
		return this.email;
	}
	
	public String getEndpoint(){
		return this.endpoint;
	}
}
