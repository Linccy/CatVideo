package com.catvideo.tv.catvideo.com.catvideo.Model;

import java.io.Serializable;

public class PgcChannel implements Serializable {
    public static final String SECOND_CATE_CODE = "second_cate_code";
    public static final String SECOND_CATE_NAME = "second_cate_name";
    public static final String FIRST_CATE_CODE = "first_cate_code";
    public int second_cate_code;
    public String second_cate_name;
    public int first_cate_code;

    public PgcChannel() {
        super();
    }

    public PgcChannel(int first_cate_code, int second_cate_code, String second_cate_name) {
        this.first_cate_code = first_cate_code;
        this.second_cate_name = second_cate_name;
        this.second_cate_code = second_cate_code;
    }
}
