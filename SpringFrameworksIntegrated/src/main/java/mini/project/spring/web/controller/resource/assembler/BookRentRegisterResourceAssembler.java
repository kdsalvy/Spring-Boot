package mini.project.spring.web.controller.resource.assembler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import mini.project.spring.domain.viewobject.BookRentRegisterVO;
import mini.project.spring.domain.viewobject.BookVO;
import mini.project.spring.domain.viewobject.UserVO;
import mini.project.spring.web.controller.BookRentRegisterController;

@Component
@RequestScope
public class BookRentRegisterResourceAssembler
		extends
			ResourceAssemblerSupport<BookRentRegisterVO, BookRentRegisterVO> {

	@Autowired
	private UserResourceAssembler userResourceAssembler;

	@Autowired
	private BookResourceAssembler bookResourceAssembler;

	public BookRentRegisterResourceAssembler() {
		super(BookRentRegisterController.class, BookRentRegisterVO.class);
	}

	@Override
	public BookRentRegisterVO toResource(
			BookRentRegisterVO bookRentRegisterVO) {
		Link bookRentRegisterDetailLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder
						.methodOn(BookRentRegisterController.class)
						.getBookRentRegister(bookRentRegisterVO.getRentId()))
				.withRel("details").withMedia(MediaType.APPLICATION_JSON_VALUE)
				.withType(HttpMethod.GET.name());
		Link bookRentRegisterEditLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder
						.methodOn(BookRentRegisterController.class)
						.updateBookRentRegister(bookRentRegisterVO))
				.withRel("edit").withMedia(MediaType.APPLICATION_JSON_VALUE)
				.withType(HttpMethod.PUT.name());
		Link bookRentRegisterDeleteLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder
						.methodOn(BookRentRegisterController.class)
						.deleteBookRentRegister(bookRentRegisterVO.getRentId()))
				.withRel("delete").withMedia(MediaType.APPLICATION_JSON_VALUE)
				.withType(HttpMethod.DELETE.name());

		UserVO user = userResourceAssembler
				.toResource(bookRentRegisterVO.getUser());
		List<BookVO> books = bookResourceAssembler
				.toResources(bookRentRegisterVO.getBooks());
		bookRentRegisterVO.setUser(user);
		bookRentRegisterVO.setBooks(books);

		bookRentRegisterVO.add(bookRentRegisterDetailLink);
		bookRentRegisterVO.add(bookRentRegisterEditLink);
		bookRentRegisterVO.add(bookRentRegisterDeleteLink);
		return bookRentRegisterVO;
	}

}
