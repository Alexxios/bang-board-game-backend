package callbacks;

import models.cards.Bang;

import java.util.HashMap;

public class CallbackMapper {
    private static final HashMap<CallbackType, ICallback> callbacks = new HashMap<>();

    static {
        callbacks.put(CallbackType.Bang, new BangCallback());
    }

    public static ICallback searchCallback(CallbackType callback){
        return callbacks.get(callback);
    }

}
