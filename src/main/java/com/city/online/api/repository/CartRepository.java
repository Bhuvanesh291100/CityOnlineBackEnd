package com.city.online.api.repository;

import com.city.online.api.model.Cart;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {

    @Query("{username:?0}")
    Cart findByUsername(String username);

    @Query("{username:?0}")
    Cart findCartItemsByUsername(String username);

    @DeleteQuery
    Long deleteByUsername(String username);
}
