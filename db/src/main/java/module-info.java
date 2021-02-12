module org.example.db {
    requires java.sql;
    requires javax.persistence;
    opens org.example.db;
    exports org.example.db;
}