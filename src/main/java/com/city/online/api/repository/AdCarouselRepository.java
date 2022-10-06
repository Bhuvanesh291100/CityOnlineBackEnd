package com.city.online.api.repository;

import com.city.online.api.model.AdvertisementCarousel;
import com.city.online.api.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdCarouselRepository extends MongoRepository<AdvertisementCarousel, String> {

    Optional<AdvertisementCarousel> findAdByStatus(Status status);

    @Query("{'mobileNumber': ?0}")
    Page<AdvertisementCarousel> findAdvertisementCarouselBy(String status, long mobileNumber, Pageable pageable);

    @Query("{'status': ?0}")
    List<AdvertisementCarousel> findAdvertisementCarouselByStatus(Status status);

}
