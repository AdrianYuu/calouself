package controller;

import lib.response.Response;
import model.User;

public final class UserController {

    private static UserController instance;

    public static UserController getInstance() {
        return instance = (instance == null) ? new UserController() : instance;
    }

    private UserController() {
    }

    public Response<User> register(String username, String password, String phoneNumber, String address, String role) {
        String validationResult = checkAccountValidation(username, password, phoneNumber, address, role);

        if (!validationResult.isBlank()) {
            return new Response<>(false, validationResult, null);
        }

        return new Response<>(true, "Successfully register user.", null);
    }

    public String checkAccountValidation(String username, String password, String phoneNumber, String address, String role) {
        if (username.isBlank()) {
            return "Name can't be empty.";
        }

        return "";
    }

}
