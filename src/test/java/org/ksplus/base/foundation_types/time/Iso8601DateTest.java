package org.ksplus.base.foundation_types.time;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openehr.base.foundation_types.time.Iso8601Date;

class Iso8601DateTest {

    @Nested
    @DisplayName("#year")
    class Year {

        @ParameterizedTest
        @ValueSource(strings = {
            "2023",
            "2023-08",
            "2023-08-14",
        })
        void should_access_year(String value) {
            Iso8601Date date = new Iso8601DateImpl(value);

            assertThat(date.year(), equalTo(2023));
        }
    }
}