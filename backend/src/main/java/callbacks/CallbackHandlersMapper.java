package callbacks;

import models.callbacks.handlers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CallbackHandlersMapper {
    private final HashMap<CallbackType, ICallbackHandler> callbacks = new HashMap<>();

    @Autowired
    public CallbackHandlersMapper(ApplicationContext context){

        callbacks.put(CallbackType.Bang, context.getBean("bangCallbackHandlerBean", BangCallbackHandler.class));
        callbacks.put(CallbackType.Indians, context.getBean("indiansCallbackHandlerBean", IndiansCallbackHandler.class));
        callbacks.put(CallbackType.Duel, context.getBean("duelCallbackHandlerBean", DuelCallbackHandler.class));
        callbacks.put(CallbackType.Shop, context.getBean("shopCallbackHandlerBean", ShopCallbackHandler.class));
        callbacks.put(CallbackType.Panic, context.getBean("panicCallbackHandlerBean", PanicCallbackHandler.class));
        callbacks.put(CallbackType.Lovely, context.getBean("lovelyCallbackHandlerBean", LovelyCallbackHandler.class));
    }

    public ICallbackHandler searchCallback(CallbackType callback){
        return callbacks.get(callback);
    }

}
