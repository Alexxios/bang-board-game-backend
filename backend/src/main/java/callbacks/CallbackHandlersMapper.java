package callbacks;

import configurators.ModelsConfiguration;
import models.callbacks.handlers.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("callbackHandlersMapperBean")
public class CallbackHandlersMapper {
    private static final HashMap<CallbackType, ICallbackHandler> callbacks = new HashMap<>();

    public CallbackHandlersMapper(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ModelsConfiguration.class);

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
