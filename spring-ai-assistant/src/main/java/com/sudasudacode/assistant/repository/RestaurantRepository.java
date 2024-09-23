package com.sudasudacode.assistant.repository;

import com.sudasudacode.assistant.models.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {



    @Query(value = "{ _id : ?0 }")
    Restaurant findByRestaurantId(String restaurantId);

    @Query(value = "{ _id : { $in :?0 } }")
    List<Restaurant> findByRestaurantId(List<String> restaurantId);
}
