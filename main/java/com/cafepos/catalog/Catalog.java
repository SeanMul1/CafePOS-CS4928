package com.cafepos.catalog;
import java.util.Optional;
public interface Catalog {
    void add(Product p);
    Optional<Product> findById(String id);
}

//seeans branch attempt 2