package lib.response;

public final class Response<T> {
    private boolean success;
    private String message;
    private T data;

    public Response(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> Response<T> Success(T data) {
        return new Response<T>(true, null, data);
    }

    public static <T> Response<T> Failed(String message, T data) {
        return new Response<T>(false, message, data);
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
