package learn.kd.rating.data.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import learn.kd.rating.data.dto.Rating;
import learn.kd.rating.data.dto.UserRating;

@RestController
@RequestMapping("ratings")
public class RatingController {

    @Autowired
    private Environment env;

    @GetMapping(path = "{movieId}")
    public Rating getRatingData(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 3);
    }

    @GetMapping(path = "users/{userId}")
    public UserRating getUserRating(@PathVariable("userId") String userId) {
        System.out.println("Ratings App on port: " + env.getProperty("server.port"));
        List<Rating> ratings = Arrays.asList(new Rating("100", 4), new Rating("200", 3));
        return new UserRating(ratings);
    }
}
