package com.in28minutes.login;

public class LoginService {

	public boolean isUserValid(String user, String password) {
		if ("in28Minutes".equals(user) && "dummy".equals(password))
			return true;

		return false;
	}

}
