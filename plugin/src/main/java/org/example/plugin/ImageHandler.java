package org.example.plugin;

import org.example.db.Comment;
import org.example.db.Image;
import org.example.db.ImageDAO;
import org.example.db.ImageDaoJPAImpl;
import org.example.fileutil.FileReader;
import org.example.http.HttpRequest;
import org.example.http.HttpResponse;
import org.example.http.Parameter;
import org.example.spi.Address;
import org.example.spi.RequestHandler;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Address("/images.html")
public class ImageHandler implements RequestHandler {

    private ImageDAO imageDao;

    @Override
    public HttpResponse handleRequest(HttpRequest httpRequest) {
        imageDao = new ImageDaoJPAImpl();
        saveImage(httpRequest);

        HttpResponse response = new HttpResponse();
        response.setHeader("Content-Type", "text/html");

        byte[] data = FileReader.readFromFile(new File("htdocs/images.html"));
        String body = new String(data);
        body = body.replace("_?", getImages());
        response.setBody(body);

        return response;
    }

    private void saveImage(HttpRequest httpRequest) {
        String name = httpRequest.getParameterString("name");

        if(name != null)
            imageDao.create(new Image(name));
    }

    private String getImages() {
        List<Image> imageList = imageDao.getAll();

    }

}
