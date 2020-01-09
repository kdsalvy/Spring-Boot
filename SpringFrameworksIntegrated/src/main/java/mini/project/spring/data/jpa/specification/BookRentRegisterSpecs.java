package mini.project.spring.data.jpa.specification;

import java.util.Date;

import org.springframework.data.jpa.domain.Specification;

import mini.project.spring.data.jpa.entity.BookRentRegister;
import mini.project.spring.data.jpa.entity.BookRentRegister_;
import mini.project.spring.data.jpa.entity.User;

public class BookRentRegisterSpecs {

	public static Specification<BookRentRegister> matchByRentId(Long rentId) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
				.equal(root.get(BookRentRegister_.rentId), rentId);
	}

	public static Specification<BookRentRegister> matchBetweenRentedDates(
			Date startDate, Date endDate) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
				.between(root.get(BookRentRegister_.rentedDate), startDate,
						endDate);
	}

	public static Specification<BookRentRegister> matchByUser(User user) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
				.equal(root.get(BookRentRegister_.user), user);
	}
}
