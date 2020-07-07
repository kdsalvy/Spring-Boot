package learn.kd.movies.info.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import learn.kd.movies.info.dto.Movie;
import learn.kd.movies.info.dto.MovieSummary;
import learn.kd.movies.info.service.MovieInfoService;

public class MovieInfoServiceImpl implements MovieInfoService {

    private static volatile int count = 0;

    @Value("${tmdb.api-key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
        System.out.println("\n\n\n Getting movie Info \n\n\n");
        MovieSummary movieSummary = restTemplate.getForObject("https://api.themoviedb.org/3/movie/" + movieId + "?language=en-US&api_key=" + apiKey, MovieSummary.class);
        return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
    }

    @Override
    public String throwErrorToCheckBackOffRetry() {
        ++count;
        System.out.println("Count: " + count);
        if (count % 2 == 0)
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        else
            return "I'm Healthy";
    }

}
