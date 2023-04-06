package core;

import utils.JacksonUtil;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

/**
 * @className: ResultJson
 * @description:
 * @author: Aim
 * @date: 2023/4/6
 **/
public class ResultJson implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final boolean SUCCESS = true;
    public static final boolean FAULT = false;
    // 请求是否成功
    private boolean state;
    // 返回信息
    private String msg;
    private String exceptionDetailMsg;
    private Object obj;

    public ResultJson() {
        this.state = true;
    }

    /**
     * 请求成功返回信息
     *
     * @param message
     */
    public ResultJson(String message) {
        this.state = SUCCESS;
        this.msg = message;
    }

    /**
     * 请求成功返回Object
     *
     * @param object 信息对象
     */
    public ResultJson(Object object) {
        this.state = SUCCESS;
        this.obj = object;
    }

    /**
     * 返回自定义信息内容
     *
     * @param sta     状态
     * @param message 消息
     */
    public ResultJson(boolean sta, String message) {
        this.state = sta;
        this.msg = message;
    }

    /**
     * 返回异常错误信息
     *
     * @param exceptionMessage 异常消息对象
     */
    public ResultJson(Throwable exceptionMessage) {
        exceptionMessage.printStackTrace(new PrintWriter(new StringWriter()));
        this.state = FAULT;
        this.msg = exceptionMessage.getMessage();
    }

    /**
     * 返回异常错误信息
     *
     * @param exceptionMessage 异常消息对象
     * @param detailMsg        是否设置详细信息
     */
    public ResultJson(Throwable exceptionMessage, boolean detailMsg) {
        exceptionMessage.printStackTrace(new PrintWriter(new StringWriter()));
        this.state = FAULT;
        this.msg = exceptionMessage.getMessage();
        if (detailMsg) {
            this.exceptionDetailMsg = exceptionMessage.toString();
        }

    }

    public boolean iState() {
        return this.state;
    }

    public String getMsg() {
        return this.msg;
    }

    public Object getObj() {
        return this.obj;
    }

    public String getExceptionDetailMsg() {
        return this.exceptionDetailMsg;
    }

    public String toJson() {
        return JacksonUtil.toJson(this);
    }
}