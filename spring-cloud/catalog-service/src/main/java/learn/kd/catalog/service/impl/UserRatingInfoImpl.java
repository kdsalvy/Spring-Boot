package learn.kd.catalog.service.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import learn.kd.catalog.service.RatingsInfoClient;
import learn.kd.catalog.service.UserRatingInfo;
import learn.kd.catalog.service.model.Rating;
import learn.kd.catalog.service.model.UserRating;

@Service
public class UserRatingInfoImpl implements UserRatingInfo {
    
    @Autowired
    private RatingsInfoClient ratingsInfoClient;
    
    @Retryable
    @HystrixCommand(fallbackMethod = "getFallbackUserRatings",
        threadPoolKey = "userRatingsInfoThreadPool",
        threadPoolProperties = {
                @HystrixProperty(name = "coreSize", value = "20"),
                @HystrixProperty(name = "maxQueueSize", value = "10")
        },
        commandProperties = {
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
        })
    @Override
    public UserRating getUserRatings(String userId) {
        return ratingsInfoClient.getUserRating(userId);
    }

    public UserRating getFallbackUserRatings(String userId) {
        UserRating userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setRatings(Arrays.asList(new Rating("0", 0)));
        return userRating;
    }

}
