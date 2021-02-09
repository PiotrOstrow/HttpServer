import org.example.get.MethodHandler;
import org.example.spi.RequestHandler;

module method {
	requires org.example.http;
	requires org.example.spi;
	requires org.example.fileutils;
	provides RequestHandler with MethodHandler;
}