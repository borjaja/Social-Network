package app.controller.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class UserLoginDto implements Serializable {

	private static final long serialVersionUID = 156L;
	@Email
	@NotBlank
	String email;

	@NotBlank(message = "password required.")
	String pass;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

}
