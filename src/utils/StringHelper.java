package utils;

public class StringHelper {

    public static boolean hasSpecialCharacter(String s) {
        String specialCharacters = "!@#$%^&*";
        for (char c : s.toCharArray()) if (specialCharacters.indexOf(c) != -1) return true;
        return false;
    }

}
