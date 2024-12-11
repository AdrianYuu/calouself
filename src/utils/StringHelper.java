package utils;

public class StringHelper {

    public static boolean hasSpecialCharacter(String s) {
        String specialCharacters = "!@#$%^&*";
        for (char ch : s.toCharArray()) if (specialCharacters.indexOf(ch) != -1) return true;
        return false;
    }

}
