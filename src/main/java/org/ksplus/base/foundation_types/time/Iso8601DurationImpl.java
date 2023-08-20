package org.ksplus.base.foundation_types.time;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openehr.base.foundation_types.primitive_types.Real;
import org.openehr.base.foundation_types.time.Iso8601Duration;

import jakarta.annotation.Nonnull;

public class Iso8601DurationImpl extends TimeDefinitionsImpl implements Iso8601Duration {

    @Nonnull
    private final String value;

    public Iso8601DurationImpl(@Nonnull String value) {
        this.value = value;
    }

    @Override
    public Boolean isExtended() {
        return null;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Boolean isPartial() {
        return null;
    }

    @Override
    public Integer years() {
        // TODO Create constant
        Pattern YEAR_PATTERN = Pattern.compile("\\p{Upper}(\\p{Digit}+)Y");
        Matcher m = YEAR_PATTERN.matcher(value);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        } else {
            return 0;
        }
    }

    @Override
    public Integer months() {
        return 0;
    }

    @Override
    public Integer days() {
        return 0;
    }

    @Override
    public Integer hours() {
        return 0;
    }

    @Override
    public Integer minutes() {
        return 0;
    }

    @Override
    public Integer seconds() {
        return 0;
    }

    @Override
    public Double fractionalSeconds() {
        return null;
    }

    @Override
    public Integer weeks() {
        return 0;
    }

    @Override
    public Boolean isDecimalSignComma() {
        return null;
    }

    @Override
    public Real toSeconds() {
        return null;
    }

    @Override
    public String asString() {
        return null;
    }

    @Override
    public Iso8601Duration add(Object o) {
        return null;
    }

    @Override
    public Iso8601Duration subtract(Object o) {
        return null;
    }

    @Override
    public Iso8601Duration multiply(Object o) {
        return null;
    }

    @Override
    public Iso8601Duration divide(Object o) {
        return null;
    }

    @Override
    public Iso8601Duration negative() {
        return null;
    }

    @Override
    public Boolean lessThan(Object o) {
        return null;
    }

    @Override
    public Boolean lessThanOrEqual(Object o) {
        return null;
    }

    @Override
    public Boolean greaterThan(Object o) {
        return null;
    }

    @Override
    public Boolean greaterThanOrEqual(Object o) {
        return null;
    }
}
