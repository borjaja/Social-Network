package app.controller.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class UserLoginDto {

	private static final long serialVersionUID = 156L;
	@Email
	@NotEmpty(message = "email required.")
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

	@Override
	public String toString() {
		return "UserLoginDto [email=" + email + ", pass=" + pass + "]";
	}

}
