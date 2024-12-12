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

    private static void dropOffersTable(){
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
                            "item_price VARCHAR(100)," +
                            "item_category VARCHAR(100)," +
                            "item_status VARCHAR(100)," +
                            "seller_id INT," +
                            "FOREIGN KEY (seller_id) REFERENCES users(user_id)" +
                        ")";
        Connect.getConnection().executeUpdate(query);
    }

    private static void createOffersTable(){
        String query = "";
    }

    private static void dropTables(){
        dropOffersTable();
        dropItemsTable();
        dropUsersTable();
    }

    private static void createTables(){
        createUsersTable();
        createItemsTable();
        createOffersTable();
    }

    public static void run(){
        dropTables();
        createTables();
    }

}
