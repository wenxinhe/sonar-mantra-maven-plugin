package com.thoughtworks.sonar.mantra;

public class CheckResult {
    private final boolean fail;
    private final String msg;

    public CheckResult(boolean fail, String msg) {
        this.fail = fail;
        this.msg = msg;
    }

    public static CheckResult failResult(String msg) {
        return new CheckResult(true, msg);
    }

    public static CheckResult passResult() {
        return new CheckResult(false, "");
    }

    public boolean isWorse() {
        return fail;
    }

    public String getMsg() {
        return msg;
    }
}
