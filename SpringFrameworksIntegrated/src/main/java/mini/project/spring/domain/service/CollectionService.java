package mini.project.spring.domain.service;

import java.util.List;

import mini.project.spring.domain.viewobject.MovieVO;

public interface CollectionService {

	public List<MovieVO> acceptAll(List<MovieVO> movies);
}
