package mini.project.spring.domain.service;

import java.util.List;
import java.util.Optional;

import mini.project.spring.domain.exception.NoContentException;
import mini.project.spring.domain.exception.NoSuchDataFoundToUpdate;
import mini.project.spring.domain.viewobject.UserVO;

public interface UserService {
    public Optional<UserVO> getUser(Long id);

    public List<UserVO> getAllUsers() throws NoContentException;

    public Long createUser(UserVO userVO);

    public UserVO updateUser(UserVO userVO) throws NoSuchDataFoundToUpdate;

    public List<UserVO> searchUser(String key, String value, String operation) throws NoContentException;
}
