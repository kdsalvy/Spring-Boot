package mini.project.spring.domain.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mini.project.spring.data.jpa.entity.BookRentRegister;
import mini.project.spring.data.jpa.repository.BookRentRegisterRepository;
import mini.project.spring.data.jpa.specification.BookRentRegisterSpecification;
import mini.project.spring.domain.exception.NoSuchDataFoundToUpdate;
import mini.project.spring.domain.service.BookRentRegisterService;
import mini.project.spring.domain.util.LibraryUtil;
import mini.project.spring.domain.viewobject.BookRentRegisterVO;
import mini.project.spring.domain.viewobject.SearchCriteria;
import mini.project.spring.domain.viewobject.converter.BookRentRegisterConverter;

@Service
public class BookRentRegisterServiceImpl implements BookRentRegisterService {

	private static final Logger logger = LoggerFactory
			.getLogger(BookRentRegisterServiceImpl.class);

	@Autowired
	private BookRentRegisterRepository bookRentRegisterRepository;

	@Autowired
	private BookRentRegisterConverter converter;

	@Override
	public Optional<BookRentRegisterVO> getBookRentRegister(Long regId) {
		logger.info("Find bookRentRegister with registration id {}", regId);
		Optional<BookRentRegister> bookRentRegister = bookRentRegisterRepository
				.findById(regId);
		Optional<BookRentRegisterVO> bookRentRegisterVO = bookRentRegister
				.map(converter::convertToVO);
		logger.info(bookRentRegisterVO.toString());
		return bookRentRegisterVO;
	}

	@Override
	public List<BookRentRegisterVO> getAllBookRentRegisters(
			SearchCriteria searchCriteria) {
		logger.info("Get all bookRentRegisters with Search Criteria: "
				+ searchCriteria);
		BookRentRegisterSpecification bookRentRegisterSpecification = new BookRentRegisterSpecification(
				searchCriteria);
		List<BookRentRegister> bookRentRegisters = bookRentRegisterRepository
				.findAll(bookRentRegisterSpecification);
		LibraryUtil.verifyContent(bookRentRegisters);
		List<BookRentRegisterVO> bookRentRegisterVOs = converter
				.convertToVO(bookRentRegisters);
		return bookRentRegisterVOs;
	}

	@Override
	public Long createBookRentRegister(BookRentRegisterVO bookRentRegisterVO) {
		logger.info("Create BookRentRegister");
		BookRentRegister bookRentRegister = converter
				.convertToEO(bookRentRegisterVO);
		return bookRentRegisterRepository.save(bookRentRegister).getRentId();
	}

	@Override
	public BookRentRegisterVO updateBookRentRegister(
			BookRentRegisterVO bookRentRegisterVO) {
		logger.info("Update bookRentRegister ");
		boolean exists = bookRentRegisterRepository
				.existsById(bookRentRegisterVO.getRentId());
		if (!exists)
			throw new NoSuchDataFoundToUpdate(
					"Given BookRentRegister is not present in database to update");
		BookRentRegister bookRentRegister = converter
				.convertToEO(bookRentRegisterVO);
		bookRentRegister = bookRentRegisterRepository.save(bookRentRegister);
		return converter.convertToVO(bookRentRegister);
	}
}
