package models;

import callbacks.CallbackType;
import lombok.Getter;
import lombok.Setter;
import server.services.GameService;

import java.util.function.Function;

@Getter
public class Callback {

    public Callback(){
        this.event = new Event();
        this.callbackType = CallbackType.None;
    }

    public Callback(Event event, CallbackType callbackType){
        this.event = event;
        this.callbackType = callbackType;
    }

    private Event event;
    private CallbackType callbackType;
}
