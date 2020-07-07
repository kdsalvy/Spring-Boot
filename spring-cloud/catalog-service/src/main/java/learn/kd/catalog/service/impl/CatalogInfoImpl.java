package learn.kd.catalog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import learn.kd.catalog.service.CatalogInfo;
import learn.kd.catalog.service.MovieInfoClient;
import learn.kd.catalog.service.model.CatalogItem;
import learn.kd.catalog.service.model.Movie;
import learn.kd.catalog.service.model.Rating;

@Service
public class CatalogInfoImpl implements CatalogInfo{

    @Autowired
    private MovieInfoClient movieInfoClient;

    @Retryable
    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem",
        threadPoolKey = "moviesInfoThreadPool",
        threadPoolProperties = {
                @HystrixProperty(name = "coreSize", value = "20"),
                @HystrixProperty(name = "maxQueueSize", value = "10")
        },
        commandProperties = {
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000"),
                @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "1000")
        })
    @Override
    public CatalogItem getCatalogItem(Rating rating) {
        System.out.println("Getting Movie Info");
        Movie movie = movieInfoClient.getMovieInfo(rating.getMovieId());
        return new CatalogItem(movie.getMovieName(), movie.getDescription(), rating.getRating());
    }

    public CatalogItem getFallbackCatalogItem(Rating rating) {
        return new CatalogItem("Movie Name not found", "", rating.getRating());
    }

    @HystrixCommand(fallbackMethod = "callFallBackForErrorUri")
    @Override
    public void callErrorUri() {
        System.out.println("Getting Movie Info");
        movieInfoClient.throwErrorToCheckBackOffRetry();
    }
    
    public void callFallBackForErrorUri() {
        System.out.println("FallBack Call");
    }
}
