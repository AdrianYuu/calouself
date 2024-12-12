module calouself {
    requires transitive javafx.controls;
    requires java.sql;
	requires javafx.graphics;
    requires transitive mysql.connector.java;
    opens main;
    opens model;
}