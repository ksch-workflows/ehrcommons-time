package org.ksplus.base.foundation_types.time;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openehr.base.foundation_types.time.Iso8601Duration;

class EhrDurationTest {

    /**
     * @see <a href="https://en.wikipedia.org/wiki/ISO_8601#Durations">wikipedia.org</a>
     */
    @Test
    void should_provide_values_from_example_at_wikipedia() {
        Iso8601Duration duration = new EhrDuration("P3Y6M4DT12H30M5S");
        assertThat(duration.years(), equalTo(3));
        assertThat(duration.months(), equalTo(6));
        assertThat(duration.days(), equalTo(4));
        assertThat(duration.hours(), equalTo(12));
        assertThat(duration.minutes(), equalTo(30));
        assertThat(duration.seconds(), equalTo(5));
    }

    @Test
    void should_disambiguate_month_and_minute() {
        Iso8601Duration duration = new EhrDuration("P12MT42M");
        assertThat(duration.months(), equalTo(12));
        assertThat(duration.minutes(), equalTo(42));
    }

    @Nested
    @DisplayName("#fractionalSeconds")
    class FractionalSeconds {

        @Test
        void should_parse_second_without_fraction() {
            Iso8601Duration duration = new EhrDuration("PT42S");
            assertThat(duration.seconds(), equalTo(42));
            assertThat(duration.fractionalSeconds(), equalTo(0.0));
        }

        @Test
        void should_parse_second_with_comma_and_fraction() {
            Iso8601Duration duration = new EhrDuration("PT42,5S");
            assertThat(duration.seconds(), equalTo(42));
            assertThat(duration.fractionalSeconds(), equalTo(0.5));
        }

        @Test
        void should_parse_second_with_period_and_fraction() {
            Iso8601Duration duration = new EhrDuration("PT42.5S");
            assertThat(duration.seconds(), equalTo(42));
            assertThat(duration.fractionalSeconds(), equalTo(0.5));
        }
    }

    @Nested
    @DisplayName("#weeks")
    class Weeks {

        @Test
        void should_access_number_of_weeks() {
            Iso8601Duration duration = new EhrDuration("P12W");
            assertThat(duration.weeks(), equalTo(12));
        }
    }

    @Nested
    @DisplayName("#isDecimalSignComma")
    class IsDecimalSignComma {

        @ValueSource(strings = {
            "PT42.5S",
            "PT42S",
            "P12W",
        })
        @ParameterizedTest
        void should_yield_false(String value) {
            Iso8601Duration duration = new EhrDuration(value);
            assertThat(duration.isDecimalSignComma(), equalTo(false));
        }

        @Test
        void should_yield_true() {
            Iso8601Duration duration = new EhrDuration("PT42,5S");
            assertThat(duration.isDecimalSignComma(), equalTo(true));
        }
    }
}
