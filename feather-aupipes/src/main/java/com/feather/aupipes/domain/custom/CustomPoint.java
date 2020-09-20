package com.feather.aupipes.domain.custom;

/**
 * 功能描述：
 *
 * @Author: fang xinliang
 * @Date: 2019/12/18 11:20
 */
public class CustomPoint {



    private double X;

    public double getX() {
        return X;
    }

    public void setX(double x) {
        this.X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        this.Y = y;
    }

    private double Y;


    /// <summary>
    /// 带参数构造方法
    /// </summary>
    /// <param name="x">x坐标</param>
    /// <param name="y">y坐标</param>
    public CustomPoint(double x, double y)
    {
        this.X = x;
        this.Y = y;
    }

}

