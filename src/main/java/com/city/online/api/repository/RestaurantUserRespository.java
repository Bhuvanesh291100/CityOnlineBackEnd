package com.city.online.api.repository;

import com.city.online.api.model.RestaurantUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantUserRespository extends MongoRepository<RestaurantUser, String> {
    @Query("{username:?0}")
    RestaurantUser findRestaurantUserByUsername(String username);
}
