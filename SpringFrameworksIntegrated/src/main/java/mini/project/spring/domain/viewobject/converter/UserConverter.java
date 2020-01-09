package mini.project.spring.domain.viewobject.converter;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mini.project.spring.data.jpa.entity.User;
import mini.project.spring.domain.viewobject.UserVO;

@Component
public class UserConverter {

	@Autowired
	private ModelMapper mapper;

	public UserVO convertToVO(User user) {
		return mapper.map(user, UserVO.class);
	}

	public User convertToEO(UserVO user) {
		return mapper.map(user, User.class);
	}

	public List<UserVO> convertToVO(List<User> src) {
		Type listType = new TypeToken<List<UserVO>>() {
		}.getType();
		return mapper.map(src, listType);
	}

	public List<User> convertToEO(List<UserVO> src) {
		Type listType = new TypeToken<List<User>>() {
		}.getType();
		return mapper.map(src, listType);
	}
}
