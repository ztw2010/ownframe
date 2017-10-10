package com.ebest.frame.annomationapilib.parama;

class Constants {

    /**
     * 生成的注入器的类名后缀。
     */
    static final String SUFFIX = "$$Injector";
    /**
     * 需要被过滤包名前缀。
     */
    static final String[] FILTER_PREFIX = new String[]{
            "com.android",
            "android",
            "java",
            "javax",
    };
}
