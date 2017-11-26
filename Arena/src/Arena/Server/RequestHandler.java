package Arena.Server;

import java.io.ObjectInputStream;

public class RequestHandler<T> {
    private RequestHandlerMethod<T> requestHandlerMethod;
    private Class<T> param;

    public RequestHandler(RequestHandlerMethod<T> requestHandlerMethod, Class<T> param) {
        this.requestHandlerMethod = requestHandlerMethod;
        this.param = param;
    }

    public Object handle(ObjectInputStream in) {
        try {
            Object object = in.readObject();
            return requestHandlerMethod.handle(param.cast(object));
        } catch (Exception exception) {
            return null;
        }
    }
}
