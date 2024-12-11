package enums;

public enum AlertType {
    INFO {
        @Override
        public String toString() {
            return "Info";
        }
    },
    WARNING {
        @Override
        public String toString() {
            return "Warning";
        }
    },
    ERROR {
        @Override
        public String toString() {
            return "Error";
        }
    },
    CONFIRMATION {
        @Override
        public String toString() {
            return "Confirmation";
        }
    }
}
