package learn.kd.catalog.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import learn.kd.catalog.service.model.Rating;
import learn.kd.catalog.service.model.UserRating;

@FeignClient("ratings-data-service")
public interface RatingsInfoClient {

    @RequestMapping("/ratings/{movieId}")
    public Rating getRatingData(@PathVariable("movieId") String movieId);

    @RequestMapping(path = "/ratings/users/{userId}")
    public UserRating getUserRating(@PathVariable("userId") String userId);
}
