package lib.db;

public final class Migration {

    private static void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS users";
        Connect.getConnection().executeUpdate(query);
    }

    private static void dropItemsTable() {
        String query = "DROP TABLE IF EXISTS items";
        Connect.getConnection().executeUpdate(query);
    }

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

    private static void dropTables(){
        dropUsersTable();
        dropItemsTable();
    }

    private static void createTables(){
        createUsersTable();
        createItemsTable();
    }

    public static void run(){
        dropTables();
        createTables();
    }

}
