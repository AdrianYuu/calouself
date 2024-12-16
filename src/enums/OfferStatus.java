package enums;

public enum OfferStatus {
    PENDING {
        @Override
        public String toString() {
            return "Pending";
        }
    },
    ACCEPTED {
        @Override
        public String toString() {
            return "Accepted";
        }
    },
    DECLINED {
        @Override
        public String toString() {
            return "Declined";
        }
    }
}
