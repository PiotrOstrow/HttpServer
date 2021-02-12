module org.example.db {
    requires java.sql;
    requires org.hibernate.orm.core;

    // need for hibernate
    requires java.persistence;
    requires net.bytebuddy;
    requires com.fasterxml.classmate;
    requires java.xml.bind;

    opens org.example.db;
    exports org.example.db;
}