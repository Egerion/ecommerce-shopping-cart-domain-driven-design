package com.tmall.domain.util;

import com.tmall.domain.model.ValidationResult;

public abstract class ValidationStep<T> {

    private ValidationStep<T> next;

    public ValidationStep<T> addConstraint(ValidationStep<T> nextStep) {
        if (this.next == null) {
            this.next = nextStep;
            return this;
        }
        ValidationStep<T> last = this.next;
        while (last.next != null) {
            last = last.next;
        }
        last.next = nextStep;
        return this;
    }

    public abstract ValidationResult validate(T toValidate);

    protected ValidationResult checkNext(T toValidate) {
        if (this.next == null) {
            return ValidationResult.valid();
        }
        return this.next.validate(toValidate);
    }
}
