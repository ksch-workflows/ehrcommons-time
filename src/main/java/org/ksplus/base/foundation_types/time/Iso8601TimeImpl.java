package org.ksplus.base.foundation_types.time;

import org.openehr.base.foundation_types.time.Iso8601Duration;
import org.openehr.base.foundation_types.time.Iso8601Time;
import org.openehr.base.foundation_types.time.Iso8601Timezone;

// TODO Rename to "EhrTime"
public class Iso8601TimeImpl implements Iso8601Time {

    @Override
    public Integer hour() {
        return null;
    }

    @Override
    public Integer minute() {
        return null;
    }

    @Override
    public Integer second() {
        return null;
    }

    @Override
    public Double fractionalSecond() {
        return null;
    }

    @Override
    public Iso8601Timezone timezone() {
        return null;
    }

    @Override
    public Boolean minuteUnknown() {
        return null;
    }

    @Override
    public Boolean secondUnknown() {
        return null;
    }

    @Override
    public Boolean isDecimalSignComma() {
        return null;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public Boolean isPartial() {
        return null;
    }

    @Override
    public Boolean isExtended() {
        return null;
    }

    @Override
    public Boolean hasFractionalSecond() {
        return null;
    }

    @Override
    public String asString() {
        return null;
    }

    @Override
    public Iso8601Time add(Object aDiff) {
        return null;
    }

    @Override
    public Iso8601Time subtract(Object aDiff) {
        return null;
    }

    @Override
    public Iso8601Duration diff(Object aTime) {
        return null;
    }

    @Override
    public Boolean lessThan(Object other) {
        return null;
    }

    @Override
    public Boolean lessThanOrEqual(Object other) {
        return null;
    }

    @Override
    public Boolean greaterThan(Object other) {
        return null;
    }

    @Override
    public Boolean greaterThanOrEqual(Object other) {
        return null;
    }

    @Override
    public Boolean validYear(Integer y) {
        return null;
    }

    @Override
    public Boolean validMonth(Integer m) {
        return null;
    }

    @Override
    public Boolean validDay(Integer y, Integer m, Integer d) {
        return null;
    }

    @Override
    public Boolean validHour(Integer h, Integer m, Integer s) {
        return null;
    }

    @Override
    public Boolean validMinute(Integer m) {
        return null;
    }

    @Override
    public Boolean validSecond(Integer s) {
        return null;
    }

    @Override
    public Boolean validFractionalSecond(Double fs) {
        return null;
    }

    @Override
    public Boolean validIso8601Date(String s) {
        return null;
    }

    @Override
    public Boolean validIso8601Time(String s) {
        return null;
    }

    @Override
    public Boolean validIso8601DateTime(String s) {
        return null;
    }

    @Override
    public Boolean validIso8601Duration(String s) {
        return null;
    }
}
