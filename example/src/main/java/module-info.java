import org.example.ex.Example;
import org.example.spi.RequestHandler;

module example {
	requires org.example.http;
	requires org.example.spi;
	requires org.example.fileutil;
	requires org.example.db;
	provides RequestHandler with Example;
}