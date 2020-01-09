package mini.project.spring.domain.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mini.project.spring.data.jpa.entity.User;
import mini.project.spring.data.jpa.repository.UserRepository;
import mini.project.spring.data.jpa.specification.UserSpecification;
import mini.project.spring.domain.exception.NoContentException;
import mini.project.spring.domain.exception.NoSuchDataFoundToUpdate;
import mini.project.spring.domain.service.UserService;
import mini.project.spring.domain.util.LibraryUtil;
import mini.project.spring.domain.viewobject.SearchCriteria;
import mini.project.spring.domain.viewobject.UserVO;
import mini.project.spring.domain.viewobject.converter.UserConverter;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);

	@Autowired
	private UserConverter converter;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Optional<UserVO> getUser(Long id) {
		logger.info("Find User with isbn {}", id);
		Optional<User> user = userRepository.findById(id);
		Optional<UserVO> userVO = user.map(converter::convertToVO);
		logger.info(userVO.toString());
		return userVO;
	}

	@Override
	public List<UserVO> getAllUsers() throws NoContentException {
		logger.info("Get all users");
		List<User> users = userRepository.findAll();
		LibraryUtil.verifyContent(users);
		List<UserVO> userVOs = converter.convertToVO(users);
		return userVOs;
	}

	@Override
	public Long createUser(UserVO userVO) {
		logger.info("Create User");
		User user = converter.convertToEO(userVO);
		return userRepository.save(user).getUserId();
	}

	@Override
	public UserVO updateUser(UserVO userVO) throws NoSuchDataFoundToUpdate {
		logger.info("Update user {}", userVO);
		boolean exists = userRepository.existsById(userVO.getUserId());
		if (!exists)
			throw new NoSuchDataFoundToUpdate(
					"Given User is not present in database to update");
		User user = converter.convertToEO(userVO);
		user = userRepository.save(user);
		return converter.convertToVO(user);
	}

	public List<UserVO> searchUser(String key, String value, String operation)
			throws NoContentException {
		SearchCriteria searchCriteria = new SearchCriteria(key, value,
				operation, 0, 0);
		UserSpecification userSpecification = new UserSpecification(
				searchCriteria);
		List<User> users = userRepository.findAll(userSpecification);
		LibraryUtil.verifyContent(users);
		return converter.convertToVO(users);
	}
}
