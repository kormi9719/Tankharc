package hu.elte.kormi.database;

public class User {

    private final String email;

    public User(String email){
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }
}

