package org.ksplus.base.foundation_types.time;

import jakarta.annotation.Nonnull;

class FormattedLocalTime {

    private static final String MINUS = "−";

    private static final String HYPHEN = "-";

    private final String formattedValue;

    public FormattedLocalTime(@Nonnull final String originalValue) {
        String formattedValue = originalValue;

        formattedValue = formattedValue.replaceFirst("^T", "");
        formattedValue = formattedValue.replace(",", ".");
        formattedValue = formattedValue.replace(MINUS, HYPHEN);

        if (!originalValue.contains(":")) {
            StringBuilder extendedFormattedValue = new StringBuilder();
            var len = formattedValue.length();
            for (int i = 0; i < len; i++) {
                char c = formattedValue.charAt(i);
                extendedFormattedValue.append(c);
                if (i != 0 && i < 5 && (i + 1 != formattedValue.length()) && ((i + 1) % 2) == 0) {
                    extendedFormattedValue.append(":");
                }
            }
            formattedValue = extendedFormattedValue.toString();
        }

        this.formattedValue = formattedValue;
    }

    @Nonnull
    public String getValueWithUnknownPartsAsZero() {
        String result = getValue();
        if (result.length() == 2) {
            result = result + ":00:00";
        }
        if (result.length() == 5) {
            result = result + ":00";
        }
        return result;
    }

    @Nonnull
    public String getValue() {
        return formattedValue;
    }
}
