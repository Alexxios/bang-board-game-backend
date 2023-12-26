package models;

public class User {

    public User(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return userId;
    }

    private String userId;
}
