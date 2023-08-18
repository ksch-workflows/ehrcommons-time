package org.ksplus.base.foundation_types.time;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openehr.base.foundation_types.time.Iso8601Date;

class Iso8601DateTest {

    @Nested
    class Constructor {

        @ParameterizedTest
        @ValueSource(strings = {
            "-1001",
            "abcd",
            "2023-",
            "20",
            "2023-02-50"
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

    }
}