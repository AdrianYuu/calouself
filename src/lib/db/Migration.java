package lib.db;

public final class Migration {

    private static void createUsersTable(){
        String query = "CREATE TABLE users (" +
                            "user_id INT PRIMARY KEY AUTO_INCREMENT," +
                            "username VARCHAR(100)," +
                            "password VARCHAR(100)," +
                            "phone_number VARCHAR(100)," +
                            "address VARCHAR(100)," +
                            "role VARCHAR(100)" +
                        ")";
        Connect.getConnection().executeUpdate(query);
    }

    public static void run(){
        createUsersTable();
    }

}
