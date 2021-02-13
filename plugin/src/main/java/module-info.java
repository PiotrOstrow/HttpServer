import org.example.plugin.CommentHandler;
import org.example.plugin.CountryHandler;
import org.example.plugin.UserHandler;
import org.example.spi.RequestHandler;

module org.example.plugin {
	requires org.example.http;
	requires org.example.spi;
	requires org.example.fileutil;
	requires org.example.db;
	requires com.google.gson;

	provides RequestHandler with UserHandler, CountryHandler, CommentHandler;
}