package com.sp.myapplication.order;

public class Item {
    private String foodimg;
    private String foodprice;
    private String foodname;
    private String expiry;
    private String desc;

    // Mandatory empty constructor
    // for use of FirebaseUI
    public Item() {}

    // Getter and setter method
    public String getfoodimg()
    {
        return foodimg;
    }
    public void setfoodimg(String foodimg)
    {
        this.foodimg = foodimg;
    }
    public String getfoodprice()
    {
        return foodprice;
    }
    public void setfoodprice(String foodprice)
    {
        this.foodprice = foodprice;
    }
    public String getfoodname()
    {
        return foodname;
    }
    public void setfoodname(String foodname)
    {
        this.foodname = foodname;
    }
    public String getexpiry()
    {
        return expiry;
    }
    public void setexpiry(String expiry)
    {
        this.expiry = expiry;
    }
    public String getdesc()
    {
        return desc;
    }
    public void setdesc(String desc)
    {
        this.desc = desc;
    }
}
