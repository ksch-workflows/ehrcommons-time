package org.ksplus.base.foundation_types.time;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
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
    }
}