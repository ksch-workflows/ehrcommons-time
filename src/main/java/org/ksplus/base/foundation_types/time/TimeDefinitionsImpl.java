package org.ksplus.base.foundation_types.time;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openehr.base.foundation_types.time.TimeDefinitions;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @see <a href="https://learning.oreilly.com/library/view/core-java-vol/9780137870899/ch06.xhtml#sec6_6">Core Java, Vol. II-Advanced Features, 12th Edition</a>
 */
@NoArgsConstructor
@SuppressWarnings("RedundantIfStatement")
public class TimeDefinitionsImpl implements TimeDefinitions {

    private static final Pattern YYYY_MM_DD_EXTENDED = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
    private static final Pattern YYYY_MM_DD_BASIC = Pattern.compile("[0-9]{4}[0-9]{2}[0-9]{2}");
    private static final Pattern YYYY_MM_EXTENDED = Pattern.compile("[0-9]{4}-[0-9]{2}");
    private static final Pattern YYYY_MM_BASIC = Pattern.compile("[0-9]{4}[0-9]{2}");
    public static final Pattern YYYY_DDD = Pattern.compile("[0-9]{4}-?([0-9]{3})");
    public static final Pattern YYYY = Pattern.compile("[0-9]{4}");

    @Override
    public Boolean validYear(@NonNull Integer y) {
        return y >= 0;
    }

    @Override
    public Boolean validMonth(@NonNull Integer m) {
        return m >= 1 && m <= MONTHS_IN_YEAR;
    }

    @Override
    public Boolean validDay(@NonNull Integer y, @NonNull Integer m, @NonNull Integer d) {
        if (!validYear(y)) {
            return false;
        }
        try {
            //noinspection ResultOfMethodCallIgnored
            LocalDate.of(y, m, d);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    @Override
    public Boolean validHour(@NonNull Integer h, @NonNull Integer m, @NonNull Integer s) {
        if (h >= 0 && h < HOURS_IN_DAY && s < SECONDS_IN_MINUTE) {
            return true;
        }
        if (h.equals(HOURS_IN_DAY) && m == 0 && s == 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean validMinute(@NonNull Integer m) {
        return m >= 0 && m < MINUTES_IN_HOUR;
    }

    @Override
    public Boolean validSecond(@NonNull Integer s) {
        return s >= 0 && s < SECONDS_IN_MINUTE;
    }

    @Override
    public Boolean validFractionalSecond(@NonNull Double fs) {
        return fs >= 0.0 && fs < 1;
    }

    @Override
    public Boolean validIso8601Date(@NonNull String s) {
        Matcher m;
        if (YYYY_MM_DD_EXTENDED.matcher(s).matches()) {
            return true;
        }
        if (YYYY_MM_DD_BASIC.matcher(s).matches()) {
            return true;
        }
        if (YYYY_MM_EXTENDED.matcher(s).matches()) {
            return true;
        }
        if (YYYY_MM_BASIC.matcher(s).matches()) {
            return true;
        }
        m = YYYY_DDD.matcher(s);
        if (m.matches()) {
            try {
                int dayOfYear = Integer.parseInt(m.group(1));
                if (dayOfYear < 1 || dayOfYear > 365) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }
        if (YYYY.matcher(s).matches()) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean validIso8601Time(@NonNull String s) {
        record TimeParts(
            @Nonnull
            String localTime,
            @Nullable
            String timezone
        ){}

        TimeParts timeParts;
        Pattern pattern = Pattern.compile("(?<localTime>.*)(?<timezone>[Z+-].*)");
        Matcher m = pattern.matcher(s);
        if (m.matches()) {
            String time = m.group("localTime");
            String formattedTime = new FormattedLocalTime(time).getValueWithUnknownPartsAsZero();
            timeParts = new TimeParts(formattedTime, m.group("timezone"));
        } else {
            String formattedTime = new FormattedLocalTime(s).getValueWithUnknownPartsAsZero();
            timeParts = new TimeParts(formattedTime, null);
        }

        if (timeParts.timezone != null) {
            try {
                //noinspection ResultOfMethodCallIgnored
                ZoneId.of(timeParts.timezone);
            } catch (Exception e) {
                return false;
            }
        }

        try {
            //noinspection ResultOfMethodCallIgnored
            LocalTime.parse(timeParts.localTime);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public Boolean validIso8601DateTime(@NonNull String s) {
        var parts = s.split("T");
        if (!validIso8601Date(parts[0])) {
            return false;
        }
        if (parts.length == 2) {
            if (!validIso8601Time(parts[1])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean validIso8601Duration(@NonNull String s) {
        return null;
    }
}
