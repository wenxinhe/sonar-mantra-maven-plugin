package com.thoughtworks.comsonar.mantra;

import com.google.common.collect.Lists;
import com.thoughtworks.sonar.mantra.Mantra;
import com.thoughtworks.sonar.mantra.MetricMeasures;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MetricMeasuresTest {
    private Mantra coverageMantra = new Mantra("coverage", "HIGHER", 3, 80d);

    @Test
    public void should_consider_fail_if_worse_than_history_record_in_count() {
        MetricMeasures metricMeasures = new MetricMeasures(
                                     Lists.newArrayList(57d, 40d, 55d, 56d));
        assertTrue(metricMeasures.checkMantraBreakness(coverageMantra).isWorse());
    }

    @Test
    public void should_not_consider_fail_if_worse_than_history_record_out_of_count() {
        MetricMeasures metricMeasures = new MetricMeasures(
                Lists.newArrayList(57d, 56d, 40d, 55d, 56d));
        assertFalse(metricMeasures.checkMantraBreakness(coverageMantra).isWorse());
    }

    @Test
    public void should_not_consider_fail_if_better_than_safe_value() {
        MetricMeasures metricMeasures = new MetricMeasures(
                Lists.newArrayList(81d, 82d, 81d, 80d));
        assertFalse(metricMeasures.checkMantraBreakness(coverageMantra).isWorse());
    }

    @Test
    public void should_ignore_history_count_if_bigger_than_existing_record() {
        MetricMeasures metricMeasures = new MetricMeasures(
                Lists.newArrayList(42d, 41d, 50d));
        assertFalse(metricMeasures.checkMantraBreakness(coverageMantra).isWorse());
    }

    @Test
    public void should_not_consider_fail_if_no_record_at_all() {
        MetricMeasures metricMeasures = new MetricMeasures(Lists.<Double>newArrayList());
        assertFalse(metricMeasures.checkMantraBreakness(coverageMantra).isWorse());
    }
}
