package org.example.plugin;

import org.example.db.Country;
import org.example.db.CountryDAO;
import org.example.db.CountryDaoJPAImpl;
import org.example.http.HttpRequest;
import org.example.http.HttpResponse;
import org.example.json.JsonConverter;
import org.example.spi.Address;
import org.example.spi.RequestHandler;

import java.util.List;

@Address("/countries")
public class CountryHandler implements RequestHandler {

    @Override
    public HttpResponse handleRequest(HttpRequest httpRequest) {
        CountryDAO countryDAO = new CountryDaoJPAImpl();
        List<Country> list = countryDAO.getAll();

        JsonConverter jsonConverter = new JsonConverter();
        String json = jsonConverter.convertToJson(list);

        HttpResponse response = new HttpResponse();
        response.setHeader("Content-Type", "application/json");
        response.setBody(json);

        return response;
    }
}

