package learn.kd.catalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import learn.kd.catalog.service.dto.CatalogItem;
import learn.kd.catalog.service.dto.Movie;
import learn.kd.catalog.service.dto.Rating;

@Service
public class MovieInfo {

    @Autowired
    private RestTemplate restTemplate;

    @Retryable
    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem",
        threadPoolKey = "moviesInfoThreadPool",
        threadPoolProperties = {
                @HystrixProperty(name = "coreSize", value = "20"),
                @HystrixProperty(name = "maxQueueSize", value = "10")
        },
        commandProperties = {
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "90"),
                @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
        })
    public CatalogItem getCatalogItem(Rating rating) {
        System.out.println("Getting Movie Info");
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getMovieName(), movie.getDescription(), rating.getRating());
    }

    public CatalogItem getFallbackCatalogItem(Rating rating) {
        return new CatalogItem("Movie Name not found", "", rating.getRating());
    }

    @HystrixCommand(fallbackMethod = "callFallBackForErrorUri")
    public void callErrorUri() {
        System.out.println("Getting Movie Info");
        restTemplate.getForObject("http://movie-info-service/error/", Object.class);
    }
    
    public void callFallBackForErrorUri() {
        System.out.println("FallBack Call");
    }
}
