package mini.project.spring.domain.service;

import java.util.List;
import java.util.Optional;

import mini.project.spring.domain.viewobject.BookVO;
import mini.project.spring.domain.viewobject.SearchCriteria;

public interface BookService {
	public Optional<BookVO> getBook(String isbn);

	public List<BookVO> getAllBooks(SearchCriteria searchCriteria);

	public String createBook(BookVO bookVO);

	public BookVO updateBook(BookVO bookVO);

	public void deleteBook(String isbn);
}
