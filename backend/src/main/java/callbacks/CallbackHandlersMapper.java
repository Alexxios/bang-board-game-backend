package callbacks;

import configurators.ModelsConfiguration;
import models.callbacks.handlers.BangCallbackHandler;
import models.callbacks.handlers.ICallbackHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("callbackHandlersMapperBean")
public class CallbackHandlersMapper {
    private static final HashMap<CallbackType, ICallbackHandler> callbacks = new HashMap<>();

    public CallbackHandlersMapper(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ModelsConfiguration.class);

        callbacks.put(CallbackType.Bang, context.getBean("bangCallbackHandlerBean", BangCallbackHandler.class));
    }

    public ICallbackHandler searchCallback(CallbackType callback){
        return callbacks.get(callback);
    }

}
