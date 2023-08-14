package org.ksplus.base.foundation_types.time;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.ksplus.base.foundation_types.time.Iso8601DateTimeImpl;
import org.openehr.base.foundation_types.time.Iso8601DateTime;

class Iso8601DateTimeTest {

    @Nested
    @DisplayName("Constructor")
    class Constructor {

        @Test
        void should_accept_negative_timezone_with_minus() {
            new Iso8601DateTimeImpl("2023-07-28T16:22:34−02:00");
            // test passes if no exception occurs
        }

        @Test
        void should_accept_negative_timezone_with_hyphen() {
            new Iso8601DateTimeImpl("2023-07-28T16:22:34-02:00");
            // test passes if no exception occurs
        }
    }

    @Nested
    @DisplayName("#checkIso8601SemanticsNotIncludedInOpenEhr")
    class CheckIso8601SemanticsNotIncludedInOpenEhr {

        @Test
        void should_reject_week_based_dates_1() {
            Exception exception = assertThrows(
                IllegalArgumentException.class, () -> new Iso8601DateTimeImpl("2023-W30")
            );

            assertThat(exception, instanceOf(IllegalArgumentException.class));
            assertThat(exception.getMessage(), containsString("openEHR"));
        }

        @Test
        void should_reject_week_based_dates_2() {
            Exception exception = assertThrows(
                IllegalArgumentException.class, () -> new Iso8601DateTimeImpl("2023-W30-3")
            );

            assertThat(exception, instanceOf(IllegalArgumentException.class));
            assertThat(exception.getMessage(), containsString("openEHR"));
        }

        @Test
        void should_reject_six_digit_year_1() {
            Exception exception = assertThrows(
                IllegalArgumentException.class, () -> new Iso8601DateTimeImpl("+002023-01-01")
            );

            assertThat(exception, instanceOf(IllegalArgumentException.class));
            assertThat(exception.getMessage(), containsString("openEHR"));
        }

        @Test
        void should_reject_six_digit_year_2() {
            Exception exception = assertThrows(
                IllegalArgumentException.class, () -> new Iso8601DateTimeImpl("-002023-01-01")
            );

            assertThat(exception, instanceOf(IllegalArgumentException.class));
            assertThat(exception.getMessage(), containsString("openEHR"));
        }
    }

    @Nested
    @DisplayName("Iso8601DateTime#year")
    class Year {

        @Test
        void should_return_year() {
            Iso8601DateTime date = new Iso8601DateTimeImpl("2023");

            assertThat(date.year(), equalTo(2023));
        }
    }

    @Nested
    @DisplayName("Iso8601DateTime#month")
    class Month {

        @Test
        void should_return_month_from_extended_time_format() {
            Iso8601DateTime date = new Iso8601DateTimeImpl("2023-07");

            assertThat(date.month(), equalTo(7));
        }

        @Test
        void should_return_month_from_basic_time_format() {
            Iso8601DateTime date = new Iso8601DateTimeImpl("202307");

            assertThat(date.month(), equalTo(7));
        }

        @Test
        void should_return_zero_for_unknown_month() {
            Iso8601DateTime date = new Iso8601DateTimeImpl("2023-??");

            assertThat(date.month(), equalTo(0));
        }

        @Test
        void should_return_zero_for_unknown_month_2() {
            Iso8601DateTime date = new Iso8601DateTimeImpl("2023-??-25");

            assertThat(date.month(), equalTo(0));
        }

        @Test
        void should_return_zero_for_unknown_month_3() {
            Iso8601DateTime date = new Iso8601DateTimeImpl("2023??");

            assertThat(date.month(), equalTo(0));
        }

        @Test
        void should_return_zero_for_unknown_month_4() {
            Iso8601DateTime date = new Iso8601DateTimeImpl("2023-??");

            assertThat(date.month(), equalTo(0));
        }

        @Test
        void should_return_zero_for_unspecified_month() {
            Iso8601DateTime date = new Iso8601DateTimeImpl("2023");

            assertThat(date.month(), equalTo(0));
        }
    }

    @Nested
    @DisplayName("Iso8601DateTime#hour")
    class Hour {

        @Test
        void should_get_hour_from_date_time() {
            Iso8601DateTime dateTime = new Iso8601DateTimeImpl("2023-01-01T15");

            assertThat(dateTime.hour(), equalTo(15));
        }

        @Test
        void should_get_default_value_for_unknown_hour() {
            Iso8601DateTime dateTime = new Iso8601DateTimeImpl("2023-01-01T??");

            assertThat(dateTime.hour(), equalTo(0));
        }

        @Test
        void should_get_default_value_for_not_specified_hour() {
            Iso8601DateTime dateTime = new Iso8601DateTimeImpl("2023-01-01");

            assertThat(dateTime.hour(), equalTo(0));
        }
    }

    @Nested
    @DisplayName("Iso8601DateTime#minute")
    class Minute {
        
        @Test
        void should_get_minute() {
            Iso8601DateTime dateTime = new Iso8601DateTimeImpl("2023-01-01T12:42:24");

            assertThat(dateTime.minute(), equalTo(42));
        }

        @Test
        void should_get_default_value_for_unknown_minute() {
            Iso8601DateTime dateTime = new Iso8601DateTimeImpl("2023-01-01T12:??:24");

            assertThat(dateTime.minute(), equalTo(0));
        }

        @Test
        void should_get_default_value_for_unspecified_minute() {
            Iso8601DateTime dateTime = new Iso8601DateTimeImpl("2023-01-01T12");

            assertThat(dateTime.minute(), equalTo(0));
        }
    }

    @Nested
    @DisplayName("#second")
    class Second {

        @Test
        void should_get_second() {
            Iso8601DateTime dateTime = new Iso8601DateTimeImpl("2023-01-01T12:42:24");

            assertThat(dateTime.second(), equalTo(24));
        }

        @Test
        void should_get_integral_part_of_second() {
            Iso8601DateTime dateTime = new Iso8601DateTimeImpl("2023-01-01T12:42:01,9999");

            assertThat(dateTime.second(), equalTo(1));
        }

        @Test
        void should_get_integral_part_of_second_2() {
            Iso8601DateTime dateTime = new Iso8601DateTimeImpl("2023-01-01T12:42:02,0001");

            assertThat(dateTime.second(), equalTo(2));
        }

        @Test
        void should_get_integral_part_of_second_3() {
            Iso8601DateTime dateTime = new Iso8601DateTimeImpl("2023-01-01T12:42:03.9999");

            assertThat(dateTime.second(), equalTo(3));
        }

        @Test
        void should_get_integral_part_of_second_4() {
            Iso8601DateTime dateTime = new Iso8601DateTimeImpl("2023-01-01T12:42:04.0001");

            assertThat(dateTime.second(), equalTo(4));
        }
    }

    @Nested
    @DisplayName("#fractionalSecond")
    class FractionalSecond {

        @Test
        void should_get_default_value_for_unspecified_fractional_second() {
            Iso8601DateTime dateTime = new Iso8601DateTimeImpl("2023-01-01T12:42:04");

            assertThat(dateTime.fractionalSecond(), equalTo(0.0));
        }

        @Test
        void should_access_fractional_second_after_period_1() {
            Iso8601DateTime dateTime = new Iso8601DateTimeImpl("2023-01-01T12:42:04.0001");

            assertThat(dateTime.fractionalSecond(), equalTo(0.0001));
        }

        @Test
        void should_access_fractional_second_after_period_2() {
            Iso8601DateTime dateTime = new Iso8601DateTimeImpl("2023-01-01T12:42:04.9999");

            assertThat(dateTime.fractionalSecond(), equalTo(0.9999));
        }

        @Test
        void should_access_fractional_second_after_period_3() {
            Iso8601DateTime dateTime = new Iso8601DateTimeImpl("2023-01-01T12:42:04.0000");

            assertThat(dateTime.fractionalSecond(), equalTo(0.0));
        }

        @Test
        void should_access_fractional_second_after_comma_1() {
            Iso8601DateTime dateTime = new Iso8601DateTimeImpl("2023-01-01T12:42:04,0001");

            assertThat(dateTime.fractionalSecond(), equalTo(0.0001));
        }

        @Test
        void should_access_fractional_second_after_comma_2() {
            Iso8601DateTime dateTime = new Iso8601DateTimeImpl("2023-01-01T12:42:04,9999");

            assertThat(dateTime.fractionalSecond(), equalTo(0.9999));
        }

        @Test
        void should_access_fractional_second_after_comma_3() {
            Iso8601DateTime dateTime = new Iso8601DateTimeImpl("2023-01-01T12:42:04,0000");

            assertThat(dateTime.fractionalSecond(), equalTo(0.0));
        }
    }
}
