package com.weijifen.demining;

/**
 * Created by dell on 2017/8/18.
 */

public class Area {
    /**
     * 有没有雷
     */
    boolean bomb;
    /**
     *有没有被扫
     */
    boolean sweep;

    boolean flag;


    /**
     * 周围雷的数量
     */
    int number;

    public Area() {
        this.bomb = false;
        this.sweep = false;
        this.number = 0;
    }
    public Area(boolean bomb, boolean sweep, int number) {
        this.bomb = bomb;
        this.sweep = sweep;
        this.number = number;
    }

    public boolean isBomb() {
        return bomb;
    }

    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }

    public boolean isSweep() {
        return sweep;
    }

    public void setSweep(boolean sweep) {
        this.sweep = sweep;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
