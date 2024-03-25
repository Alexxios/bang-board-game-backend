package models;

import lombok.Getter;

public class User {
    public User(){}

    public User(String nickname){
        this.nickname = nickname;
    }

    @Getter
    private String nickname;
}
