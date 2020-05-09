package com.blend.basic.NyetHack;

public class LambdaTest {

    /**
     * 函数类型能让开发者少写模式化代码，写出更为灵活的代码。
     * <p>
     * Java支持面向对象编程和lambda表达式，但是不支持函数作为参数传递给另外一个函数或者变量
     * <p>
     * 但是可以使用匿名内部类，定义在类中，用来实现某个方法的无名类，就像使用方法那样，可以把匿名内部类作为实例传递
     *
     * @param args
     */

    public static void main(String[] args) {
        System.out.println(greeting.greet("Blend", 6));
    }

    static Greeting greeting = (playName, numBuildings) -> {
        int currentYear = 2018;
        System.out.println("Adding " + numBuildings + " house");
        return "Welcome to SimVillage," + playName + " in " + currentYear;
    };

    //需要命名接口或者类来代表Lambda定义的函数，
    public interface Greeting {
        String greet(String playName, int numBuildings);

    }

}
