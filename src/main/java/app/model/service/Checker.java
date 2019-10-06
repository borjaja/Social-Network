package app.model.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.model.dao.UserDao;
import app.model.entities.User;
import app.model.exceptions.InstanceNotFoundException;

@Service
@Transactional(readOnly = true)
public class Checker {

	@Autowired
	private UserDao userDao;

	public void checkUserExists(Long userId) throws InstanceNotFoundException {
		if (!userDao.existsById(userId)) {
			throw new InstanceNotFoundException("project.entities.user", userId);
		}
	}

	public User checkUser(Long userId) throws InstanceNotFoundException {
		Optional<User> user = userDao.findById(userId);
		if (!user.isPresent()) {
			throw new InstanceNotFoundException("project.entities.user", userId);
		}
		return user.get();
	}

}
