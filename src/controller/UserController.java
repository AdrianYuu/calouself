package controller;

import enums.UserRole;
import lib.response.Response;
import model.User;

public final class UserController {

    private static UserController instance;

    public static UserController getInstance() {
        return instance = (instance == null) ? new UserController() : instance;
    }

    private UserController() {
    }

    public Response<User> register(String username, String password, String phoneNumber, String address, UserRole role) {
        String validationResult = checkAccountValidation(username, password, phoneNumber, address, role);

        if (!validationResult.isBlank()) {
            return Response.Failed(validationResult);
        }

        boolean isSuccess = User.create(username, password, phoneNumber, address, role);

        if (!isSuccess) {
            return Response.Failed("Failed to register user.");
        }

        return Response.Success(null);
    }

    public String checkAccountValidation(String username, String password, String phoneNumber, String address, UserRole role) {
        if (username.isBlank()) {
            return "Name can't be empty.";
        }

        return "";
    }

    public Response<User> login(String username, String password) {
        if (username.isEmpty()) {
            return Response.Failed("Username can't be empty.");
        }

        if (password.isEmpty()) {
            return Response.Failed("Password can't be empty.");
        }

        User user = User.get(username);

        if (user == null) {
            return Response.Failed("User not found.");
        }

        if (!user.getPassword().equals(password)) {
            return Response.Failed("Wrong password.");
        }

        return Response.Success(user);
    }

}
