package app.test.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import app.model.entities.User;
import app.model.exceptions.DuplicateInstanceException;
import app.model.exceptions.IncorrectLoginException;
import app.model.exceptions.IncorrectPasswordException;
import app.model.exceptions.InstanceNotFoundException;
import app.model.service.UserService;

@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:context/service.xml", "classpath:context/persistence.xml",
		"classpath:context/application-context.xml" })
@TestPropertySource({ "classpath:config/persistence-access.properties", "classpath:config/service.properties" })
@Transactional
public class UserServiceTest {

	private final Long NON_EXISTENT_ID = -1l;

	@Autowired
	private UserService userService;

	private User createUser(String userName) {
		return new User(userName, "password", "firstName", "lastName", userName + "@" + userName + ".com");
	}

	@Test
	public void testSignUpAndLoginFromId() throws DuplicateInstanceException, InstanceNotFoundException {
		User user = createUser("user");
		userService.signUp(user);

		User loggedInUser = userService.loginFromId(user.getId());

		// assertEquals(User.RoleType.USER,user.getRole());
		assertEquals(user, loggedInUser);
	}

	@Test
	public void testSignUpDuplicatedUserName() throws DuplicateInstanceException {
		Assertions.assertThrows(DuplicateInstanceException.class, () -> {
			User user = createUser("user1");
			userService.signUp(user);
			userService.signUp(user);
		});
	}

	@Test
	public void testloginFromNonExistentId() throws InstanceNotFoundException {
		Assertions.assertThrows(InstanceNotFoundException.class, () -> {
			userService.loginFromId(NON_EXISTENT_ID);
		});
	}

	@Test
	public void testLogin() throws DuplicateInstanceException, IncorrectLoginException {
		User user = createUser("user2");
		String clearPassword = user.getPassword();
		userService.signUp(user);
		User loggedInUser = userService.login(user.getUserName(), clearPassword);

		assertEquals(user, loggedInUser);
	}

	@Test
	public void testLoginWithIncorrectPassword() throws DuplicateInstanceException, IncorrectLoginException {
		Assertions.assertThrows(IncorrectLoginException.class, () -> {
			User user = createUser("user3");
			String clearPassword = user.getPassword();
			userService.signUp(user);
			userService.login(user.getUserName(), 'X' + clearPassword);
		});
	}

	@Test
	public void testLoginWithNonExistentUserName() throws IncorrectLoginException {
		Assertions.assertThrows(IncorrectLoginException.class, () -> {
			userService.login("X", "Y");
		});
	}

	@Test
	public void testUpdateProfile() throws InstanceNotFoundException, DuplicateInstanceException {
		User user = createUser("user4");
		userService.signUp(user);
		userService.updateProfile(user.getId(), 'X' + user.getFirstName(), 'X' + user.getLastName(),
				'X' + user.getEmail());
		User updatedUser = userService.loginFromId(user.getId());

		user.setFirstName('X' + user.getFirstName());
		user.setLastName('X' + user.getLastName());
		user.setEmail('X' + user.getEmail());

		assertEquals(user, updatedUser);
	}

	@Test
	public void testUpdateProfileWithNonExistentId() throws InstanceNotFoundException {
		Assertions.assertThrows(InstanceNotFoundException.class, () -> {
			userService.updateProfile(NON_EXISTENT_ID, "X", "X", "X");
		});
	}

	@Test
	public void testChangePassword() throws DuplicateInstanceException, InstanceNotFoundException,
			IncorrectPasswordException, IncorrectLoginException {
		User user = createUser("user5");
		String oldPassword = user.getPassword();
		String newPassword = 'X' + oldPassword;

		userService.signUp(user);
		userService.changePassword(user.getId(), oldPassword, newPassword);
		userService.login(user.getUserName(), newPassword);
	}

	@Test
	public void testChangePasswordWithNonExistentId() throws InstanceNotFoundException, IncorrectPasswordException {
		Assertions.assertThrows(InstanceNotFoundException.class, () -> {
			userService.changePassword(NON_EXISTENT_ID, "X", "Y");
		});
	}

	@Test
	public void testChangePasswordWithIncorrectPassword()
			throws DuplicateInstanceException, InstanceNotFoundException, IncorrectPasswordException {
		Assertions.assertThrows(IncorrectPasswordException.class, () -> {
			User user = createUser("user6");
			String oldPassword = user.getPassword();
			String newPassword = 'X' + oldPassword;
			userService.signUp(user);
			userService.changePassword(user.getId(), 'Y' + oldPassword, newPassword);
		});

	}

}
