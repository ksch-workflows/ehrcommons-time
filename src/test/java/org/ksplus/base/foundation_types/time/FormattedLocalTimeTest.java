package org.ksplus.base.foundation_types.time;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.ksplus.base.foundation_types.time.FormattedLocalTime;

class FormattedLocalTimeTest {

    @Test
    void should_format_local_time() {
        assertThat(new FormattedLocalTime("T143723,508").getValue(), is("14:37:23.508"));
        assertThat(new FormattedLocalTime("T143723.508").getValue(), is("14:37:23.508"));

        assertThat(new FormattedLocalTime("T143723").getValue(), is("14:37:23"));

        assertThat(new FormattedLocalTime("1410").getValueWithUnknownPartsAsZero(), is("14:10:00"));
        assertThat(new FormattedLocalTime("14").getValueWithUnknownPartsAsZero(), is("14:00:00"));
        assertThat(new FormattedLocalTime("141013").getValue(), is("14:10:13"));
        assertThat(new FormattedLocalTime("14:10:13").getValue(), is("14:10:13"));
        assertThat(new FormattedLocalTime("14:10:13,123").getValue(), is("14:10:13.123"));
    }
}
