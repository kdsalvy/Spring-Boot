package mini.project.spring.domain.viewobject.converter;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mini.project.spring.data.jpa.entity.Book;
import mini.project.spring.domain.viewobject.BookVO;

@Component
public class BookConverter {

	@Autowired
	private ModelMapper mapper;

	public BookVO convertToVO(Book book) {
		return mapper.map(book, BookVO.class);
	}

	public Book convertToEO(BookVO book) {
		return mapper.map(book, Book.class);
	}

	public List<BookVO> convertToVO(List<Book> src) {
		Type listType = new TypeToken<List<BookVO>>() {
		}.getType();
		return mapper.map(src, listType);
	}

	public List<Book> convertToEO(List<BookVO> src) {
		Type listType = new TypeToken<List<Book>>() {
		}.getType();
		return mapper.map(src, listType);
	}
}
