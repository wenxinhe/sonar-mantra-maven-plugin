package com.thoughtworks.sonar.mantra;

public abstract class Compare {
    public static Compare HIGHER = new HigherCompare();
    public static Compare LOWER = new LowerCompare();

    abstract public boolean firstIsWorseThanSecond(Double first, Double second);

    public static Compare withComparer(String shouldBe) {
        return shouldBe.equals("HIGHER") ? HIGHER : LOWER;
    }

    private static class HigherCompare extends Compare {
        @Override
        public boolean firstIsWorseThanSecond(Double first, Double second) {
            return first < second;
        }
    }

    private static class LowerCompare extends Compare {
        @Override
        public boolean firstIsWorseThanSecond(Double first, Double second) {
            return first > second;
        }
    }
}
