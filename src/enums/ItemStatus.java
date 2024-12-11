package enums;

public enum ItemStatus {
    PENDING {
        @Override
        public String toString() {
            return "Pending";
        }
    },
    APPROVED {
        @Override
        public String toString() {
            return "Approved";
        }
    },
    DECLINED {
        @Override
        public String toString() {
            return "Declined";
        }
    }
}
