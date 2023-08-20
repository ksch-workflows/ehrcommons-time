package org.ksplus.base.foundation_types.time;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import org.openehr.base.foundation_types.time.Iso8601Date;
import org.openehr.base.foundation_types.time.Iso8601Duration;
import org.openehr.base.foundation_types.time.Iso8601Timezone;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class Iso8601DateImpl implements Iso8601Date {

    /**
     * e.g. 2003-02-20
     */
    private static final int YYYY_DASH_MM_DASH_DD = 10;

    /**
     * e.g. 2003-02
     */
    private static final int YYYY_DASH_MM = 7;

    /**
     * e.g. 2003
     */
    private static final int YYYY = 4;

    /**
     * e.g. 20030220
     */
    private static final int YYYY_MM_DD = 8;

    /**
     * e.g. 200302
     */
    private static final int YYYY_MM = 6;

    @Nonnull
    private final String value;

    @Nonnull
    private final Integer year;

    @Nullable
    private final Integer month;

    @Nullable
    private final Integer day;

    @Nullable
    private final Iso8601Timezone timezone;

    public Iso8601DateImpl(@Nonnull String value) {
        this.value = value;

        var parts = value.split("T");
        var datePart = parts[0];
        if (parts.length == 2) {
            var timezonePart = parts[1];
            timezone = new Iso8601TimezoneImpl(timezonePart);
            System.out.println();
        } else {
            timezone = null;
        }

        switch (datePart.length()) {
            case YYYY -> {
                if (!Pattern.matches("\\d{4}", datePart)) {
                    throw new DateFormatException(value);
                }
                // TODO Use regex match groups
                this.year = Integer.valueOf(datePart);
                this.month = null;
                this.day = null;
            }
            // Extended format
            case YYYY_DASH_MM_DASH_DD -> {
                if (!Pattern.matches("\\d{4}-\\d{2}-\\d{2}", datePart)) {
                    throw new DateFormatException(value);
                }
                // TODO Use regex match groups
                this.year = Integer.valueOf(datePart.substring(0, 4));
                this.month = Integer.valueOf(datePart.substring(5, 7));
                this.day = Integer.valueOf(datePart.substring(8));
            }
            case YYYY_DASH_MM -> {
                if (!Pattern.matches("\\d{4}-\\d{2}", datePart)) {
                    throw new DateFormatException(value);
                }
                // TODO Use regex match groups
                this.year = Integer.valueOf(datePart.substring(0, 4));
                this.month = Integer.valueOf(datePart.substring(5, 7));
                this.day = null;
            }
            // Basic format
            case YYYY_MM_DD -> {
                if (!Pattern.matches("\\d{8}", datePart)) {
                    throw new DateFormatException(value);
                }
                // TODO Use regex match groups
                this.year = Integer.valueOf(datePart.substring(0, 4));
                this.month = Integer.valueOf(datePart.substring(4, 6));
                this.day = Integer.valueOf(datePart.substring(6));
            }
            case YYYY_MM -> {
                if (!Pattern.matches("\\d{6}", datePart)) {
                    throw new DateFormatException(value);
                }
                // TODO Use regex match groups
                this.year = Integer.valueOf(datePart.substring(0, 4));
                this.month = Integer.valueOf(datePart.substring(4, 6));
                this.day = null;
            }
            // Invalid format
            default -> throw new DateFormatException(value);
        }

        // Instantiating `LocalDate` to leverage the date validation of the JDK
        try {
            //noinspection ResultOfMethodCallIgnored
            LocalDate.of(
                year,
                month != null ? month : 1,
                day != null ? day : 1
            );
        } catch (DateTimeException e) {
            throw new DateFormatException(value, e);
        }
    }

    @Override
    public Integer year() {
        return year;
    }

    @Override
    public Integer month() {
        return month != null ? month : 0;
    }

    @Override
    public Integer day() {
        return day != null ? day : 0;
    }

    // Also see https://discourse.openehr.org/t/timezone-of-iso-8601-date/4353
    @Override
    public Iso8601Timezone timezone() {
        return timezone;
    }

    @Override
    public Boolean monthUnknown() {
        return month == null;
    }

    @Override
    public Boolean dayUnknown() {
        return day == null;
    }

    @Nonnull
    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Boolean isPartial() {
        return monthUnknown() || dayUnknown();
    }

    @Override
    public Boolean isExtended() {
        return value.contains("-");
    }

    @Override
    public String asString() {
        StringBuilder result = new StringBuilder();
        result.append(year);
        if (month != null) {
            result.append("-");
            result.append(String.format("%02d", month));
        }
        if (day != null) {
            result.append("-");
            result.append(String.format("%02d", day));
        }
        if (timezone != null) {
            result.append("T");
            result.append(timezone.asString());
        }
        return result.toString();
    }

    @Override
    public Iso8601Date add(Iso8601Duration aDiff) {
        return null;
    }

    @Override
    public Iso8601Date subtract(Iso8601Duration aDiff) {
        return null;
    }

    @Override
    public Iso8601Duration diff(Iso8601Date aDate) {
        return null;
    }

    @Override
    public Iso8601Date addNominal(Iso8601Duration aDiff) {
        return null;
    }

    @Override
    public Iso8601Date subtractNominal(Iso8601Duration aDiff) {
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

    // TODO Use implementation from abstract base class
    @Override
    public Boolean validYear(Integer integer) {
        return null;
    }

    @Override
    public Boolean validMonth(Integer integer) {
        return null;
    }

    // TODO Add PR in openEHR Jira to improve the variable names since they seem to be ambiguous.
    @Override
    public Boolean validDay(Integer integer, Integer integer1, Integer integer2) {
        return null;
    }

    // TODO Add PR in openEHR Jira to improve the variable names since they seem to be ambiguous.
    @Override
    public Boolean validHour(Integer integer, Integer integer1, Integer integer2) {
        return null;
    }

    @Override
    public Boolean validMinute(Integer integer) {
        return null;
    }

    @Override
    public Boolean validSecond(Integer integer) {
        return null;
    }

    @Override
    public Boolean validFractionalSecond(Double aDouble) {
        return null;
    }

    @Override
    public Boolean validIso8601Date(String s) {
        return null;
    }

    @Override
    public Boolean validIso8601Time(String s) {
        return null;
    }

    @Override
    public Boolean validIso8601DateTime(String s) {
        return null;
    }

    @Override
    public Boolean validIso8601Duration(String s) {
        return null;
    }
}
