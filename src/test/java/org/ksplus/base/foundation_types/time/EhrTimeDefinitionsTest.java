package org.ksplus.base.foundation_types.time;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openehr.base.foundation_types.time.TimeDefinitions;

class EhrTimeDefinitionsTest {

    private final TimeDefinitions timeDefinitions = new EhrTimeDefinitions();

    @Nested
    @DisplayName("#validYear")
    class ValidYear {

        @Test
        void should_accept_valid_year() {
            assertThat(timeDefinitions.validYear(0), equalTo(true));
            assertThat(timeDefinitions.validYear(1), equalTo(true));
            assertThat(timeDefinitions.validYear(9999), equalTo(true));
        }

        @Test
        void should_reject_invalid_year() {
            assertThat(timeDefinitions.validYear(-1), equalTo(false));
        }
    }

    @Nested
    @DisplayName("#validMonth")
    class ValidMonth {

        @Test
        void should_accept_valid_month() {
            assertThat(timeDefinitions.validMonth(1), equalTo(true));
            assertThat(timeDefinitions.validMonth(12), equalTo(true));
        }

        @Test
        void should_reject_invalid_month() {
            assertThat(timeDefinitions.validMonth(0), equalTo(false));
            assertThat(timeDefinitions.validMonth(13), equalTo(false));
        }
    }

    @Nested
    @DisplayName("#validDay")
    class ValidDay {

        @Test
        void should_accept_valid_day() {
            assertThat(timeDefinitions.validDay(2000, 1, 1), equalTo(true));
            assertThat(timeDefinitions.validDay(2000, 1, 31), equalTo(true));
        }

        @Test
        void should_reject_invalid_day() {
            assertThat(timeDefinitions.validDay(2000, 1, 0), equalTo(false));
            assertThat(timeDefinitions.validDay(2000, 1, 32), equalTo(false));
            assertThat(timeDefinitions.validDay(2000, 2, 30), equalTo(false));
        }
    }

    @Nested
    @DisplayName("#validHour")
    class ValidHour {

        @Test
        void should_accept_valid_hour() {
            assertThat(timeDefinitions.validHour(0, 0, 0), equalTo(true));
            assertThat(timeDefinitions.validHour(23, 59, 59), equalTo(true));
            assertThat(timeDefinitions.validHour(24, 0, 0), equalTo(true));
        }

        @Test
        void should_reject_invalid_hour() {
            assertThat(timeDefinitions.validHour(-1, 0, 0), equalTo(false));
            assertThat(timeDefinitions.validHour(25, 61, 61), equalTo(false));
            assertThat(timeDefinitions.validHour(24, 1, 0), equalTo(false));
        }
    }

    @Nested
    @DisplayName("#validMinute")
    class ValidMinute {

        @Test
        void should_accept_valid_minute() {
            assertThat(timeDefinitions.validMinute(0), equalTo(true));
            assertThat(timeDefinitions.validMinute(59), equalTo(true));
        }

        @Test
        void should_reject_invalid_minute() {
            assertThat(timeDefinitions.validMinute(-1), equalTo(false));
            assertThat(timeDefinitions.validMinute(60), equalTo(false));
        }
    }

    @Nested
    @DisplayName("#validSecond")
    class ValidSecond {

        @Test
        void should_accept_valid_second() {
            assertThat(timeDefinitions.validSecond(0), equalTo(true));
            assertThat(timeDefinitions.validSecond(59), equalTo(true));
        }

        @Test
        void should_reject_invalid_second() {
            assertThat(timeDefinitions.validSecond(-1), equalTo(false));
            assertThat(timeDefinitions.validSecond(60), equalTo(false));
        }
    }

    @Nested
    @DisplayName("#validFractionalSecond")
    class ValidFactionalSecond {

        @Test
        void should_accept_valid_fractional_second() {
            assertThat(timeDefinitions.validFractionalSecond(0.0), equalTo(true));
            assertThat(timeDefinitions.validFractionalSecond(0.1), equalTo(true));
            assertThat(timeDefinitions.validFractionalSecond(0.9999999999999999), equalTo(true));
        }

        @Test
        void should_reject_invalid_fractional_second() {
            assertThat(timeDefinitions.validFractionalSecond(-1.0), equalTo(false));
            assertThat(timeDefinitions.validFractionalSecond(1.0), equalTo(false));
        }
    }

    @Nested
    @DisplayName("#validIso8601Date")
    class ValidIso8601Date {

        /**
         * @see <a href="https://ijmacd.github.io/rfc3339-iso8601/">ijmacd.github.io</a>
         *
         */
        @Test
        void should_accept_valid_iso8601_date() {
            assertThat(timeDefinitions.validIso8601Date("2023-07-29"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Date("20230729"), equalTo(true));

            assertThat(timeDefinitions.validIso8601Date("2023-07"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Date("202307"), equalTo(true));

            assertThat(timeDefinitions.validIso8601Date("2023-210"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Date("2023210"), equalTo(true));

            assertThat(timeDefinitions.validIso8601Date("2023"), equalTo(true));
        }

        /**
         * @see <a href="https://specifications.openehr.org/releases/BASE/Release-1.2.0/foundation_types.html#_primitive_time_types">
         *     specifications.openehr.org</a>
         */
        @Test
        void should_reject_valid_iso8601_date_excluded_by_openEHR_specification() {
            assertThat(timeDefinitions.validIso8601Date("20"), equalTo(false));
            assertThat(timeDefinitions.validIso8601Date("202"), equalTo(false));
            assertThat(timeDefinitions.validIso8601Date("2023-W30"), equalTo(false));
            assertThat(timeDefinitions.validIso8601Date("2023-W30-6"), equalTo(false));
            assertThat(timeDefinitions.validIso8601Date("2023W30"), equalTo(false));
            assertThat(timeDefinitions.validIso8601Date("2023W306"), equalTo(false));
        }

        @Test
        void should_reject_invalid_iso8601_date() {
            assertThat(timeDefinitions.validIso8601Date("2"), equalTo(false));
            assertThat(timeDefinitions.validIso8601Date("99999"), equalTo(false));
        }
    }

    @Nested
    @DisplayName("#validIso8601Time")
    class ValidIso8601Time {

        /**
         * @see <a href="https://ijmacd.github.io/rfc3339-iso8601/">ijmacd.github.io</a>
         */
        @Test
        void should_accept_valid_iso_8601_time() {
            assertThat(timeDefinitions.validIso8601Time("14:37:23+02:00"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14:37:23.5+02:00"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14:37:23.50+02:00"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14:37:23.508+02:00"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14:37:23.508549+02:00"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("12:37:23Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("12:37:23.5Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("12:37:23.50Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("12:37:23.508Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("12:37:23.508549Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("12:37:23+00:00"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("12:37:23.5+00:00"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("12:37:23.508+00:00"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("12:37:23.508549+00:00"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("14,6"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("14.6"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14:37"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("14:37,3"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("14:37.3"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14:37:23"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14:37:23.5"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14:37:23.50"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14:37:23,508"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14:37:23.508"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14:37:23,508549"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14:37:23.508549"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("12Z"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("12,6Z"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("12.6Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("12:37Z"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("12:37,3Z"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("12:37.3Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("12:37:23,508Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("12:37:23,508549Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14+02"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("14,6+02"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("14.6+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14:37+02"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("14:37,3+02"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("14:37.3+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14:37:23+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14:37:23.5+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14:37:23.50+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14:37:23,508+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14:37:23.508+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14:37:23,508549+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14:37:23.508549+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14+02:00"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("14,6+02:00"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("14.6+02:00"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14:37+02:00"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("14:37,3+02:00"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("14:37.3+02:00"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14:37:23,508+02:00"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14:37:23,508549+02:00"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T14,6"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T14.6"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14:37"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T14:37,3"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T14:37.3"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14:37:23"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14:37:23.5"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14:37:23.50"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14:37:23,508"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14:37:23.508"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14:37:23,508549"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14:37:23.508549"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T12Z"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T12,6Z"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T12.6Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T12:37Z"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T12:37,3Z"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T12:37.3Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T12:37:23Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T12:37:23.5Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T12:37:23.50Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T12:37:23,508Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T12:37:23.508Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T12:37:23,508549Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T12:37:23.508549Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14+02"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T14,6+02"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T14.6+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14:37+02"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T14:37,3+02"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T14:37.3+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14:37:23+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14:37:23.5+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14:37:23.50+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14:37:23,508+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14:37:23.508+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14:37:23,508549+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14:37:23.508549+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14+02:00"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T14,6+02:00"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T14.6+02:00"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14:37+02:00"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T14:37,3+02:00"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T14:37.3+02:00"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14:37:23+02:00"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14:37:23.5+02:00"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14:37:23.50+02:00"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14:37:23,508+02:00"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14:37:23.508+02:00"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14:37:23,508549+02:00"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14:37:23.508549+02:00"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("1437"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("1437,3"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("1437.3"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("143723"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("143723.5"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("143723.50"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("143723,508"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("143723.508"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("143723,508549"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("143723.508549"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("1237Z"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("1237,3Z"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("1237.3Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("123723Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("123723.5Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("123723.50Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("123723,508Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("123723.508Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("123723,508549Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("123723.508549Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("1437+02"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("1437,3+02"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("1437.3+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("143723+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("143723.5+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("143723.50+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("143723,508+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("143723.508+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("143723,508549+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("143723.508549+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("14+0200"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("14,6+0200"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("14.6+0200"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("1437+0200"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("1437,3+0200"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("1437.3+0200"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("143723+0200"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("143723.5+0200"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("143723.50+0200"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("143723,508+0200"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("143723.508+0200"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("143723,508549+0200"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("143723.508549+0200"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T1437"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T1437,3"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T1437.3"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T143723"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T143723.5"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T143723.50"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T143723,508"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T143723.508"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T143723,508549"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T143723.508549"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T1237Z"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T1237,3Z"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T1237.3Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T123723Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T123723.5Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T123723.50Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T123723,508Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T123723.508Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T123723,508549Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T123723.508549Z"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T1437+02"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T1437,3+02"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T1437.3+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T143723+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T143723.5+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T143723.50+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T143723,508+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T143723.508+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T143723,508549+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T143723.508549+02"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T14+0200"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T14,6+0200"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T14.6+0200"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T1437+0200"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T1437,3+0200"), equalTo(true));
            // assertThat(timeDefinitions.validIso8601Time("T1437.3+0200"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T143723+0200"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T143723.5+0200"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T143723.50+0200"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T143723,508+0200"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T143723.508+0200"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T143723,508549+0200"), equalTo(true));
            assertThat(timeDefinitions.validIso8601Time("T143723.508549+0200"), equalTo(true));
        }

        /**
         * @see <a href="https://www.myintervals.com/blog/2009/05/20/iso-8601-date-validation-that-doesnt-suck/">
         *     ISO 8601 Date Validation That Doesn’t Suck | myintervals.com
         *     </a>
         */
        @ParameterizedTest
        @ValueSource(strings = {
            "24:50",
            "14a39r",
            "14:3924",
            "14a39a22",
            "14:39:22+06a00",
            "14.5.44",
            "16,25:23:48,444",
        })
        void should_reject_invalid_iso_8601_time(String time) {
            assertThat(timeDefinitions.validIso8601Time(time), equalTo(false));
        }
    }

    @Nested
    @DisplayName("#validIso8601DateTime")
    class ValidIso8601DateTime {

        /**
         * @see <a href="https://www.myintervals.com/blog/2009/05/20/iso-8601-date-validation-that-doesnt-suck/">
         *     ISO 8601 Date Validation That Doesn’t Suck | myintervals.com
         *     </a>
         * @see <a href="https://stackoverflow.com/questions/9531524/in-an-iso-8601-date-is-the-t-character-mandatory">stackoverflow.com</a>
         */
        @ParameterizedTest
        @ValueSource(strings = {
            "2009-12T12:34",
            "2009",
            "2009-05-19",
            "2009-05-19",
            "20090519",
            "2009123",
            "2009-05",
            "2009-123",
            "2009-222",
            "2009-001",
            "2009-05-19",
            "2009-05-19T14:39Z",
            "2009-139",
            "20090621T0545Z",
            "2007-04-06T00:00",
            "2010-02-18T16:23:48.5",
            "2010-02-18T16:23:48,444",
            "2010-02-18T16:23:48,3-06:00",
        })
        void should_accept_valid_iso_8601_date_time(String dateTime) {
            assertThat(timeDefinitions.validIso8601DateTime(dateTime), equalTo(true));
        }

        /**
         * @see <a href="https://www.myintervals.com/blog/2009/05/20/iso-8601-date-validation-that-doesnt-suck/">
         *     ISO 8601 Date Validation That Doesn’t Suck | myintervals.com
         *     </a>
         */
        @ParameterizedTest
        @ValueSource(strings = {
            "2009367",
            "2009-",
            "2007-04-05T24:50",
            "2009-000",
            "2009-M511",
            "2009M511",
            "2009-05-19T14a39r",
            "2009-05-19T14:3924",
            "2009-0519",
            "2009-05-1914:39",
            "2009-05-19 14:",
            "2009-05-19r14:39",
            "2009-05-19 14a39a22",
            "200912-01",
            "2009-05-19 14:39:22+06a00",
            "2009-05-19 146922.500",
            "2010-02-18T16.5:23.35:48",
            "2010-02-18T16:23.35:48",
            "2010-02-18T16:23.35:48.45",
            "2009-05-19 14.5.44",
            "2010-02-18T16:23.33.600",
            "2010-02-18T16,25:23:48,444",
        })
        void should_reject_invalid_iso_8601_date_time(String dateTime) {
            assertThat(timeDefinitions.validIso8601DateTime(dateTime), equalTo(false));
        }

        @ParameterizedTest
        @ValueSource(strings = {
            "2007-04-05T24:00",
            "2010-02-18T16:23.4",
            "2010-02-18T16:23,25",
            "2010-02-18T16.23334444",
            "2010-02-18T16,2283",
            "2009-W01-1",
            "2009-W51-1",
            "2009-W511",
            "2009-W33",
            "2009W511",
            "2009-W21-2",
            "2009-W21-2T01:22",
            "2010-02-18T16:23.33+0600",
        })
        void should_reject_invalid_openehr_date_time(String dateTime) {
            assertThat(timeDefinitions.validIso8601DateTime(dateTime), equalTo(false));
        }
    }

    @Nested
    @DisplayName("#xxxx")
    class ValidXxxx {

        @Test
        void should_accept_valid_xxx() {
            // assertThat(timeDefinitions.validXxx(1), equalTo(true));
        }

        @Test
        void should_reject_invalid_xxxx() {
            // assertThat(timeDefinitions.validXxxx(0), equalTo(false));
        }
    }
}