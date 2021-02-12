import org.example.plugin.Jpa;
import org.example.plugin.UserHandler;
import org.example.spi.RequestHandler;

module org.example.plugin {
	requires org.example.http;
	requires org.example.spi;
	requires org.example.fileutil;
	requires org.example.db;
	requires com.google.gson;
    requires javax.persistence;
	provides RequestHandler with UserHandler, Jpa;
}