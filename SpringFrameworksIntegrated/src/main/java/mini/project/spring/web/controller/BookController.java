package mini.project.spring.web.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.micrometer.core.annotation.Timed;
import mini.project.spring.domain.exception.NoContentException;
import mini.project.spring.domain.exception.NoSuchDataFoundToUpdate;
import mini.project.spring.domain.service.BookService;
import mini.project.spring.domain.viewobject.BookVO;
import mini.project.spring.domain.viewobject.SearchCriteria;
import mini.project.spring.web.controller.resource.assembler.BookResourceAssembler;

@RestController
@RequestMapping(path = "/books")
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private BookResourceAssembler bookResourceAssembler;

	@Timed(value = "Books.GET")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BookVO>> getAllBooks(
			SearchCriteria searchCriteria) {
		List<BookVO> bookVOs = bookService.getAllBooks(searchCriteria);
		List<BookVO> bookResources = bookResourceAssembler.toResources(bookVOs);
		return ResponseEntity.ok(bookResources);
	}

	@GetMapping(path = "{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookVO> getBook(@PathVariable("isbn") String isbn) {
		Optional<BookVO> book = bookService.getBook(isbn)
				.map(bookResourceAssembler::toResource);
		return ResponseEntity.of(book);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookVO> createBook(@RequestBody BookVO book)
			throws NoContentException {
		String isbn = bookService.createBook(book);
		URI createdPath = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("{id}").buildAndExpand(isbn).toUri();
		return ResponseEntity.created(createdPath).build();
	}

	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookVO> updateBook(@RequestBody BookVO bookVO)
			throws NoSuchDataFoundToUpdate {
		BookVO book = bookService.updateBook(bookVO);
		return ResponseEntity.accepted().body(book);
	}

	@DeleteMapping(path = "{isbn}")
	public ResponseEntity<BookVO> deleteBook(
			@PathVariable("isbn") String isbn) {
		bookService.deleteBook(isbn);
		return ResponseEntity.noContent().build();
	}

}
