package Arena.Server;

import java.io.ObjectInputStream;

public class RequestHandler {
    private RequestHandlerMethod requestHandlerMethod;

    public RequestHandler(RequestHandlerMethod requestHandlerMethod) {
        this.requestHandlerMethod = requestHandlerMethod;
    }

    public Object handle(ObjectInputStream in) {
        try {
            return requestHandlerMethod.handle(in);
        } catch (Exception exception) {
            return null;
        }
    }
}
