package mini.project.spring.web.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import mini.project.spring.domain.service.BookRentRegisterService;
import mini.project.spring.domain.viewobject.BookRentRegisterVO;
import mini.project.spring.domain.viewobject.SearchCriteria;
import mini.project.spring.web.controller.resource.assembler.BookRentRegisterResourceAssembler;

@RestController
@RequestMapping(path = "/bookrentregisters")
public class BookRentRegisterController {

	@Autowired
	private BookRentRegisterService bookRentRegisterService;

	@Autowired
	private BookRentRegisterResourceAssembler resourceAssembler;
	
	@Timed(value="BRR.GET")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BookRentRegisterVO>> getAllBookRentRegisters(
			SearchCriteria searchCriteria)
			throws Exception, NoContentException {
		List<BookRentRegisterVO> books = bookRentRegisterService
				.getAllBookRentRegisters(searchCriteria);
		return ResponseEntity.ok(resourceAssembler.toResources(books));
	}

	@GetMapping(path = "{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookRentRegisterVO> getBookRentRegister(
			@PathVariable("id") Long rentId) {
		Optional<BookRentRegisterVO> bookRentRegisterResource = bookRentRegisterService
				.getBookRentRegister(rentId).map(resourceAssembler::toResource);
		return ResponseEntity.of(bookRentRegisterResource);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookRentRegisterVO> createBookRentRegister(
			@RequestBody BookRentRegisterVO bookRentRegister)
			throws NoContentException {
		Long rentId = bookRentRegisterService
				.createBookRentRegister(bookRentRegister);
		URI createdPath = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(rentId).toUri();
		return ResponseEntity.created(createdPath).build();
	}

	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookRentRegisterVO> updateBookRentRegister(
			@RequestBody BookRentRegisterVO bookRentRegisterVO)
			throws NoSuchDataFoundToUpdate {
		BookRentRegisterVO bookRentRegisterResource = resourceAssembler
				.toResource(bookRentRegisterService
						.updateBookRentRegister(bookRentRegisterVO));
		return ResponseEntity.accepted().body(bookRentRegisterResource);
	}

	public ResponseEntity<BookRentRegisterVO> deleteBookRentRegister(
			long rentId) {
		// TODO Auto-generated method stub
		return null;
	}
}
