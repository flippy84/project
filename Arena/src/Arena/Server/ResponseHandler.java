package Arena.Server;

public interface ResponseHandler<T> {
    Object handle(T object);
}
