package com.blend.basic.Interop;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.function.Function;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Jhava {

    private int hitPoints = 52489112;

    private String greeting = "BLARGH";

    @NotNull
    public String utterGreeting() {
        return greeting;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    //使用@Nullable代表某个方法返回可能为null
    @Nullable
    public String determineFriendShipLevel() {
        return null;
    }

    //Kotlin可以避开使用getter/setter方法，可以使用看上去似乎是直接读写字段或属性的点语法，同时不影响封装性
    //因为getter方法getHitPoints带get前缀，可以在kotlin中不使用这个前缀，但必须以get开头
    //这种方式同样适用于setter方法
    public int getHitPoints() {
        return hitPoints;
    }

    //Kotlin代码使用JvmOverloads注解，使得java代码在调用时不会出错
    public void offerFood() {
        HeroKt.handOverFood("pozza");
    }

    //在java中定义异常
    public void extendHandInFriendShip() throws Exception {
        throw new Exception();
    }

    //处理Kotlin中的异常
    public void apologize() {
        try {
            HeroKt.acceptApology();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        //将makeProclamation函数当做HeroKt类的静态方法
        System.out.println(HeroKt.makeProclamation());

        Spellbook spellbook = new Spellbook();
        for (String spell : spellbook.spells) {
            System.out.println(spell);
        }

        for (String spell : spellbook.getSpellsNo()) {
            System.out.println(spell);
        }

        System.out.println("companion object no:" + Spellbook.Companion.getMAX_SPELL_COUNT_NO());
        System.out.println("companion object:" + Spellbook.MAX_SPELL_COUNT);

        Spellbook.getSpellBook();

        //Function接口有23个，从Function0到Function22。每一个FunctionN都包含一个invoke函数，专门调用函数类型函数。
        //所以任何时候调用一个函数类型，在Java中都需要invoke。
        Function1<String, Unit> translator = HeroKt.getTranslator();
        translator.invoke("TRUCE");
    }

}
