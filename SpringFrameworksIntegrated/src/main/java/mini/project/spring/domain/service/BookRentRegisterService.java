package mini.project.spring.domain.service;

import java.util.List;
import java.util.Optional;

import mini.project.spring.domain.exception.NoContentException;
import mini.project.spring.domain.exception.NoSuchDataFoundToUpdate;
import mini.project.spring.domain.viewobject.BookRentRegisterVO;
import mini.project.spring.domain.viewobject.SearchCriteria;

public interface BookRentRegisterService {
	public Optional<BookRentRegisterVO> getBookRentRegister(Long regId);

	public List<BookRentRegisterVO> getAllBookRentRegisters(
			SearchCriteria searchCriteria) throws NoContentException;

	public Long createBookRentRegister(BookRentRegisterVO bookRentRegisterVO);

	public BookRentRegisterVO updateBookRentRegister(
			BookRentRegisterVO bookRentRegisterVO) throws NoSuchDataFoundToUpdate;
}
