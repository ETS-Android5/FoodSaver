package com.sp.myapplication.menu;

public class Order {
    private Long orderid;
    private String date;
    private String total;
    private Integer completion;

    // Mandatory empty constructor
    // for use of FirebaseUI
    public Order() {}

    // Getter and setter method
    public Long getOrderid()
    {
        return orderid;
    }
    public void setOrderid(Long orderid)
    {
        this.orderid = orderid;
    }
    public String getDate()
    {
        return date;
    }
    public void setDate(String date)
    {
        this.date = date;
    }
    public String getTotal()
    {
        return total;
    }
    public void setTotal(String total)
    {
            this.total = total;
    }
    public Integer getCompletion()
    {
        return completion;
    }
    public void setCompletion(Integer completion)
    {
        this.completion = completion;
    }
}
