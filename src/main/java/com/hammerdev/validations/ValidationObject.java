package com.hammerdev.validations;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationObject
{

	public final Map<String, ValidationField> fieldsValidation = new HashMap<>();

	public ValidationField string(String fieldName)
	{
		ValidationField fieldValidation = ValidationField.string();
		fieldsValidation.put(fieldName, fieldValidation);

		return fieldsValidation.get(fieldName);
	}

	public ValidationField number(String fieldName)
	{
		ValidationField fieldValidation = ValidationField.number();
		fieldsValidation.put(fieldName, fieldValidation);

		return fieldsValidation.get(fieldName);
	}

	public ValidationField reference(String field, String refField)
	{
		fieldsValidation.put(refField, new ValidationField(fieldsValidation.get(refField)));
		return fieldsValidation.get(field);
	}

	public void validateException(Object object) throws IllegalArgumentException, IllegalAccessException, ValidationFieldErroException
	{
		for (Field field : object.getClass().getDeclaredFields()) {
			if (!field.canAccess(object)) field.setAccessible(true);
			try {
				Object value = field.get(object);
				if (fieldsValidation.get(field.getName()) != null) {
					fieldsValidation.get(field.getName()).validateException(value);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw e;
			} catch (ValidationErrorException e) {
				throw new ValidationFieldErroException(field.getName(), e.getMessage().replace("${FIELD_NAME}", field.getName()), e.getValidationType(), e.getFieldValidation());
			}
		}
	}

	public List<ValidationFieldErroException> validateReturn(Object object)
	{
		List<ValidationFieldErroException> theErrors = new ArrayList<>();

		for (Field field : object.getClass().getDeclaredFields()) {
			if (!field.canAccess(object)) field.setAccessible(true);
			try {
				Object value = field.get(object);
				if (fieldsValidation.get(field.getName()) != null) {
					fieldsValidation.get(field.getName()).validateException(value);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			} catch (ValidationErrorException e) {
				theErrors.add(new ValidationFieldErroException(field.getName(), e.getMessage().replace("${FIELD_NAME}", field.getName()), e.getValidationType(), e.getFieldValidation()));
			}	
		}

		return theErrors;
	}
}
