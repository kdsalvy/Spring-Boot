package learn.kd.movies.info.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import learn.kd.movies.info.dto.Movie;
import learn.kd.movies.info.dto.MovieSummary;

@RestController
@RequestMapping(path = "movies")
public class MovieInfoResource {

    @Value("${tmdb.api-key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(path = "{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
        MovieSummary movieSummary = restTemplate.getForObject("https://api.themoviedb.org/3/movie/"+ movieId +"?language=en-US&api_key="+ apiKey, MovieSummary.class);
        return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
    }
    
    @GetMapping(path = "/error")
    public MovieSummary throwErrorToCheckBackOffRetry() {
        throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
