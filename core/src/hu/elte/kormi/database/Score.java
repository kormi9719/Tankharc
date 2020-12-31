package hu.elte.kormi.database;

public class Score {

    private final String rank;
    private final String username;
    private final int score;

    public Score(int rank, String email, int score){
        this.rank = (rank < 10 ? "0" : "") + rank;
        this.username = DatabaseHandler.getUsernameByEmail(email);
        this.score = score;
    }

    public String getRank(){
        return this.rank;
    }

    public String getUsername(){
        return this.username;
    }

    public String getScore(){
        return Integer.toString(this.score);
    }
}
