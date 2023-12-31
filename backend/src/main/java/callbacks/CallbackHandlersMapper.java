package callbacks;

import callbacks.handlers.BangCallbackHandler;
import callbacks.handlers.ICallbackHandler;

import java.util.HashMap;

public class CallbackHandlersMapper {
    private static final HashMap<CallbackType, ICallbackHandler> callbacks = new HashMap<>();

    static {
        callbacks.put(CallbackType.Bang, new BangCallbackHandler());
    }

    public static ICallbackHandler searchCallback(CallbackType callback){
        return callbacks.get(callback);
    }

}
