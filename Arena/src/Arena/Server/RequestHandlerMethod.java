package Arena.Server;

import java.io.ObjectInputStream;

public interface RequestHandlerMethod {
    Object handle(ObjectInputStream in);
}
