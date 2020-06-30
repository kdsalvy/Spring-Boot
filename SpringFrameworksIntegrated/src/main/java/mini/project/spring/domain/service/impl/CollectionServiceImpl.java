package mini.project.spring.domain.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import mini.project.spring.domain.service.CollectionService;
import mini.project.spring.domain.viewobject.MovieVO;

@Service
public class CollectionServiceImpl implements CollectionService {

	@Override
	public List<MovieVO> acceptAll(List<MovieVO> movies) {
		return movies;
	}

}
