package org.example.db;

import java.util.List;

    public interface CountryDAO {
        void create (Country c);
        // Inserts country C to the Countries table

        List<Country> getAll();
        // Returns a list of all Countries in table

    }