import org.example.ex.Example;
import org.example.spi.RequestHandler;

module example {
	requires org.example.http;
	requires org.example.spi;
	provides RequestHandler with Example;
}