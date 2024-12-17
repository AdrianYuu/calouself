package controller;

import enums.UserRole;
import lib.response.Response;
import model.User;
import utils.StringHelper;

public final class UserController {

    /**
     * Registers a new user with the provided details.
     *
     * @param username    the username for the new user
     * @param password    the password for the new user
     * @param phoneNumber the phone number of the user (must start with "+62")
     * @param address     the address of the user
     * @param role        the role of the user (must be BUYER or SELLER)
     * @return a response indicating success or failure of the registration process
     */
    public Response<User> register(String username, String password, String phoneNumber, String address, UserRole role) {
        String message = checkAccountValidation(username, password, phoneNumber, address, role);

        if (!message.isBlank()) {
            return Response.Failed(message);
        }

        boolean isSuccess = User.create(username, password, phoneNumber, address, role);

        if (!isSuccess) {
            return Response.Failed("Failed to register user.");
        }

        return Response.Success("Successfully register user.");
    }

    /**
     * Authenticates a user using their username and password.
     * Supports an admin shortcut login with hardcoded credentials.
     *
     * @param username the username provided by the user
     * @param password the password provided by the user
     * @return a response containing the authenticated user or an error message
     */
    public Response<User> login(String username, String password) {
        if (username.isBlank()) {
            return Response.Failed("Username can't be empty.");
        }

        if (password.isBlank()) {
            return Response.Failed("Password can't be empty.");
        }

        if(username.equals("admin") && password.equals("admin")){
            return Response.Success(new User("0", "admin", "admin", "0", "-", UserRole.ADMIN));
        }

        User user = User.getByUsername(username);

        if (user == null) {
            return Response.Failed("User not found.");
        }

        if (!user.getPassword().equals(password)) {
            return Response.Failed("Wrong password.");
        }

        return Response.Success(user);
    }

    /**
     * Validates the inputs for account creation.
     *
     * @param username    the username to validate
     * @param password    the password to validate
     * @param phoneNumber the phone number to validate
     * @param address     the address to validate
     * @param role        the role to validate
     * @return an error message if validation fails; otherwise, an empty string
     */
    private String checkAccountValidation(String username, String password, String phoneNumber, String address, UserRole role) {
        if (username.isBlank()) {
            return "Username can't be empty.";
        }

        if (username.length() < 3) {
            return "Username must be at least 3 characters.";
        }

        User user = User.getByUsername(username);

        if (user != null) {
            return "Username already exists.";
        }

        if (password.isBlank()) {
            return "Password can't be empty.";
        }

        if (password.length() < 8) {
            return "Password must be at least 8 characters.";
        }

        if (!StringHelper.hasSpecialCharacter(password)) {
            return "Password must include at least one special character (!, @, #, $, %, ^, &, *).";
        }

        if (phoneNumber.isBlank()) {
            return "Phone number can't be empty.";
        }

        if (!phoneNumber.startsWith("+62")) {
            return "Phone number must start with '+62'.";
        }

        int secondPlus62Index = phoneNumber.indexOf("+62", phoneNumber.indexOf("+62") + 1);
        if (secondPlus62Index != -1) {
            return "Phone number must contain exactly one '+62'.";
        }

        String digitsOnly = phoneNumber.substring(3);
        if (digitsOnly.length() < 9 || !digitsOnly.chars().allMatch(Character::isDigit)) {
            return "Phone number must contain at least 9 digits after '+62'.";
        }

        if (address.isBlank()) {
            return "Address can't be empty.";
        }

        if (role == null) {
            return "Role can't empty.";
        }

        if (!role.equals(UserRole.BUYER) && !role.equals(UserRole.SELLER)) {
            return "Role must be pick between 'Buyer' or 'Seller'.";
        }

        return "";
    }

    private static UserController instance;

    /**
     * Provides a singleton instance of UserController.
     *
     * @return the singleton instance of UserController
     */
    public static UserController getInstance() {
        return instance = (instance == null) ? new UserController() : instance;
    }

    private UserController() {
    }

}
