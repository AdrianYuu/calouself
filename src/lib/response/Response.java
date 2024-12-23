package lib.response;

public final class Response<T> {
    private final boolean success;
    private final String message;
    private final T data;

    public Response(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> Response<T> Success(String message) {
        return new Response<T>(true, message, null);
    }

    public static <T> Response<T> Success(T data) {
        return new Response<T>(true, null, data);
    }

    public static <T> Response<T> Success(String message, T data) {
        return new Response<T>(true, message, data);
    }

    public static <T> Response<T> Failed(String message) {
        return new Response<T>(false, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

}
