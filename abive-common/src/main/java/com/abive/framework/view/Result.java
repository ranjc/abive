package com.abive.framework.view;

import com.abive.util.JsonUtil;

/**
 * Created by ranjiangchuan on 15/3/29.
 */
public class Result {
    public static final int SUCCESS                = 0; // 成功
    public static final int ERROR                  = 1; // 失败

    private int             tyreJavascriptResolver = 1; // js判断使用字段，尽量保证不重复
    private String          tip;                       // 提示内容
    private Object          body;                      // 数据源
    private int             code;                      // 返回结果

    public Result(int code, String tip, Object body) {
        checkCode(code);
        this.code = code;
        this.tip = tip;
        this.body = body;
    }

    public Result() {
        this(SUCCESS, null, null);
    }

    public Result(int code) {
        this(code, null, null);
    }

    public Result(String tip, Object body) {
        this(SUCCESS, tip, body);
    }

    public Result(String tip) {
        this(SUCCESS, tip, null);
    }

    public Result(Object body) {
        this(SUCCESS, null, body);
    }

    public Result(int code, String tip) {
        this(code, tip, null);
    }

    private void checkCode(int code) {
        if (!(code == ERROR || code == SUCCESS)) {
            throw new IllegalArgumentException("code must be 0 or 1");
        }
    }

    public String getTip() {
        return tip;
    }

    public Object getBody() {
        return body;
    }

    public int getCode() {
        return code;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTyreJavascriptResolver() {
        return tyreJavascriptResolver;
    }

    public void setTyreJavascriptResolver(int tyreJavascriptResolver) {
        this.tyreJavascriptResolver = tyreJavascriptResolver;
    }

    @Override
    public String toString() {
        return JsonUtil.objectToJson(this);
    }

}
