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

    private static void createItemsTable(){
        String query = "CREATE TABLE items (" +
                "item_id INT PRIMARY KEY AUTO_INCREMENT," +
                "item_name VARCHAR(100)," +
                "item_size VARCHAR(100)," +
                "item_price INT," +
                "item_category VARCHAR(100)," +
                "item_status VARCHAR(100)" +
                ")";
        Connect.getConnection().executeUpdate(query);
    }
    public static void run(){
        createUsersTable();
        createItemsTable();
    }

}
