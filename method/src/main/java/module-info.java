import org.example.method.MethodHandler;
import org.example.spi.RequestHandler;

module method {
	requires org.example.http;
	requires org.example.spi;
	requires org.example.fileutil;
	provides RequestHandler with MethodHandler;
}