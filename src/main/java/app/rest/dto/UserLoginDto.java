package app.rest.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

public class UserLoginDto implements Serializable {

	private static final long serialVersionUID = 156L;

	@NotBlank(message = "User name required.")
	String userName;

	@NotBlank(message = "Password required.")
	String pass;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Override
	public String toString() {
		return "UserLoginDto [userName=" + userName + ", pass=" + pass + "]";
	}

}
