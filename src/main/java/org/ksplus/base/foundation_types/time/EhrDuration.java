package org.ksplus.base.foundation_types.time;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openehr.base.foundation_types.primitive_types.Real;
import org.openehr.base.foundation_types.time.Iso8601Duration;

import jakarta.annotation.Nonnull;

public class EhrDuration extends EhrTimeDefinitions implements Iso8601Duration {

    @Nonnull
    private final String value;

    public EhrDuration(@Nonnull String value) {
        this.value = value;
    }

    @Override
    public Boolean isExtended() {
        return null;
    }

    @Nonnull
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
        // TODO Create constant
        Pattern MONTHS_PATTERN = Pattern.compile("(\\d+)M.*T?");
        Matcher m = MONTHS_PATTERN.matcher(value);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        } else {
            return 0;
        }
    }

    @Override
    public Integer days() {
        Pattern DAYS_PATTERN = Pattern.compile("(\\p{Digit}+)D");
        Matcher m = DAYS_PATTERN.matcher(value);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        } else {
            return 0;
        }
    }

    @Override
    public Integer hours() {
        Pattern HOURS_PATTERN = Pattern.compile("T.*?(\\d+)H");
        Matcher m = HOURS_PATTERN.matcher(value);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        } else {
            return 0;
        }
    }

    @Override
    public Integer minutes() {
        Pattern MINUTES_PATTERN = Pattern.compile("T.*?(\\d+)M");
        Matcher m = MINUTES_PATTERN.matcher(value);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        } else {
            return 0;
        }
    }

    @Override
    public Integer seconds() {
        Pattern SECONDS_PATTERN = Pattern.compile("T.*?([\\d.,]+)S");
        Matcher m = SECONDS_PATTERN.matcher(value);
        if (m.find()) {
            return Double.valueOf(
                Math.floor(
                    Double.parseDouble(
                        m.group(1).replace(",", ".")
                    )
                )
            ).intValue();
        } else {
            return 0;
        }
    }

    @Override
    public Double fractionalSeconds() {
        Pattern SECONDS_PATTERN = Pattern.compile("T.*?([\\d,.]+)S");
        Matcher m = SECONDS_PATTERN.matcher(value);
        if (m.find()) {
            double seconds = Double.parseDouble(m.group(1).replace(",", "."));
            return seconds - Math.floor(seconds);
        } else {
            return 0.0;
        }
    }

    @Override
    public Integer weeks() {
        Pattern WEEKS_PATTERN = Pattern.compile("(\\d+)W");
        Matcher m = WEEKS_PATTERN.matcher(value);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        } else {
            return 0;
        }
    }

    @Override
    public Boolean isDecimalSignComma() {
        return value.contains(",");
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
