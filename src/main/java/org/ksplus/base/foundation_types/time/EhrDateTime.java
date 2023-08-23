package org.ksplus.base.foundation_types.time;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openehr.base.foundation_types.time.Iso8601Date;
import org.openehr.base.foundation_types.time.Iso8601DateTime;
import org.openehr.base.foundation_types.time.Iso8601Duration;
import org.openehr.base.foundation_types.time.Iso8601Timezone;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.NonNull;

public class EhrDateTime extends EhrTimeDefinitions implements Iso8601DateTime {

    private static final String MINUS = "−";

    private static final String HYPHEN = "-";

    private static final Pattern CHARACTERS_ALLOWED_BY_ISO_8601 = Pattern.compile("["+ HYPHEN + "0-9:TWZ?.,+ " + MINUS + "]+");

    @Nonnull
    private final String value;

    @Nonnull
    private final String dateValue;

    @Nullable
    private final String timeValue;

    public EhrDateTime(@NonNull String value) {
        checkThatOnlyCharactersDefinedByIso8601AreUsed(value);

        this.value = value;

        dateValue = parseDateValue(value);
        timeValue = parseTimeValue(value);

        checkIso8601SemanticsNotIncludedInOpenEhr();
        checkIso8601DeviationsInOpenEhr();
    }

    private static void checkThatOnlyCharactersDefinedByIso8601AreUsed(String value) {
        Matcher m = CHARACTERS_ALLOWED_BY_ISO_8601.matcher(value);
        if (!m.matches()) {
            throw new IllegalArgumentException("It looks like the date time value contains characters not allowed by" +
                " ISO 8601: " + value);
        }
    }

    private void checkIso8601SemanticsNotIncludedInOpenEhr() {
        if (dateValue.startsWith("+") || dateValue.startsWith("-")) {
            throw new IllegalArgumentException("Invalid date/time value, because only positive 4-digit year numbers " +
                "are assumed by openEHR: " + value);
        }
        if (dateValue.contains("W")) {
            throw new IllegalArgumentException("Invalid date/time value, because week-based dates are not supported " +
                "by openEHR: " + value);
        }

        // TODO: reject partial date/times with fractional minutes or hours

        // TODO: reject the interval syntax
    }

    private void checkIso8601DeviationsInOpenEhr() {

    }

    private static String parseDateValue(String value) {
        String[] parts = value.split("T");
        String datePart = parts[0];
        boolean dateValueHasNegativeSign = datePart.startsWith("-");
        String basicDate = datePart.replaceAll("-", "");
        if (dateValueHasNegativeSign) {
            basicDate = "-" + basicDate;
        }
        return basicDate;
    }

    private static String parseTimeValue(String value) {
        String[] parts = value.split("T");
        if (parts.length == 2) {
            String result = parts[1];
            return result.replaceAll(":", "");
        } else {
            return null;
        }
    }

    @Override
    public Integer year() {
        if (dateValue.length() < 4) {
            throw new IllegalArgumentException("Cannot read year from: " + value);
        }
        return Integer.valueOf(dateValue.substring(0, 4));
    }

    @Override
    public Integer month() {
        if (dateValue.length() >= 6) {
            String monthAsString = dateValue.substring(4, 6);
            if ("??".equals(monthAsString)) {
                return 0;
            } else {
                return Integer.valueOf(monthAsString);
            }
        } else {
            return 0;
        }
    }

    @Override
    public Integer day() {
        int len = dateValue.length();
        if (len > 6) {
            if (len != 8) {
                throw new IllegalArgumentException("Doesn't look like a valid ISO 8601 date declaration: " + value);
            }
            return Integer.valueOf(dateValue.substring(6, 8));
        } else {
            return 0;
        }
    }

    @Override
    public Integer hour() {
        if (timeValue == null) {
            return 0;
        }
        if (timeValue.length() >= 2) {
            String hourAsString = timeValue.substring(0, 2);
            if ("??".equals(hourAsString)) {
                return 0;
            }
            return Integer.valueOf(hourAsString);
        } else {
            return 0;
        }
    }

    @Override
    public Integer minute() {
        if (timeValue == null) {
            return 0;
        }
        if (timeValue.length() >= 4) {
            String minuteAsString = timeValue.substring(2, 4);
            if ("??".equals(minuteAsString)) {
                return 0;
            }
            return Integer.valueOf(minuteAsString);
        } else {
            return 0;
        }
    }

    @Override
    public Integer second() {
        if (timeValue == null) {
            return 0;
        }
        if (timeValue.length() >= 6) {
            String secondAsString = timeValue.substring(4, 6);
            if ("??".equals(secondAsString)) {
                return 0;
            }
            return Integer.valueOf(secondAsString);
        } else {
            return 0;
        }
    }

    @Override
    public Double fractionalSecond() {
        if (timeValue == null) {
            return 0.0;
        }
        if (timeValue.length() > 7) {
            Matcher m = Pattern.compile(".*[,.](.*)").matcher(timeValue);
            if (m.matches()) {
                String fractionalSecondAsString = "0." + m.group(1);
                return Double.valueOf(fractionalSecondAsString);
            }
        }
        return 0.0;
    }

    @Override
    public @Nullable Iso8601Timezone timezone() {
        return null;
    }

    @Override
    public Boolean monthUnknown() {
        return null;
    }

    @Override
    public Boolean dayUnknown() {
        return null;
    }

    @Override
    public Boolean minuteUnknown() {
        return null;
    }

    @Override
    public Boolean secondUnknown() {
        return null;
    }

    @Override
    public Boolean isDecimalSignComma() {
        return null;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public Boolean isPartial() {
        return null;
    }

    @Override
    public Boolean isExtended() {
        return null;
    }

    @Override
    public Boolean hasFractionalSecond() {
        return null;
    }

    @Override
    public String asString() {
        return null;
    }

    @Override
    public Iso8601DateTime add(Object aDiff) {
        return null;
    }

    @Override
    public Iso8601DateTime subtract(Object aDiff) {
        return null;
    }

    @Override
    public Iso8601Duration diff(Object aDateTime) {
        return null;
    }

    @Override
    public Iso8601Date addNominal(Object aDiff) {
        return null;
    }

    @Override
    public Iso8601Date subtractNominal(Object aDiff) {
        return null;
    }

    @Override
    public Boolean lessThan(Object other) {
        return null;
    }

    @Override
    public Boolean lessThanOrEqual(Object other) {
        return null;
    }

    @Override
    public Boolean greaterThan(Object other) {
        return null;
    }

    @Override
    public Boolean greaterThanOrEqual(Object other) {
        return null;
    }
}
