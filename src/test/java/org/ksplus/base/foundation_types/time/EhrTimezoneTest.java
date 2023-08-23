package org.ksplus.base.foundation_types.time;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openehr.base.foundation_types.time.Iso8601Timezone;

class EhrTimezoneTest {

    @Nested
    @DisplayName("#hour")
    class Hour {

        @Test
        void should_get_zero_value_for_Z() {
            Iso8601Timezone timezone = new EhrTimezone("Z");

            assertThat(timezone.hour(), equalTo(0));
        }

        @Test
        void should_get_hour_if_hour_only_1() {
            Iso8601Timezone timezone = new EhrTimezone("+02");

            assertThat(timezone.hour(), equalTo(2));
        }

        @Test
        void should_get_hour_if_hour_only_2() {
            Iso8601Timezone timezone = new EhrTimezone("+14");

            assertThat(timezone.hour(), equalTo(14));
        }

        @Test
        void should_get_hour_from_complete_basic_timezone() {
            Iso8601Timezone timezone = new EhrTimezone("+0530");

            assertThat(timezone.hour(), equalTo(5));
        }

        @Test
        void should_get_hour_from_complete_extended_timezone() {
            Iso8601Timezone timezone = new EhrTimezone("+05:30");

            assertThat(timezone.hour(), equalTo(5));
        }
    }

    @Nested
    @DisplayName("#minute")
    class Minute {

        @Test
        void should_get_minute_for_basic_timezone_declaration() {
            Iso8601Timezone timezone = new EhrTimezone("+0530");

            assertThat(timezone.minute(), equalTo(30));
        }

        @Test
        void should_get_minute_for_extended_timezone_declaration() {
            Iso8601Timezone timezone = new EhrTimezone("+05:30");

            assertThat(timezone.minute(), equalTo(30));
        }

        @Test
        void should_get_zero_value_for_Z() {
            Iso8601Timezone timezone = new EhrTimezone("Z");

            assertThat(timezone.minute(), equalTo(0));
        }

        @Test
        void should_get_default_value_for_minute() {
            Iso8601Timezone timezone = new EhrTimezone("+14");

            assertThat(timezone.minute(), equalTo(0));
        }

        @Test
        void should_fail_get_minute() {
            Exception exception = assertThrows(
                IllegalArgumentException.class, () -> new EhrTimezone("+14:").minute()
            );
            assertThat(exception, instanceOf(IllegalArgumentException.class));
        }
    }

    @Nested
    @DisplayName("#sign")
    class Sign {

        @Test
        void should_get_negative_one_for_minus() {
            Iso8601Timezone timezone = new EhrTimezone("−04:00");

            assertThat(timezone.sign(), equalTo(-1));
        }

        @Test
        void should_get_negative_one_for_hyphen() {
            Iso8601Timezone timezone = new EhrTimezone("-04:00");

            assertThat(timezone.sign(), equalTo(-1));
        }

        @Test
        void should_get_positive_one_for_plus() {
            Iso8601Timezone timezone = new EhrTimezone("+01:00");

            assertThat(timezone.sign(), equalTo(1));
        }

        @Test
        void should_fail_get_sign() {
            Exception exception = assertThrows(
                IllegalArgumentException.class, () -> new EhrTimezone("_14").sign()
            );
            assertThat(exception, instanceOf(IllegalArgumentException.class));
        }
    }

    @Nested
    @DisplayName("#minuteUnknown")
    class MinuteUnknown {

        @Test
        void should_yield_minute_unknown() {
            Iso8601Timezone timezone = new EhrTimezone("+01");

            assertThat(timezone.minuteUnknown(), equalTo(true));
        }

        @Test
        void should_yield_minute_known_for_utc() {
            Iso8601Timezone timezone = new EhrTimezone("Z");

            assertThat(timezone.minuteUnknown(), equalTo(false));
        }

        @Test
        void should_yield_minute_known_for_extended_timezone_declaration() {
            Iso8601Timezone timezone = new EhrTimezone("+04:30");

            assertThat(timezone.minuteUnknown(), equalTo(false));
        }

        @Test
        void should_yield_minute_known_for_basic_timezone_declaration() {
            Iso8601Timezone timezone = new EhrTimezone("+0430");

            assertThat(timezone.minuteUnknown(), equalTo(false));
        }
    }

    @Nested
    @DisplayName("#isPartial")
    class IsPartial {

        @Test
        void should_yield_true_if_minute_missing() {
            Iso8601Timezone timezone = new EhrTimezone("+01");

            assertThat(timezone.isPartial(), equalTo(true));
        }

        @Test
        void should_yield_false_if_minute_present() {
            Iso8601Timezone timezone = new EhrTimezone("+04:30");

            assertThat(timezone.isPartial(), equalTo(false));
        }
    }

    @Nested
    @DisplayName("#asString")
    class AsString {

        @Test
        void should_format_as_string() {
            assertThat(new EhrTimezone("-03:00").asString(), equalTo("-03:00"));
            assertThat(new EhrTimezone("-0300").asString(), equalTo("-03:00"));
            assertThat(new EhrTimezone("-03").asString(), equalTo("-03:00"));
            assertThat(new EhrTimezone("+02:00").asString(), equalTo("+02:00"));
            assertThat(new EhrTimezone("+0200").asString(), equalTo("+02:00"));
            assertThat(new EhrTimezone("+02").asString(), equalTo("+02:00"));
        }
    }

    @Nested
    @DisplayName("#lessThan")
    class LessThan {

        @ParameterizedTest
        @CsvSource(value = {
            "timezone, other timezone",
            "-03:00, -02:00",
            "-03:00, -02   ",
            "-03   , -02:30",
            "-03:00, -02:00",
            "-03:00, +02:00",
            "+02:00, +02:30",
            "+02:00, +05",
            "-03:00, +02:00",
            "-03:00, +02   ",
        }, useHeadersInDisplayName = true)
        void should_yield_true_for_less_than(String timezoneValue, String otherTimezoneValue) {
            var timezone = new EhrTimezone(timezoneValue);
            var otherTimezone = new EhrTimezone(otherTimezoneValue);

            assertThat(timezone.lessThan(otherTimezone), equalTo(true));
        }

        @ParameterizedTest
        @CsvSource(value = {
            "timezone, other timezone",
            "-03:00, -03:00",
            "-03:00, -03   ",
            "-03   , -03:00",
            "+02:00, +02:00",
        }, useHeadersInDisplayName = true)
        void should_yield_false_for_equal(String timezoneValue, String otherTimezoneValue) {
            var timezone = new EhrTimezone(timezoneValue);
            var otherTimezone = new EhrTimezone(otherTimezoneValue);

            assertThat(timezone.lessThan(otherTimezone), equalTo(false));
        }

        @ParameterizedTest
        @CsvSource(value = {
            "timezone, other timezone",
            "-02:00, -03:00",
            "-02   , -03:00",
            "-02:30, -03   ",
            "-02:00, -03:00",
            "+02:00, -03:00",
            "+02:30, +02:00",
            "+05   , +02:00",
            "+02:00, -03:00",
            "+02   , -03:00",
        }, useHeadersInDisplayName = true)
        void should_yield_false_for_more_than(String timezoneValue, String otherTimezoneValue) {
            var timezone = new EhrTimezone(timezoneValue);
            var otherTimezone = new EhrTimezone(otherTimezoneValue);

            assertThat(timezone.lessThan(otherTimezone), equalTo(false));
        }
    }

    @Nested
    @DisplayName("#lessThanOrEqual")
    class LessThanOrEqual {

        @ParameterizedTest
        @CsvSource(value = {
            "timezone, other timezone",
            "-03:00, -02:00",
            "-03:00, -02   ",
            "-03   , -02:30",
            "-03:00, -02:00",
            "-03:00, +02:00",
            "+02:00, +02:30",
            "+02:00, +05",
            "-03:00, +02:00",
            "-03:00, +02   ",
        }, useHeadersInDisplayName = true)
        void should_yield_true_for_less_than(String timezoneValue, String otherTimezoneValue) {
            var timezone = new EhrTimezone(timezoneValue);
            var otherTimezone = new EhrTimezone(otherTimezoneValue);

            assertThat(timezone.lessThanOrEqual(otherTimezone), equalTo(true));
        }

        @ParameterizedTest
        @CsvSource(value = {
            "timezone, other timezone",
            "-03:00, -03:00",
            "-03:00, -03   ",
            "-03   , -03:00",
            "+02:00, +02:00",
        }, useHeadersInDisplayName = true)
        void should_yield_true_for_equal(String timezoneValue, String otherTimezoneValue) {
            var timezone = new EhrTimezone(timezoneValue);
            var otherTimezone = new EhrTimezone(otherTimezoneValue);

            assertThat(timezone.lessThanOrEqual(otherTimezone), equalTo(true));
        }

        @ParameterizedTest
        @CsvSource(value = {
            "timezone, other timezone",
            "-02:00, -03:00",
            "-02   , -03:00",
            "-02:30, -03   ",
            "-02:00, -03:00",
            "+02:00, -03:00",
            "+02:30, +02:00",
            "+05   , +02:00",
            "+02:00, -03:00",
            "+02   , -03:00",
        }, useHeadersInDisplayName = true)
        void should_yield_false_for_more_than(String timezoneValue, String otherTimezoneValue) {
            var timezone = new EhrTimezone(timezoneValue);
            var otherTimezone = new EhrTimezone(otherTimezoneValue);

            assertThat(timezone.lessThan(otherTimezone), equalTo(false));
        }
    }

    @Nested
    @DisplayName("#greaterThan")
    class GreaterThan {

        @ParameterizedTest
        @CsvSource(value = {
            "timezone, other timezone",
            "-03:00, -02:00",
            "-03:00, -02   ",
            "-03   , -02:30",
            "-03:00, -02:00",
            "-03:00, +02:00",
            "+02:00, +02:30",
            "+02:00, +05",
            "-03:00, +02:00",
            "-03:00, +02   ",
        }, useHeadersInDisplayName = true)
        void should_yield_false_for_less_than(String timezoneValue, String otherTimezoneValue) {
            var timezone = new EhrTimezone(timezoneValue);
            var otherTimezone = new EhrTimezone(otherTimezoneValue);

            assertThat(timezone.greaterThan(otherTimezone), equalTo(false));
        }

        @ParameterizedTest
        @CsvSource(value = {
            "timezone, other timezone",
            "-03:00, -03:00",
            "-03:00, -03   ",
            "-03   , -03:00",
            "+02:00, +02:00",
        }, useHeadersInDisplayName = true)
        void should_yield_false_for_equal(String timezoneValue, String otherTimezoneValue) {
            var timezone = new EhrTimezone(timezoneValue);
            var otherTimezone = new EhrTimezone(otherTimezoneValue);

            assertThat(timezone.greaterThan(otherTimezone), equalTo(false));
        }

        @ParameterizedTest
        @CsvSource(value = {
            "timezone, other timezone",
            "-02:00, -03:00",
            "-02   , -03:00",
            "-02:30, -03   ",
            "-02:00, -03:00",
            "+02:00, -03:00",
            "+02:30, +02:00",
            "+05   , +02:00",
            "+02:00, -03:00",
            "+02   , -03:00",
        }, useHeadersInDisplayName = true)
        void should_yield_true_for_more_than(String timezoneValue, String otherTimezoneValue) {
            var timezone = new EhrTimezone(timezoneValue);
            var otherTimezone = new EhrTimezone(otherTimezoneValue);

            assertThat(timezone.greaterThan(otherTimezone), equalTo(true));
        }
    }

    @Nested
    @DisplayName("#greaterThanOrEqual")
    class GreaterThanOrEqual {

        @ParameterizedTest
        @CsvSource(value = {
            "timezone, other timezone",
            "-03:00, -02:00",
            "-03:00, -02   ",
            "-03   , -02:30",
            "-03:00, -02:00",
            "-03:00, +02:00",
            "+02:00, +02:30",
            "+02:00, +05",
            "-03:00, +02:00",
            "-03:00, +02   ",
        }, useHeadersInDisplayName = true)
        void should_yield_false_for_less_than(String timezoneValue, String otherTimezoneValue) {
            var timezone = new EhrTimezone(timezoneValue);
            var otherTimezone = new EhrTimezone(otherTimezoneValue);

            assertThat(timezone.greaterThanOrEqual(otherTimezone), equalTo(false));
        }

        @ParameterizedTest
        @CsvSource(value = {
            "timezone, other timezone",
            "-03:00, -03:00",
            "-03:00, -03   ",
            "-03   , -03:00",
            "+02:00, +02:00",
        }, useHeadersInDisplayName = true)
        void should_yield_true_for_equal(String timezoneValue, String otherTimezoneValue) {
            var timezone = new EhrTimezone(timezoneValue);
            var otherTimezone = new EhrTimezone(otherTimezoneValue);

            assertThat(timezone.greaterThanOrEqual(otherTimezone), equalTo(true));
        }

        @ParameterizedTest
        @CsvSource(value = {
            "timezone, other timezone",
            "-02:00, -03:00",
            "-02   , -03:00",
            "-02:30, -03   ",
            "-02:00, -03:00",
            "+02:00, -03:00",
            "+02:30, +02:00",
            "+05   , +02:00",
            "+02:00, -03:00",
            "+02   , -03:00",
        }, useHeadersInDisplayName = true)
        void should_yield_true_for_more_than(String timezoneValue, String otherTimezoneValue) {
            var timezone = new EhrTimezone(timezoneValue);
            var otherTimezone = new EhrTimezone(otherTimezoneValue);

            assertThat(timezone.greaterThanOrEqual(otherTimezone), equalTo(true));
        }
    }
}
