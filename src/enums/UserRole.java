package enums;

public enum UserRole {
    BUYER {
        @Override
        public String toString() {
            return "Buyer";
        }
    },
    SELLER {
        @Override
        public String toString() {
            return "Seller";
        }
    },
    ADMIN {
        @Override
        public String toString() {
            return "Admin";
        }
    }
}
