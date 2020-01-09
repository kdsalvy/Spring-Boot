package mini.project.spring.web.controller.resource.assembler;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import mini.project.spring.domain.viewobject.BookVO;
import mini.project.spring.web.controller.BookController;

@Component
public class BookResourceAssembler
		extends
			ResourceAssemblerSupport<BookVO, BookVO> {

	public BookResourceAssembler() {
		super(BookController.class, BookVO.class);
	}

	@Override
	public BookVO toResource(BookVO bookVO) {
		Link bookDetailLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(BookController.class)
						.getBook(bookVO.getIsbn()))
				.withSelfRel().withMedia(MediaType.APPLICATION_JSON_VALUE)
				.withType(HttpMethod.GET.name());
		Link bookEditLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(BookController.class)
						.updateBook(bookVO))
				.withRel("edit").withMedia(MediaType.APPLICATION_JSON_VALUE)
				.withType(HttpMethod.PUT.name());
		Link bookDeleteLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(BookController.class)
						.deleteBook(bookVO.getIsbn()))
				.withRel("delete").withMedia(MediaType.APPLICATION_JSON_VALUE)
				.withType(HttpMethod.DELETE.name());
		bookVO.add(bookDetailLink);
		bookVO.add(bookEditLink);
		bookVO.add(bookDeleteLink);
		return bookVO;
	}

	public BookVO createResourceWithId(String isbn, BookVO book) {
		return createResourceWithId(isbn, book, new Object[0]);
	}

}
