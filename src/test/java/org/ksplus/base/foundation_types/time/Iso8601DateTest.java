package org.ksplus.base.foundation_types.time;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openehr.base.foundation_types.time.Iso8601Date;
import org.openehr.base.foundation_types.time.Iso8601Timezone;

class Iso8601DateTest {

    @Nested
    class Constructor {

        @ParameterizedTest
        @ValueSource(strings = {
            "2023-02-12",
            "2023-02-12T+03:00",
        })
        void should_accept_valid_date(String value) {
            Iso8601Date date = new Iso8601DateImpl(value);
            assertThat(date, notNullValue());
        }

        @ParameterizedTest
        @ValueSource(strings = {
            "-1001",
            "abcd",
            "2023-",
            "20",
            "2023-02-50",
            "2023-02-12T*03:00",
            "2023-02-12T03:00",
        })
        void should_reject_invalid_date(String value) {
            assertThrows(
                IllegalArgumentException.class, () -> new Iso8601DateImpl(value)
            );
        }
    }

    @Nested
    @DisplayName("#year")
    class Year {

        @ParameterizedTest
        @ValueSource(strings = {
            "2023",
            "2023-08",
            "2023-08-14",
            "202308",
            "20230814",
        })
        void should_access_year(String value) {
            Iso8601Date date = new Iso8601DateImpl(value);

            assertThat(date.year(), equalTo(2023));
        }

        @Nested
        @DisplayName("#month")
        class Month {

            @Test
            void should_access_month() {
                Iso8601Date date = new Iso8601DateImpl("2023-08");
                assertThat(date.month(), equalTo(8));
            }

            @Test
            void should_return_zero_if_month_not_present() {
                Iso8601Date date = new Iso8601DateImpl("2023");
                assertThat(date.month(), equalTo(0));
            }

            @ParameterizedTest
            @CsvFileSource(files = "src/test/resources/valid-dates.csv", useHeadersInDisplayName = true)
            void should_access_month_with_all_date_formats(
                String value, @SuppressWarnings("unused") Integer expectedYear, Integer expectedMonth
            ) {
                Iso8601Date date = new Iso8601DateImpl(value);
                assertThat(date.month(), equalTo(expectedMonth));
            }
        }

        @Nested
        @DisplayName("#day")
        class Day {

            @Test
            void should_access_day() {
                Iso8601Date date = new Iso8601DateImpl("2023-08-18");
                assertThat(date.day(), equalTo(18));
            }

            @Test
            void should_return_zero_if_day_not_present() {
                Iso8601Date date = new Iso8601DateImpl("2023-08");
                assertThat(date.day(), equalTo(0));
            }

            @ParameterizedTest
            @CsvFileSource(files = "src/test/resources/valid-dates.csv", useHeadersInDisplayName = true)
            void should_access_day_with_all_date_formats(
                String value,
                @SuppressWarnings("unused") Integer expectedYear,
                @SuppressWarnings("unused") Integer expectedMonth,
                Integer expectedDay
            ) {
                Iso8601Date date = new Iso8601DateImpl(value);
                assertThat(date.day(), equalTo(expectedDay));
            }
        }

        @Nested
        @DisplayName("#timezone")
        class Timezone {

            @Test
            void should_allow_void_value_for_timezone() {
                Iso8601Date date = new Iso8601DateImpl("2023-08-18");

                Iso8601Timezone timezone = date.timezone();

                assertThat(timezone, nullValue());
            }

            @Test
            void should_access_timezone() {
                Iso8601Date date = new Iso8601DateImpl("2023-08-18T+03:30");

                Iso8601Timezone timezone = date.timezone();

                assertThat(timezone, notNullValue());
                assertThat(timezone.sign(), equalTo(1));
                assertThat(timezone.hour(), equalTo(3));
                assertThat(timezone.minute(), equalTo(30));
            }
        }

        @Nested
        @DisplayName("#monthUnknown")
        class MonthUnknown {

            @Test
            void should_yield_month_unknown() {
                Iso8601Date date = new Iso8601DateImpl("2023");
                assertThat(date.monthUnknown(), equalTo(true));
            }

            @ParameterizedTest
            @ValueSource(strings = {
                "2023-08",
                "202308",
                "2023-08-14",
                "20230814",
            })
            void should_yield_month_known(String value) {
                Iso8601Date date = new Iso8601DateImpl(value);
                assertThat(date.monthUnknown(), equalTo(false));
            }
        }

        @Nested
        @DisplayName("#dayUnknown")
        class DayUnknown {

            @ParameterizedTest
            @ValueSource(strings = {
                "2023-08-14",
                "20230814",
            })
            void should_yield_day_known(String value) {
                Iso8601Date date = new Iso8601DateImpl(value);
                assertThat(date.dayUnknown(), equalTo(false));
            }

            @ParameterizedTest
            @ValueSource(strings = {
                "2023",
                "2023-08",
                "202308",
            })
            void should_yield_day_unknown(String value) {
                Iso8601Date date = new Iso8601DateImpl(value);
                assertThat(date.dayUnknown(), equalTo(true));
            }
        }

        @Nested
        @DisplayName("#getValue")
        class GetValue {

            @ParameterizedTest
            @ValueSource(strings = {
                "2023",
                "2023-08",
                "2023-08-14",
                "202308",
                "20230814",
            })
            void should_return_original_value(String value) {
                Iso8601Date date = new Iso8601DateImpl(value);
                assertThat(date.getValue(), equalTo(value));
            }
        }

        @Nested
        @DisplayName("#isPartial")
        class IsPartial {

            @ParameterizedTest
            @ValueSource(strings = {
                "2023-08",
                "202308",
            })
            void should_yield_true_for_missing_day(String value) {
                Iso8601Date date = new Iso8601DateImpl(value);
                assertThat(date.isPartial(), equalTo(true));
            }

            @Test
            void should_yield_true_for_missing_month() {
                Iso8601Date date = new Iso8601DateImpl("2023");
                assertThat(date.isPartial(), equalTo(true));
            }

            // TBD: Should `isPartial` yield `true` if the timezone is missing?
            // It not really clear whether the timezone is actually part of ISO 8601 or not.
            // For practical use it appears to be interesting whether the day or month is missing in a date.
            // The presence or absence of the timezone seems rather to be just a theoretical problem.
            // So, for the time being
            @ParameterizedTest
            @ValueSource(strings = {
                "2023-08-14",
                "2023-08-14T+03:00",
                "20230814",
                "20230814T+0300",
            })
            void should_yield_false_for_complete_date(String value) {
                Iso8601Date date = new Iso8601DateImpl(value);
                assertThat(date.isPartial(), equalTo(false));
            }
        }

        @Nested
        @DisplayName("#isExtended")
        class IsExtended {

            @ParameterizedTest
            @ValueSource(strings = {
                "2023",
                "202308",
                "20230814",
                "20230814T+0300",
            })
            void should_yield_false_for_basic_format(String value) {
                Iso8601Date date = new Iso8601DateImpl(value);
                assertThat(date.isExtended(), equalTo(false));
            }

            @ParameterizedTest
            @ValueSource(strings = {
                "2023-08",
                "2023-08-14",
                "2023-08-14T+03:00",
            })
            void should_yield_true_for_extended_format(String value) {
                Iso8601Date date = new Iso8601DateImpl(value);
                assertThat(date.isExtended(), equalTo(true));
            }
        }
    }
}