package Arena.Server;

public interface RequestHandlerMethod<T> {
    Object handle(T object);
}
