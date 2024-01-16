package com.demo.project.validation;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface ValidationUtils {

	String NUMERIC_REGEX = "\\d+";
	String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9]*$";
	String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
			+ "A-Z]{2,7}$";
	String NAME_REGEX = "^[A-Z]{1}[a-z]+[]{1}[A-Z]{1}[a-z]+$";

	String PASSWORD_REGEX = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";

	public static boolean isEmpty(String value) {
		return Objects.isNull(value) || value.trim().length() == 0;

	}

	public static boolean isNumeric(String data) {
		return !isEmpty(data) && data.matches(NUMERIC_REGEX);
	}

	public static boolean isAlphaNumeric(String data) {
		return !isEmpty(data) ? false : data.matches(ALPHANUMERIC_REGEX);
	}

	public static boolean isEmailValid(String email) {
		Pattern pat = Pattern.compile(EMAIL_REGEX);
		return pat.matcher(email).matches();
	}

	public static boolean isNameValid(String name) {
		return !isEmpty(name) ? false : name.matches(NAME_REGEX);

	}

	/*
	 * It contains at least 8 characters and at most 20 characters. It contains at
	 * least one digit. It contains at least one upper case alphabet. It contains at
	 * least one lower case alphabet. It contains at least one special character
	 * which includes !@#$%&*()-+=^. It doesn’t contain any white space.
	 */

	public static boolean isValidPassword(String password) {
		Pattern p = Pattern.compile(PASSWORD_REGEX);

		Matcher m = p.matcher(password);

		return m.matches();
	}

}
