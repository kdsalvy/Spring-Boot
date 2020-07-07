package learn.kd.movies.info.service;

import learn.kd.movies.info.dto.Movie;

public interface MovieInfoService {

    public Movie getMovieInfo(String movieId);

    public String throwErrorToCheckBackOffRetry();

}
