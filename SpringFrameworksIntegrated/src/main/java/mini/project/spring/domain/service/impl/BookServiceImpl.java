package mini.project.spring.domain.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mini.project.spring.data.jpa.entity.Book;
import mini.project.spring.data.jpa.repository.BookRepository;
import mini.project.spring.data.jpa.specification.BookSpecification;
import mini.project.spring.domain.exception.NoContentException;
import mini.project.spring.domain.exception.NoSuchDataFoundToUpdate;
import mini.project.spring.domain.exception.ResourceNotFoundException;
import mini.project.spring.domain.service.BookService;
import mini.project.spring.domain.util.LibraryUtil;
import mini.project.spring.domain.viewobject.BookVO;
import mini.project.spring.domain.viewobject.SearchCriteria;
import mini.project.spring.domain.viewobject.converter.BookConverter;

@Service
public class BookServiceImpl implements BookService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BookServiceImpl.class);

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookConverter converter;

	@Override
	public Optional<BookVO> getBook(String isbn)
			throws ResourceNotFoundException {
		LOGGER.info("Find book with isbn {}", isbn);
		Optional<Book> book = bookRepository.findById(isbn);

		Optional<BookVO> bookVO = book.map(converter::convertToVO);
		LOGGER.info(bookVO.toString());
		return bookVO;
	}

	@Override
	public List<BookVO> getAllBooks(SearchCriteria searchCriteria)
			throws NoContentException {
		LOGGER.info("Get all books with Search Criteria: " + searchCriteria);
		BookSpecification bookSpecification = new BookSpecification(
				searchCriteria);
		Pageable pageable = PageRequest.of(searchCriteria.getPage(),
				searchCriteria.getLimit());
		Page<Book> page = bookRepository.findAll(bookSpecification, pageable);
		List<Book> books = page.getContent();
		LibraryUtil.verifyContent(books);
		List<BookVO> bookVOs = converter.convertToVO(books);
		return bookVOs;
	}

	@Override
	public String createBook(BookVO bookVO) {
		LOGGER.info("Create Book");
		Book book = converter.convertToEO(bookVO);
		return bookRepository.save(book).getIsbn();
	}

	@Override
	public BookVO updateBook(BookVO bookVO) throws NoSuchDataFoundToUpdate {
		LOGGER.info("Update book ");
		boolean exists = bookRepository.existsById(bookVO.getIsbn());
		String errorMsg = "Book is not present in database to update";
		LibraryUtil.existsElseThrow(exists, errorMsg);
		Book book = converter.convertToEO(bookVO);
		book = bookRepository.save(book);
		return converter.convertToVO(book);
	}

	@Override
	public void deleteBook(String isbn) {
		LOGGER.info("Delete book with isbn {}", isbn);
		Supplier<ResourceNotFoundException> resourceNotFoundException = () -> new ResourceNotFoundException(
				"No matching book found to delete");
		Book book = bookRepository.findById(isbn)
				.orElseThrow(resourceNotFoundException);
		bookRepository.delete(book);
	}

}