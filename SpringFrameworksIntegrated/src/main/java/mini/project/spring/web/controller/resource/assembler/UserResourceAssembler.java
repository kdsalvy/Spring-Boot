package mini.project.spring.web.controller.resource.assembler;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import mini.project.spring.domain.viewobject.UserVO;
import mini.project.spring.web.controller.UserController;

@Component
public class UserResourceAssembler
		extends
			ResourceAssemblerSupport<UserVO, UserVO> {

	public UserResourceAssembler() {
		super(UserController.class, UserVO.class);
	}

	@Override
	public UserVO toResource(UserVO userVO) {
		Link userDetailLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(UserController.class)
						.getUser(userVO.getUserId()))
				.withRel("details")
				.withMedia(MediaType.APPLICATION_JSON_VALUE)
				.withType(HttpMethod.GET.name());
		Link userEditLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(UserController.class)
						.updateUser(userVO))
				.withRel("edit")
				.withMedia(MediaType.APPLICATION_JSON_VALUE)
				.withType(HttpMethod.PUT.name());
		Link userDeleteLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(UserController.class)
						.deleteUser(userVO.getUserId()))
				.withRel("delete")
				.withMedia(MediaType.APPLICATION_JSON_VALUE)
				.withType(HttpMethod.DELETE.name());
		userVO.add(userDetailLink);
		userVO.add(userEditLink);
		userVO.add(userDeleteLink);
		return userVO;
	}

}
