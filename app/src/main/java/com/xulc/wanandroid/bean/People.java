package com.xulc.wanandroid.bean;

/**
 * Date：2018/5/9
 * Desc：从链式到建造者模式
 * Created by xuliangchun.
 */

public class People {
    private String name;
    private String age;

    private People() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public People(Builder builder) {
        this.age = builder.age;
        this.name = builder.name;
    }

    public static class Builder{
        private String name;
        private String age;

        public Builder() {
        }

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder age(String age){
            this.age = age;
            return this;
        }

        public People build(){
            People people = new People(this);
            //非线程安全  在这里做最终校验
            return people;
        }
    }
}
