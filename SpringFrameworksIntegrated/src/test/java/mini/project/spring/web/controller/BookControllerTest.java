package mini.project.spring.web.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import mini.project.spring.SpringBootSampleApplication;
import mini.project.spring.config.ApplicationConfiguration;
import mini.project.spring.domain.exception.NoContentException;
import mini.project.spring.domain.exception.NoSuchDataFoundToUpdate;
import mini.project.spring.domain.exception.ResourceNotFoundException;
import mini.project.spring.domain.service.BookService;
import mini.project.spring.domain.viewobject.BookVO;
import mini.project.spring.domain.viewobject.SearchCriteria;
import mini.project.spring.web.controller.resource.assembler.BookResourceAssembler;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ApplicationConfiguration.class,
		SpringBootSampleApplication.class})
@WebMvcTest(BookController.class)
public class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService bookService;

	@MockBean
	private BookResourceAssembler bookResourceAssembler;

	@Test
	public void testGetAllBooksPresentTestCase() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/books");
		List<BookVO> books = Arrays.asList(
				new BookVO("1234ABCD", "TestBook", 1.0, new Date(), 1, 1));
		SearchCriteria searchCriteria = new SearchCriteria();

		when(bookService.getAllBooks(searchCriteria)).thenReturn(books);
		when(bookResourceAssembler.toResources(books)).thenReturn(books);

		mockMvc.perform(request).andExpect(status().isOk())
				.andExpect(content()
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString("1234ABCD")));
	}

	@Test
	public void testGetAllBooksNotPresentTestCase() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/books");
		List<BookVO> books = Collections.emptyList();
		SearchCriteria searchCriteria = new SearchCriteria();

		when(bookService.getAllBooks(searchCriteria))
				.thenThrow(NoContentException.class);
		when(bookResourceAssembler.toResources(books)).thenReturn(books);

		mockMvc.perform(request).andExpect(status().isNoContent())
				.andExpect(content().string("No Content Found"));
	}

	@Test(expected = Exception.class)
	public void testGetAllBooksExceptionTestCase() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/books");
		List<BookVO> books = Collections.emptyList();
		SearchCriteria searchCriteria = new SearchCriteria();

		when(bookService.getAllBooks(searchCriteria))
				.thenThrow(Exception.class);
		when(bookResourceAssembler.toResources(books)).thenReturn(books);

		mockMvc.perform(request);
	}

	@Test
	public void testGetBookPresentTestCase() throws Exception {
		String isbn = "1234ABCD";
		RequestBuilder request = MockMvcRequestBuilders.get("/books/{isbn}",
				isbn);
		BookVO bookVO = new BookVO("1234ABCD", "TestBook", 1.0, new Date(), 1,
				1);
		Optional<BookVO> book = Optional.ofNullable(bookVO);

		when(bookService.getBook(isbn)).thenReturn(book);
		when(bookResourceAssembler.toResource(bookVO)).thenReturn(bookVO);

		mockMvc.perform(request).andExpect(status().isOk())
				.andExpect(content()
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string(containsString("1234ABCD")));
	}

	@Test
	public void testGetBookNotPresentTestCase() throws Exception {
		String isbn = "1234ABCD";
		RequestBuilder request = MockMvcRequestBuilders.get("/books/{isbn}",
				isbn);
		BookVO bookVO = null;
		Optional<BookVO> book = Optional.empty();

		when(bookService.getBook(isbn)).thenReturn(book);
		when(bookResourceAssembler.toResource(bookVO)).thenReturn(bookVO);

		mockMvc.perform(request).andExpect(status().isNotFound());
	}

	@Test(expected = Exception.class)
	public void testGetBookExceptionTestCase() throws Exception {
		String isbn = "1234ABCD";
		RequestBuilder request = MockMvcRequestBuilders.get("/books/{isbn}",
				isbn);
		BookVO bookVO = null;

		when(bookService.getBook(isbn)).thenThrow(Exception.class);
		when(bookResourceAssembler.toResource(bookVO)).thenReturn(bookVO);

		mockMvc.perform(request);
	}

	@Test
	public void testCreateBookSuccess() throws Exception {
		BookVO book = new BookVO("1234ABCD", "TestBook", 1.0, new Date(), 1, 1);
		ObjectMapper mapper = new ObjectMapper();
		RequestBuilder request = MockMvcRequestBuilders.post("/books")
				.content(mapper.writeValueAsBytes(book))
				.contentType(MediaType.APPLICATION_JSON_VALUE);

		when(bookService.createBook(book)).thenReturn(book.getIsbn());

		mockMvc.perform(request).andExpect(status().isCreated());
	}

	@Test
	public void testCreateBookBadRequest() throws Exception {
		BookVO book = new BookVO();
		RequestBuilder request = MockMvcRequestBuilders.post("/books")
				.content("").contentType(MediaType.APPLICATION_JSON_VALUE);

		when(bookService.createBook(book)).thenReturn(book.getIsbn());

		mockMvc.perform(request).andExpect(status().isBadRequest());
	}

	@Test(expected = Exception.class)
	public void testCreateBookException() throws Exception {
		BookVO book = new BookVO("1234ABCD", "TestBook", 1.0, new Date(), 1, 1);
		ObjectMapper mapper = new ObjectMapper();
		RequestBuilder request = MockMvcRequestBuilders.post("/books")
				.content(mapper.writeValueAsBytes(book))
				.contentType(MediaType.APPLICATION_JSON_VALUE);

		when(bookService.createBook(book)).thenThrow(Exception.class);

		mockMvc.perform(request);
	}

	@Test()
	public void testUpdateBookSuccess() throws Exception {
		BookVO book = new BookVO("1234ABCD", "UpdatedTestBook", 1.0, new Date(),
				1, 1);
		ObjectMapper mapper = new ObjectMapper();
		RequestBuilder request = MockMvcRequestBuilders.put("/books")
				.content(mapper.writeValueAsBytes(book))
				.contentType(MediaType.APPLICATION_JSON_VALUE);

		when(bookService.updateBook(book)).thenReturn(book);

		mockMvc.perform(request).andExpect(status().isAccepted())
				.andExpect(content().string(containsString("UpdatedTestBook")));
	}

	@Test()
	public void testUpdateBookInvalidJson() throws Exception {
		BookVO book = new BookVO();
		RequestBuilder request = MockMvcRequestBuilders.put("/books")
				.content("").contentType(MediaType.APPLICATION_JSON_VALUE);

		when(bookService.updateBook(book)).thenReturn(book);

		mockMvc.perform(request).andExpect(status().isBadRequest());
	}

	@Test
	public void testUpdateBookNotPresent() throws Exception {
		BookVO book = new BookVO();
		RequestBuilder request = MockMvcRequestBuilders.put("/books")
				.content("").contentType(MediaType.APPLICATION_JSON_VALUE);

		when(bookService.updateBook(book))
				.thenThrow(NoSuchDataFoundToUpdate.class);

		mockMvc.perform(request).andExpect(status().isBadRequest());
	}

	@Test(expected = Exception.class)
	public void testUpdateBookException() throws Exception {
		BookVO book = new BookVO();
		RequestBuilder request = MockMvcRequestBuilders.put("/books")
				.content("").contentType(MediaType.APPLICATION_JSON_VALUE);

		when(bookService.updateBook(book)).thenThrow(Exception.class);

		mockMvc.perform(request);
	}

	@Test
	public void testDeleteBookSuccess() throws Exception {
		String isbn = "1234ABCD";
		RequestBuilder request = MockMvcRequestBuilders
				.delete("/books/{isbn}", isbn).content("")
				.contentType(MediaType.APPLICATION_JSON_VALUE);

		doNothing().when(bookService).deleteBook(isbn);

		mockMvc.perform(request).andExpect(status().isNoContent());
	}

	@Test
	public void testDeleteBookFailed() throws Exception {
		String isbn = "1234ABCD";
		RequestBuilder request = MockMvcRequestBuilders
				.delete("/books/{isbn}", isbn).content("")
				.contentType(MediaType.APPLICATION_JSON_VALUE);

		doThrow(ResourceNotFoundException.class).when(bookService)
				.deleteBook(isbn);

		mockMvc.perform(request).andExpect(status().isNotFound());
	}

	@Test(expected = Exception.class)
	public void testDeleteBookException() throws Exception {
		String isbn = "1234ABCD";
		RequestBuilder request = MockMvcRequestBuilders
				.delete("/books/{isbn}", isbn).content("")
				.contentType(MediaType.APPLICATION_JSON_VALUE);

		doThrow(Exception.class).when(bookService).deleteBook(isbn);

		mockMvc.perform(request);
	}
}
