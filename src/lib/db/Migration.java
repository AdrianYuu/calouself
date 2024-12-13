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

    private static void dropOffersTable() {
    }

    private static void dropTransactionsTable() {
        String query = "DROP TABLE IF EXISTS transactions";
        Connect.getConnection().executeUpdate(query);
    }

    private static void dropWishlistsTable() {
        String query = "DROP TABLE IF EXISTS wishlists";
        Connect.getConnection().executeUpdate(query);
    }

    private static void createUsersTable() {
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

    private static void createItemsTable() {
        String query = "CREATE TABLE items (" +
                "item_id INT PRIMARY KEY AUTO_INCREMENT," +
                "item_name VARCHAR(100)," +
                "item_size VARCHAR(100)," +
                "item_price VARCHAR(100)," +
                "item_category VARCHAR(100)," +
                "item_status VARCHAR(100)," +
                "decline_reason VARCHAR(100)," +
                "seller_id INT," +
                "FOREIGN KEY (seller_id) REFERENCES users(user_id)" +
                ")";
        Connect.getConnection().executeUpdate(query);
    }

    private static void createOffersTable() {
        String query = "CREATE TABLE offers (" +
                "offer_id INT PRIMARY KEY AUTO_INCREMENT," +
                "item_id INT," +
                "buyer_id INT," +
                "offer_price INT," +
                "offer_status VARCHAR(100)," +
                "FOREIGN KEY (item_id) REFERENCES items(item_id)," +
                "FOREIGN KEY (buyer_id) REFERENCES users(user_id)," +
                ")";
        Connect.getConnection().executeUpdate(query);

    }

    private static void createTransactionsTable() {
        String query = "CREATE TABLE transactions (" +
                "transaction_id INT PRIMARY KEY AUTO_INCREMENT," +
                "user_id INT," +
                "item_id INT," +
                "FOREIGN KEY (user_id) REFERENCES users(user_id)," +
                "FOREIGN KEY (item_id) REFERENCES items(item_id)" +
                ")";
        Connect.getConnection().executeUpdate(query);
    }

    private static void createWishlistsTable() {
        String query = "CREATE TABLE wishlists (" +
                    "wishlist_id INT PRIMARY KEY AUTO_INCREMENT," +
                    "user_id INT," +
                    "item_id INT," +
                    "FOREIGN KEY (user_id) REFERENCES users(user_id)," +
                    "FOREIGN KEY (item_id) REFERENCES items(item_id)" +
                    ")";
        Connect.getConnection().executeUpdate(query);
    }

    private static void dropTables() {
        dropWishlistsTable();
        dropTransactionsTable();
        dropOffersTable();
        dropItemsTable();
        dropUsersTable();
    }

    private static void createTables() {
        createUsersTable();
        createItemsTable();
        createOffersTable();
        createTransactionsTable();
        createWishlistsTable();
    }

    public static void run() {
        dropTables();
        createTables();
    }

}
