package learn.kd.catalog.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import learn.kd.catalog.service.CatalogInfo;
import learn.kd.catalog.service.UserRatingInfo;
import learn.kd.catalog.service.model.CatalogItem;
import learn.kd.catalog.service.model.UserRating;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path= "user")
public class UsersMovieCatalogResource {

    @Autowired
    private CatalogInfo catalogInfo;

    @Autowired
    private UserRatingInfo userRatingInfo;

    @GetMapping(path = "{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        log.info("Get all movies and the ratings saved by userId: {}", userId);
        
        UserRating userRating = userRatingInfo.getUserRatings(userId);

        return userRating.getRatings()
            .stream()
            .map(rating -> catalogInfo.getCatalogItem(rating))
            .collect(Collectors.toList());
    }
    
    @GetMapping(path = "demo-error")
    public void checkRetryMechanism() {
        catalogInfo.callErrorUri();
    }

}
