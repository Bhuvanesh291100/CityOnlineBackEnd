package com.city.online.api.service;

import com.city.online.api.constant.AppConstants;
import com.city.online.api.dto.request.AdCarouselRequestDto;
import com.city.online.api.exception.BusinessException;
import com.city.online.api.model.AdvertisementCarousel;
import com.city.online.api.model.enums.Status;
import com.city.online.api.repository.AdCarouselRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Builder
public class AdvertisementCarouselService {


    @Autowired
    AdCarouselRepository adCarouselRepository;





    public AdvertisementCarousel addAdvertisement(AdCarouselRequestDto adCarouselRequestDto) {
        try {
            log.info("Creating an Advertisement");
            AdvertisementCarousel advertisementCarousel = AdvertisementCarousel.builder()
                    .firstName(adCarouselRequestDto.getFirstName())
                    .lastName(adCarouselRequestDto.getLastName())
                    .url(adCarouselRequestDto.getUrl())
                    .description(adCarouselRequestDto.getDescription())
                    .mobileNumber(adCarouselRequestDto.getMobileNumber())
                    .emailId(adCarouselRequestDto.getEmailId())
                    .advertisementType(adCarouselRequestDto.getAdvertisementType())
                    .status(adCarouselRequestDto.getStatus())
                    .build();
            return adCarouselRepository.save(advertisementCarousel);
        } catch (Exception e) {
            throwException(AppConstants.ADVERTISEMENT_VALIDATOR, "UNABLE TO POST ADVERTISEMENT", HttpStatus.BAD_REQUEST);
            return null;
        }
    }
    private void throwException(String code, String errorMessage, HttpStatus httpStatus) {
        throw new BusinessException(code, errorMessage ,httpStatus , null);
    }

    public Page<AdvertisementCarousel> getAdByMobileNumberAndStatus (String status, long mobileNumber, Pageable pageable) {
        try {
            return adCarouselRepository.findAdvertisementCarouselBy(status,mobileNumber,pageable);
        }
        catch (Exception e) {
            throwException(AppConstants.ADVERTISEMENT_GET_ERROR_CODE, AppConstants.ADVERTISEMENT_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
            return null;
        }
    }

    public List<AdvertisementCarousel> getAllAdvertisementsByStatus(Status status) {
        return adCarouselRepository.findAdvertisementCarouselByStatus(status);

    }




}
