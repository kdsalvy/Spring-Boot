package learn.kd.catalog.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import learn.kd.catalog.service.model.Movie;

@FeignClient(value = "movie-info-service")
public interface MovieInfoClient {

    @RequestMapping(method = RequestMethod.GET, value = "/movies/{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") String movieId);

    @RequestMapping(method = RequestMethod.GET, value = "/movies/error")
    public String throwErrorToCheckBackOffRetry();

}
