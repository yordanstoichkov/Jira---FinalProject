package com.jira.model.employee;

import org.springframework.stereotype.Component;

@Component
public class Validator implements IValidator {

	// Validate string
	@Override
	public boolean stringValidator(String string) {
		return (string != null && string.trim().length() > 0);
	}

	// Validate object
	@Override
	public boolean objectValidator(Object object) {
		return object != null;
	}

	// Used to validate id of users, projects, issues etc
	@Override
	public boolean positiveNumberValidator(int number) {
		return number > 0;
	}
}
