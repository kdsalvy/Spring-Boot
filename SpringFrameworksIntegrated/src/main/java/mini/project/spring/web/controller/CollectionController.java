package mini.project.spring.web.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mini.project.spring.domain.service.CollectionService;
import mini.project.spring.domain.viewobject.MovieVO;

@Validated
@RestController
@RequestMapping("/collections")
public class CollectionController {

	@Autowired
	private CollectionService service;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<MovieVO> validateAndAcceptCollection(@RequestBody @NotEmpty List<@Valid MovieVO> movies) {
		return service.acceptAll(movies);
	}
}
