package com.westernstory.api.model;

import java.io.Serializable;
import java.lang.String;


public class ChargeModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 用户id **/
    private Long uid = 0L;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
    /** 支付id **/
    private int id = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /** order ids, 用户更改订单状态, 否则没地方存付款成功后更新的订单编号(一次付款可能会有多个订单) **/
    private String orderIds = "";

    public String getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(String orderIds) {
        this.orderIds = orderIds;
    }
    /*  状态定义 */
    public static int WAITING = 0; //等待付款
    public static int SUCCESS = 1; //付款成功
    public static int FAIL = 2; //付款失败
    public static int REFUND = 3; //已退款

    /* 状态 */
    private int status = 0;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
