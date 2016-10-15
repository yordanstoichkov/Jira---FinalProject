package com.jira.model.employee;

import org.springframework.stereotype.Component;

public interface IValidator {

	public boolean stringValidator(String string);

	public boolean objectValidator(Object object);

	public boolean positiveNumberValidator(int number);
}
