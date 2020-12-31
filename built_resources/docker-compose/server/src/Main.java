import java.util.*;

public class Main {

    public static void main(String[] args) {
		
		try {
            VerificationServer verificationServer = new VerificationServer();
			Timer t = new Timer();
			t.schedule(new TimerTask() {
				@Override
				public void run() {
				   try {
	   
					System.out.println("Starting");
					ArrayList<User> users = DatabaseHandler.getUsers();
					for(int i = 0; i < users.size(); i++){
						System.out.println(users.get(i).getUsername());
						if(users.get(i).getEndpoint().equals("")){
							String endpoint = "/" + VerificationEndpoint.getVerificationEndpoint();
							DatabaseHandler.updateEndpoint(users.get(i).getEmail(), endpoint);
							MailSender.sendMail(users.get(i).getEmail(), endpoint);
							verificationServer.createVerificationEndpoint(endpoint);
							
						}
					}

					} catch (Exception e){
						e.printStackTrace();
					}
				}
			}, 0, 60000);
		} catch (Exception e){
			e.printStackTrace();
		}
    }
}
