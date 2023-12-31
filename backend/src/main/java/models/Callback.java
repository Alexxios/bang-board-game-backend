package models;

import callbacks.CallbackType;
import lombok.Getter;
import lombok.Setter;
import server.services.GameService;

import java.util.function.Function;

@Getter
public class Callback {

    public Callback(){
        this.isActive = false;
        callbackType = CallbackType.None;
    }

    public Callback(Event event, CallbackType callbackType){
        this.isActive = true;
        this.event = event;
        this.callbackType = callbackType;
    }

    public void reset(){
        isActive = false;
    }

    @Setter
    private boolean isActive;
    private Event event;
    private CallbackType callbackType;
}
