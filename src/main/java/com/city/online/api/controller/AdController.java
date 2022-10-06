package com.city.online.api.controller;


import com.city.online.api.dto.request.AdCarouselRequestDto;
import com.city.online.api.model.AdvertisementCarousel;
import com.city.online.api.model.enums.Status;
import com.city.online.api.service.AdvertisementCarouselService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/advertisement")
public class AdController {
    @Autowired
    AdvertisementCarouselService advertisementCarouselService;

    AdvertisementCarousel advertisementCarousel;

    @GetMapping(value = "/getAdByStatus", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<AdvertisementCarousel>>  getAdByMobileNumber(@RequestParam String status, @RequestParam long mobileNumber, @RequestParam Integer page, @RequestParam Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AdvertisementCarousel> advertisementCarouselPage = advertisementCarouselService.getAdByMobileNumberAndStatus(status,mobileNumber,pageable);
        return new ResponseEntity<>(advertisementCarouselPage, HttpStatus.OK);
    }

    @PostMapping(value ="", consumes =  {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AdvertisementCarousel> createAdvertisement(@RequestBody AdCarouselRequestDto adCarouselRequestDto) {
        AdvertisementCarousel advertisementCarousel1 = advertisementCarouselService.addAdvertisement(adCarouselRequestDto);
        return new ResponseEntity<>(advertisementCarousel1, HttpStatus.OK);
    }

    @GetMapping(value = "/status",  produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<AdvertisementCarousel>> getAdvertisementsByStatus(@RequestParam Status status) {
        List<AdvertisementCarousel> advertisementCarousels = advertisementCarouselService.getAllAdvertisementsByStatus(status);
        return new ResponseEntity<>(advertisementCarousels, HttpStatus.OK);
    }

}
