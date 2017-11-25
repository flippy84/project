package Arena.Server;

import java.io.InputStream;
import java.io.ObjectInputStream;

public class Handler<T> {
    private ResponseHandler<T> responseHandler;
    private Class<T> param;

    public Handler(ResponseHandler<T> responseHandler, Class<T> param) {
        this.responseHandler = responseHandler;
        this.param = param;
    }

    public Object handle(ObjectInputStream in) {
        try {
            Object object = in.readObject();
            return responseHandler.handle(param.cast(object));
        } catch (Exception exception) {
            return null;
        }
    }
}
