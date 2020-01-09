package mini.project.spring.domain.viewobject.converter;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mini.project.spring.data.jpa.entity.BookRentRegister;
import mini.project.spring.domain.viewobject.BookRentRegisterVO;

@Component
public class BookRentRegisterConverter {

	@Autowired
	private ModelMapper mapper;

	public BookRentRegisterVO convertToVO(BookRentRegister bookRentRegister) {
		return mapper.map(bookRentRegister, BookRentRegisterVO.class);
	}

	public BookRentRegister convertToEO(BookRentRegisterVO bookRentRegister) {
		return mapper.map(bookRentRegister, BookRentRegister.class);
	}

	public List<BookRentRegisterVO> convertToVO(List<BookRentRegister> src) {
		Type listType = new TypeToken<List<BookRentRegisterVO>>() {
		}.getType();
		return mapper.map(src, listType);
	}

	public List<BookRentRegister> convertToEO(List<BookRentRegisterVO> src) {
		Type listType = new TypeToken<List<BookRentRegister>>() {
		}.getType();
		return mapper.map(src, listType);
	}
}
