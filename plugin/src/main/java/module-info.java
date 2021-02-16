import org.example.plugin.*;
import org.example.spi.RequestHandler;

module org.example.plugin {
	requires org.example.http;
	requires org.example.spi;
	requires org.example.fileutil;
	requires org.example.db;
	requires org.example.json;

	provides RequestHandler with UserHandler, CountryHandler, CommentHandler, ImageHandler, UploadTestHandler;
}