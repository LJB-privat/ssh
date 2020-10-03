package com.zking.ssh.Administrator.entity;

import javax.xml.crypto.Data;

public class Studnet {
    /**
     * id 用户id
     * name 用户名称
     * qq 用户qq
     * AddTime 用户创建时间
     * State 用户状态
     *
     */
    private int id;
    private String name;
    private int qq;
    private Data AddTime;
    private int State;
    private int tel;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQq() {
        return qq;
    }

    public void setQq(int qq) {
        this.qq = qq;
    }

    public Data getAddTime() {
        return AddTime;
    }

    public void setAddTime(Data addTime) {
        AddTime = addTime;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "Studnet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", qq=" + qq +
                ", AddTime=" + AddTime +
                ", State=" + State +
                ", tel=" + tel +
                '}';
    }
    public void Studnet(){

    }

}
