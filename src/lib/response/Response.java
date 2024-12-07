package lib.response;

public final class Response<T> {
    public boolean success;
    public String message;
    public T data;

    public Response(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
