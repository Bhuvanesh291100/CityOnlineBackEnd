package com.city.online.api.repository;

import com.city.online.api.model.Cart;
import com.city.online.api.model.UserOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckoutRepository extends MongoRepository<UserOrder, String> {
    @Query("{username:?0}")
    List<UserOrder> findOrdersByUsername(String username);

}
