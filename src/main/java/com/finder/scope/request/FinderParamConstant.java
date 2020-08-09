package com.finder.scope.request;

public enum FinderParamConstant {
    IS_CATEGORY_VALIDATED(1);
    private int value;

    FinderParamConstant(int value) {
        this.value = 1;
    }

    public static String getConstantByValue(Integer value) {
        for (FinderParamConstant constant : FinderParamConstant.values()) {
            if (value == constant.value) return constant.name();
        }
        return null;
    }

    public int getValue() {
        return value;
    }
}
