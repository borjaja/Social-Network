package app.model.service;

import app.model.entities.User;
import app.model.exceptions.DuplicateInstanceException;
import app.model.exceptions.IncorrectLoginException;
import app.model.exceptions.IncorrectPasswordException;
import app.model.exceptions.InstanceNotFoundException;

public interface UserService {

	void signUp(User user) throws DuplicateInstanceException;

	User login(String userName, String password) throws IncorrectLoginException;

	User loginFromId(Long id) throws InstanceNotFoundException;

	User updateProfile(Long id, String firstName, String lastName, String email) throws InstanceNotFoundException;

	void changePassword(Long id, String oldPassword, String newPassword)
			throws InstanceNotFoundException, IncorrectPasswordException;
}
