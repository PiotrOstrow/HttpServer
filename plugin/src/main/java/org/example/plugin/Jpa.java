package org.example.plugin;

import com.google.gson.Gson;
import org.example.db.Country;
import org.example.db.CountryDAO;
import org.example.db.CountryDAOWithJPAImpl;
import org.example.http.HttpRequest;
import org.example.http.HttpResponse;
import org.example.spi.Address;
import org.example.spi.RequestHandler;

import java.util.List;

@Address("/countries")
public class Jpa implements RequestHandler {

    @Override
    public HttpResponse handleRequest(HttpRequest httpRequest) {
        CountryDAO countryDAO = new CountryDAOWithJPAImpl();
        List<Country> list = countryDAO.getAll();

        Gson gson = new Gson();
        String json = gson.toJson(list);

        HttpResponse response = new HttpResponse();
        response.setHeader("Content-Type", "application/json");
        response.setBody(json);

        return response;
    }
}

