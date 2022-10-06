package com.city.online.api.repository;

import com.city.online.api.model.BusinessContactUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessContactUserRepository extends MongoRepository<BusinessContactUser, String> {

    @Query("{'$and':[{ 'mainBusinessCategory':?0}, {'status':?1}]}")
    List<BusinessContactUser> findBusinessUserDetailsByBusinessMainCategory(String mainBusinessCategory, String status);

    @Query("{'$and':[{ 'mainBusinessCategory':?0}, {'subBusinessCategoryList':?1}]}")
    List<BusinessContactUser> findBusinessUserDetailsByBusinessMainAndSubCategory(String mainBusinessCategory, String subBusinessCategory);

    boolean existsByEmailId(String email);

    @Query("{status:?0}")
    List<BusinessContactUser> findBusinessUserDetailsByStatus(String status);

    @Query("{username:?0}")
    BusinessContactUser findBusinessUserDetailsByUsername(String username);
}
