package mini.project.spring.domain.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import mini.project.spring.data.jpa.entity.Book;
import mini.project.spring.data.jpa.repository.BookRepository;
import mini.project.spring.data.jpa.specification.BookSpecification;
import mini.project.spring.domain.exception.NoContentException;
import mini.project.spring.domain.exception.NoSuchDataFoundToUpdate;
import mini.project.spring.domain.exception.ResourceNotFoundException;
import mini.project.spring.domain.service.impl.BookServiceImpl;
import mini.project.spring.domain.viewobject.BookVO;
import mini.project.spring.domain.viewobject.SearchCriteria;
import mini.project.spring.domain.viewobject.converter.BookConverter;

@RunWith(SpringRunner.class)
public class BookServiceImplTest {

	@TestConfiguration
	static class BookServiceImplTestContextConfiguration {

		@Bean
		public BookService bookService() {
			return new BookServiceImpl();
		}
	}

	@Autowired
	private BookService bookService;

	@MockBean
	private BookRepository bookRepository;

	@MockBean
	private BookConverter converter;

	@Test
	public void testGetBookWhenFound() {
		String isbn = "1234ABCD";
		Book book = new Book("1234ABCD", "TestBook", 1.0, new Date(), 1, 1);
		BookVO bookVO = new BookVO("1234ABCD", "TestBook", 1.0, new Date(), 1,
				1);

		when(bookRepository.findById(isbn)).thenReturn(Optional.of(book));
		when(converter.convertToVO(book)).thenReturn(bookVO);

		assertTrue(bookService.getBook(isbn).equals(Optional.of(bookVO)));
	}

	@Test
	public void testGetBookWhenNotFound() {
		String isbn = "1234ABCD";
		Book book = null;
		BookVO bookVO = null;

		when(bookRepository.findById(isbn))
				.thenReturn(Optional.ofNullable(book));
		when(converter.convertToVO(book)).thenReturn(bookVO);

		assertTrue(
				bookService.getBook(isbn).equals(Optional.ofNullable(bookVO)));
	}

	@Test(expected = Exception.class)
	public void testGetBookWhenExceptionThrown() {

		when(bookRepository.findById(Mockito.anyString()))
				.thenThrow(Exception.class);
		bookService.getBook(Mockito.anyString());
	}

	@Test
	public void testGetAllBooksWhenFound() {
		Book book = new Book("1234ABCD", "TestBook", 1.0, new Date(), 1, 1);
		BookVO bookVO = new BookVO("1234ABCD", "TestBook", 1.0, new Date(), 1,
				1);
		List<Book> books = Arrays.asList(book);
		Page<Book> page = new PageImpl<>(books);
		List<BookVO> bookVOs = Arrays.asList(bookVO);
		SearchCriteria criteria = new SearchCriteria("", "", "", 1, 1);

		when(bookRepository.findAll(Mockito.any(BookSpecification.class),
				Mockito.any(Pageable.class))).thenReturn(page);
		when(converter.convertToVO(books)).thenReturn(bookVOs);

		assertTrue(bookService.getAllBooks(criteria).equals(bookVOs));
	}

	@Test(expected = NoContentException.class)
	public void testGetAllBooksWhenNotFound() {
		List<Book> books = Collections.emptyList();
		List<BookVO> bookVOs = Collections.emptyList();
		Page<Book> page = new PageImpl<>(books);
		SearchCriteria criteria = new SearchCriteria("", "", "", 1, 1);

		when(bookRepository.findAll(Mockito.any(BookSpecification.class), Mockito.any(Pageable.class)))
				.thenReturn(page);
		when(converter.convertToVO(books)).thenReturn(bookVOs);

		bookService.getAllBooks(criteria);
	}

	@Test(expected = Exception.class)
	public void testGetAllBooksWhenExceptionThrown() {
		when(bookRepository.findAll(Mockito.any(BookSpecification.class)))
				.thenThrow(Exception.class);

		bookService.getAllBooks(null);
	}

	@Test
	public void testCreateBookWhenCreated() {
		Book book = new Book("1234ABCD", "TestBook", 1.0, new Date(), 1, 1);
		BookVO bookVO = new BookVO("1234ABCD", "TestBook", 1.0, new Date(), 1,
				1);

		when(bookRepository.save(book)).thenReturn(book);
		when(converter.convertToEO(bookVO)).thenReturn(book);

		assertTrue(bookService.createBook(bookVO).equals(book.getIsbn()));
	}

	@Test(expected = Exception.class)
	public void testCreateBookWhenExceptionThrown() {
		when(bookRepository.save(Mockito.any(Book.class)))
				.thenThrow(Exception.class);

		bookService.createBook(Mockito.any(BookVO.class));
	}

	@Test
	public void testUpdateBookWhenFound() {
		Book book = new Book("1234ABCD", "TestBook", 1.0, new Date(), 1, 1);
		BookVO bookVO = new BookVO("1234ABCD", "TestBook", 1.0, new Date(), 1,
				1);

		when(bookRepository.existsById(Mockito.anyString())).thenReturn(true);
		when(bookRepository.save(book)).thenReturn(book);
		when(converter.convertToVO(book)).thenReturn(bookVO);
		when(converter.convertToEO(bookVO)).thenReturn(book);

		assertTrue(bookService.updateBook(bookVO).equals(bookVO));
	}

	@Test(expected = NoSuchDataFoundToUpdate.class)
	public void testUpdateBookWhenNotFound() {
		BookVO bookVO = new BookVO("1234ABCD", "TestBook", 1.0, new Date(), 1,
				1);

		when(bookRepository.existsById(bookVO.getIsbn())).thenReturn(false);

		bookService.updateBook(bookVO);
	}

	@Test(expected = RuntimeException.class)
	public void testUpdateBookWhenExceptionThrown() {
		Book book = new Book("1234ABCD", "TestBook", 1.0, new Date(), 1, 1);
		BookVO bookVO = new BookVO("1234ABCD", "TestBook", 1.0, new Date(), 1,
				1);

		when(bookRepository.existsById(bookVO.getIsbn())).thenReturn(true);
		when(bookRepository.save(book)).thenThrow(RuntimeException.class);
		when(converter.convertToVO(book)).thenReturn(bookVO);
		when(converter.convertToEO(bookVO)).thenReturn(book);

		bookService.updateBook(bookVO);
	}

	@Test
	public void testDeleteBookWhenFound() {
		Book book = new Book("1234ABCD", "TestBook", 1.0, new Date(), 1, 1);

		when(bookRepository.findById(book.getIsbn()))
				.thenReturn(Optional.of(book));
		doNothing().when(bookRepository).delete(book);

		bookService.deleteBook(book.getIsbn());
		verify(bookRepository).delete(book);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testDeleteBookWhenNotFound() {
		String isbn = "1234ABCD";

		when(bookRepository.findById(isbn))
				.thenReturn(Optional.ofNullable(null));

		bookService.deleteBook(isbn);
	}

	@Test(expected = Exception.class)
	public void testDeleteBookWhenExceptionThrown() {
		String isbn = "1234ABCD";

		when(bookRepository.findById(isbn)).thenThrow(Exception.class);

		bookService.deleteBook(isbn);
	}
}
