package app.model.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.model.dao.UserDao;
import app.model.entities.User;
import app.model.exceptions.DuplicateInstanceException;
import app.model.exceptions.IncorrectLoginException;
import app.model.exceptions.IncorrectPasswordException;
import app.model.exceptions.InstanceNotFoundException;

@Service
@Transactional
public class UserServiceImp implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private Checker checker;

	@Override
	public void signUp(User user) throws DuplicateInstanceException {

		if (userDao.existsByUserName(user.getUserName())) {
			throw new DuplicateInstanceException("project.entities.user", user.getUserName());
		}

		// user.setPassword(passwordEncoder.encode(user.getPassword()));

		userDao.save(user);
	}

	@Override
	public User login(String userName, String password) throws IncorrectLoginException {

		Optional<User> user = userDao.findByUserName(userName);

		if (!user.isPresent()) {
			throw new IncorrectLoginException(userName, password);
		}

		if (!password.equals(user.get().getPassword())) {
			// if (!passwordEncoder.matches(password, user.get().getPassword())) {
			throw new IncorrectLoginException(userName, password);
		}

		return user.get();
	}

	@Override
	public User loginFromId(Long id) throws InstanceNotFoundException {
		return checker.checkUser(id);
	}

	@Override
	public User updateProfile(Long id, String firstName, String lastName, String email)
			throws InstanceNotFoundException {

		User user = checker.checkUser(id);

		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);

		return user;
	}

	@Override
	public void changePassword(Long id, String oldPassword, String newPassword)
			throws IncorrectPasswordException, InstanceNotFoundException {

		User user = checker.checkUser(id);

		if (!oldPassword.equals(user.getPassword())) {
			// if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
			throw new IncorrectPasswordException();
		} else {
			// user.setPassword(passwordEncoder.encode(newPassword));
			user.setPassword(newPassword);
		}
	}

}
