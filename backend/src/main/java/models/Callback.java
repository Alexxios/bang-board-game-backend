package models;

import callbacks.CallbackType;
import lombok.Getter;
import lombok.Setter;

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

    @Getter
    @Setter
    private Event event;
    private CallbackType callbackType;
}
