package org.ksplus.base.foundation_types.time;

class DateFormatException extends IllegalArgumentException {

    DateFormatException(String value) {
        super("Value does not look like a valid date: " + value);
    }

    DateFormatException(String value, Throwable cause) {
        super("Value does not look like a valid date: " + value, cause);
    }
}
