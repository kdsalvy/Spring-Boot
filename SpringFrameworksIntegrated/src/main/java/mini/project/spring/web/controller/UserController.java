package mini.project.spring.web.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.micrometer.core.annotation.Timed;
import mini.project.spring.domain.exception.NoContentException;
import mini.project.spring.domain.exception.NoSuchDataFoundToUpdate;
import mini.project.spring.domain.service.UserService;
import mini.project.spring.domain.viewobject.UserVO;
import mini.project.spring.web.controller.resource.assembler.UserResourceAssembler;

@RestController
@RequestMapping(path = "/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserResourceAssembler userResourceAssembler;

	@Timed(value="Users.GET")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserVO>> getAllUsers()
			throws Exception, NoContentException {
		return ResponseEntity.ok(
				userResourceAssembler.toResources(userService.getAllUsers()));
	}

	@GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserVO> getUser(@PathVariable("id") Long id) {
		Optional<UserVO> userResource = userService.getUser(id)
				.map(userResourceAssembler::toResource);
		return ResponseEntity.of(userResource);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserVO> createUser(@RequestBody UserVO user)
			throws NoContentException {
		Long id = userService.createUser(user);
		URI createdPath = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(createdPath).build();
	}

	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserVO> updateUser(@RequestBody UserVO userVO)
			throws NoSuchDataFoundToUpdate {
		return ResponseEntity.accepted().body(userResourceAssembler
				.toResource(userService.updateUser(userVO)));
	}

	@GetMapping("search")
	public ResponseEntity<List<UserVO>> searchUser(
			@RequestParam("key") String key,
			@RequestParam("value") String value,
			@RequestParam("operation") String operation)
			throws NoContentException {
		return ResponseEntity.ok(userResourceAssembler
				.toResources(userService.searchUser(key, value, operation)));
	}

	public ResponseEntity<UserVO> deleteUser(long userId) {
		// TODO Auto-generated method stub
		return null;
	}
}
