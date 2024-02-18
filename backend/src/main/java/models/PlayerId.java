package models;

import lombok.Getter;
import models.enums.Status;

@Getter
public class PlayerId {

    public PlayerId(){
        this.nickname = "";
        this.status = Status.NotReady;
    }

    public PlayerId(String nickname){
        this.nickname = nickname;
        this.status = Status.NotReady;
    }

    public PlayerId(String nickname, Status status){
        this.nickname = nickname;
        this.status = status;
    }

    public void changeStatus(){
        if (status == Status.Ready){
            status = Status.NotReady;
        }else{
            status = Status.Ready;
        }
    }

    private String nickname;
    private Status status;
}
