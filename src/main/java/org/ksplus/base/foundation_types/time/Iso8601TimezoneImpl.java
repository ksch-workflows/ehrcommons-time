package org.ksplus.base.foundation_types.time;

import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.zone.ZoneRulesException;
import java.util.TimeZone;

import org.openehr.base.foundation_types.time.Iso8601Timezone;

import lombok.NonNull;

public class Iso8601TimezoneImpl extends TimeDefinitionsImpl implements Iso8601Timezone  {

    private static final String MINUS = "−";

    private static final String HYPHEN = "-";

    private final String value;

    public Iso8601TimezoneImpl(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Timezone value must not be null");
        }
        String normalizedValue = value;
        if ("Z".equals(value)) {
            normalizedValue = "+00:00";
        }

        checkNoZeroOffsetWithNegativeSign(normalizedValue);

        // TODO Add some comments and consider other exception type
        try {
            ZoneId.of(value);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(e);
        }

        this.value = normalizedValue;
    }

    private static void checkNoZeroOffsetWithNegativeSign(String value) {
        if ("-00".equals(value) || "-00:00".equals(value) || "-0000".equals(value)) {
            throw new IllegalArgumentException("Negative sign not allowed with zero offset: " + value);
        }
    }

    @Override
    public Integer hour() {
        return Integer.valueOf(value.substring(1, 3));
    }

    @Override
    public Integer minute() {
        return switch (value.length()) {
            case 3 -> 0;
            case 5 -> Integer.valueOf(value.substring(3, 5));
            case 6 -> Integer.valueOf(value.substring(4, 6));
            default -> throw new IllegalArgumentException("Cannot parse minute from timezone value: " + value);
        };
    }

    @Override
    public Integer sign() {
        return switch (value.substring(0, 1)) {
            case MINUS, HYPHEN -> -1;
            case "+" -> +1;
            default -> throw new IllegalArgumentException("Cannot parse sign from timezone value: " + value);
        };
    }

    @Override
    public Boolean minuteUnknown() {
        return value.length() == 3;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Boolean isPartial() {
        return minuteUnknown();
    }

    @Override
    public Boolean isExtended() {
        return value.contains(":");
    }

    // TODO "GMT is no longer precisely defined by the scientific community and can refer to either UTC or [UT1](https://en.wikipedia.org/wiki/UT1 "UT1") depending on context.[[31]](https://en.wikipedia.org/wiki/ISO_8601#cite_note-31)"
    @Override
    public Boolean isGmt() {
        return value.equals("+00:00") || value.equals("+0000") || value.equals("+00");
    }

    @Override
    public String asString() {
        TimeZone.getTimeZone(ZoneId.systemDefault());

        String result = value;
        if (result.length() == 3) {
            result += ":00";
        } else if (result.length() == 5) {
            result = result.substring(0, 3) + ":" + result.substring(3);
        }
        return result;
    }

    @Override
    public Boolean lessThan(@NonNull Object other) {
        if (other instanceof Iso8601Timezone otherTimezone) {
            return comparisonValue(this) < comparisonValue(otherTimezone);
        } else {
            throw new ComparisonTypeMismatchException(other.getClass());
        }
    }

    @Override
    public Boolean lessThanOrEqual(Object other) {
        if (other instanceof Iso8601Timezone otherTimezone) {
            return comparisonValue(this) <= comparisonValue(otherTimezone);
        } else {
            throw new ComparisonTypeMismatchException(other.getClass());
        }
    }

    @Override
    public Boolean greaterThan(Object other) {
        if (other instanceof Iso8601Timezone otherTimezone) {
            return comparisonValue(this) > comparisonValue(otherTimezone);
        } else {
            throw new ComparisonTypeMismatchException(other.getClass());
        }
    }

    @Override
    public Boolean greaterThanOrEqual(Object other) {
        if (other instanceof Iso8601Timezone otherTimezone) {
            return comparisonValue(this) >= comparisonValue(otherTimezone);
        } else {
            throw new ComparisonTypeMismatchException(other.getClass());
        }
    }

    private static int comparisonValue(Iso8601Timezone timezone) {
        return (timezone.hour() * 60 + timezone.minute()) * timezone.sign();
    }

    private static class ComparisonTypeMismatchException extends RuntimeException {
        ComparisonTypeMismatchException(Class<?> other) {
            super("Cannot compare timezone with object of type: " + other.getName());
        }
    }
}
