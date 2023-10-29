package ru.mnk.core.service.validator;

import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import ru.mnk.core.exceptions.ValidationException;

public abstract class CommonStrictValidator implements Validator {
    public void validate(Object object) throws ValidationException {
        if(!supports(object.getClass())) {
            return;
        }

        final DataBinder dataBinder = new DataBinder(object);
        dataBinder.addValidators(this);
        dataBinder.validate();

        if (dataBinder.getBindingResult().hasErrors()) {
            throw new ValidationException(dataBinder.getBindingResult().getAllErrors().stream()
                    .map(ObjectError::toString)
                    .reduce("", (o1, o2) -> o1 + "\n" + o2));
        }
    }
}
