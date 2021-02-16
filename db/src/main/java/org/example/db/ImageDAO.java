package org.example.db;

import java.util.List;

public interface ImageDAO {
    void create(Image image);
    List<Image> getAll();
}
