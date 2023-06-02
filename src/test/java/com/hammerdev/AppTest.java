package com.hammerdev;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.hammerdev.validations.ValidationErrorException;
import com.hammerdev.validations.ValidationField;
import com.hammerdev.validations.ValidationFieldErroException;
import com.hammerdev.validations.ValidationObject;
import com.hammerdev.validations.ValidationType;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Test
    public void validationFieldWithBoolean()
    {
        String tooBar = "";
        ValidationField field = ValidationField.string().nonBlank("TooBar is blank").validate(tooBar);
        assertTrue(field.canError(ValidationType.NONBLANK));
    }

    @Test
    public void validationFieldWithException()
    {
        String tooBar = "";
        try {
            ValidationField field = ValidationField.string().nonBlank("TooBar is blank").validateException(tooBar);
        } catch (ValidationErrorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void validationObjectMultiplesFields()
    {
        Object object;

        ValidationObject validation = new ValidationObject();
        validation.string("{PropertyName}").nonBlank("{PropertyName} is null!");

        validation.validateReturn(object);
        try {
            validation.validateException(object);
        } catch (IllegalArgumentException | IllegalAccessException | ValidationFieldErroException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
