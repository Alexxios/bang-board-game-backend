package models;

import callbacks.CallbackType;
import lombok.Getter;
import server.services.GameService;

import java.util.function.Function;

@Getter
public class Callback {

    public Callback(){
        this.isActive = false;
        callbackType = CallbackType.None;
    }

    public Callback(int currentPlayerId, int callbackPlayerId, CallbackType callbackType){
        this.isActive = true;
        this.currentPlayerId = currentPlayerId;
        this.callbackPlayerId = callbackPlayerId;
        this.callbackType = callbackType;
    }

    public void reset(){
        isActive = false;
    }

    private boolean isActive;
    private int currentPlayerId;
    private int callbackPlayerId;
    private CallbackType callbackType;
}
