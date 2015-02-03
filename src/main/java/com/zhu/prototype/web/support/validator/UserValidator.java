package com.zhu.prototype.web.support.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.zhu.prototype.dto.UserDTO;
import com.zhu.prototype.entity.User;

@Component
public class UserValidator implements Validator {

	private final int MINIMUM_PASSWORD_LENGTH = 4;
	private final int[] USERNAME_RANGE = { 4, 10 };

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(User.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username",
				"field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password2",
				"field.required");

		UserDTO user = (UserDTO) target;

		int usernameLength = 0;
		if (StringUtils.isNotBlank(user.getUsername())
				&& ((usernameLength = user.getUsername().trim().length()) < USERNAME_RANGE[0] || usernameLength > USERNAME_RANGE[1])) {
			errors.rejectValue("username", "field.length", new Object[] {
					USERNAME_RANGE[0], USERNAME_RANGE[1] },
					"The username must between " + USERNAME_RANGE[0] + " and "
							+ USERNAME_RANGE[1]);
		}
		if (StringUtils.isNotBlank(user.getPassword())
				&& user.getPassword().trim().length() < MINIMUM_PASSWORD_LENGTH) {
			errors.rejectValue("password", "field.min.length",
					new Object[] { Integer.valueOf(MINIMUM_PASSWORD_LENGTH) },
					"The password must be at least [" + MINIMUM_PASSWORD_LENGTH
							+ "] characters in length.");
		}
		if (user.getPassword() != null
				&& !user.getPassword().equals(user.getPassword2())) {
			errors.rejectValue("password2", "field.not.match");
		}
		Double height = user.getHeight();
		if (height != null && (height >= 500 || height <= 0)) {
			errors.rejectValue("height", "field.not.illegal");
		}
	}
}
