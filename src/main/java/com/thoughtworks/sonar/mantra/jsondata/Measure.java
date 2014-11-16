package com.thoughtworks.sonar.mantra.jsondata;

import org.joda.time.DateTime;

import java.util.List;

public class Measure {
    private List<Double> v;
    private DateTime d;

    public List<Double> getV() {
        return v;
    }

    public void setV(List<Double> v) {
        this.v = v;
    }

    public DateTime getD() {
        return d;
    }
}
