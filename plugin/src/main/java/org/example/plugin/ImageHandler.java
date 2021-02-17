package org.example.plugin;

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
import java.io.FileOutputStream;
import java.io.IOException;
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
        Parameter parameter = httpRequest.getParameter("file");

        if(parameter != null) {
            Image image = new Image(parameter.getFileName());
            imageDao.create(image);

            File directory = new File("htdocs/upload/");
            if(directory.exists() || directory.mkdirs()){
                File imageFile = new File("htdocs/upload/" + image.getId() + ".png");
                try {
                    imageFile.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
                    fileOutputStream.write(parameter.getData());
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getImages() {
        List<Image> imageList = imageDao.getAll();
        StringBuilder stringBuilder = new StringBuilder();
        for(Image image : imageList) {
            stringBuilder.append("<a href=\"");
            stringBuilder.append("upload/");
            stringBuilder.append(image.getId());
            stringBuilder.append(".png\">");
            stringBuilder.append("<img src=\"upload/");
            stringBuilder.append(image.getId());
            stringBuilder.append(".png\">");
            stringBuilder.append("</a>");
        }
        return stringBuilder.toString();
    }
}
