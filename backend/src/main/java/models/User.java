package models;

import lombok.Getter;

@Getter
public class User {

    public User(String userId){
        this.userId = userId;
    }

    private final String userId;
}
