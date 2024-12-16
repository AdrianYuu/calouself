package lib.db;

public final class Seeder {

    private static void seedUsersTable() {
        String query = "INSERT INTO users (username, password, phone_number, address, role) VALUES " +
                "('buyer1', 'password1', '1234567890', 'Buyer Address 1', 'BUYER'), " +
                "('buyer2', 'password2', '0987654321', 'Buyer Address 2', 'BUYER'), " +
                "('seller1', 'password3', '1231231230', 'Seller Address 1', 'SELLER'), " +
                "('seller2', 'password4', '3213213210', 'Seller Address 2', 'SELLER');";
        Connect.getConnection().executeUpdate(query);
    }

    private static void seedItemsTable() {
        String query = "INSERT INTO items (item_name, item_size, item_price, item_category, item_status, decline_reason, seller_id) VALUES " +
                "('Item A', 'M', '100', 'Category 1', 'APPROVED', NULL, 3), " +
                "('Item B', 'L', '200', 'Category 2', 'APPROVED', NULL, 3), " +
                "('Item E', 'M', '300', 'Category 5', 'APPROVED', NULL, 3), " +
                "('Item G', 'S', '120', 'Category 2', 'APPROVED', NULL, 3), " +
                "('Item I', 'M', '600', 'Category 4', 'APPROVED', NULL, 3), " +
                "('Item J', 'L', '700', 'Category 5', 'APPROVED', NULL, 4), " +
                "('Item D', 'XL', '250', 'Category 4', 'PENDING', NULL, 4), " +
                "('Item H', 'XL', '500', 'Category 3', 'PENDING', NULL, 4), " +
                "('Item C', 'S', '150', 'Category 3', 'DECLINED', 'Out of stock', 4), " +
                "('Item F', 'L', '400', 'Category 1', 'DECLINED', 'Quality issues', 4);";
        Connect.getConnection().executeUpdate(query);
    }

    private static void seedOffersTable() {
        String query = "INSERT INTO offers (item_id, buyer_id, offer_price, offer_status, decline_reason) VALUES " +
                "(1, 1, 90, 'PENDING', NULL), " +
                "(2, 1, 180, 'ACCEPTED', NULL), " +
                "(3, 2, 140, 'DECLINED', 'Price too low');";
        Connect.getConnection().executeUpdate(query);
    }

    private static void seedTransactionsTable() {
        String query = "INSERT INTO transactions (user_id, item_id) VALUES " +
                "(1, 2), " +
                "(2, 2);";
        Connect.getConnection().executeUpdate(query);
    }

    private static void seedWishlistsTable() {
        String query = "INSERT INTO wishlists (user_id, item_id) VALUES " +
                "(1, 1), " +
                "(2, 3);";
        Connect.getConnection().executeUpdate(query);
    }

    public static void run() {
        seedUsersTable();
        seedItemsTable();
        seedOffersTable();
        seedTransactionsTable();
        seedWishlistsTable();
    }
}
