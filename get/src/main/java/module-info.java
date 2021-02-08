import org.example.get.GetHandler;
import org.example.spi.RequestHandler;

module get {
	requires org.example.http;
	requires org.example.spi;
	requires org.example.fileutils;
	provides RequestHandler with GetHandler;
}