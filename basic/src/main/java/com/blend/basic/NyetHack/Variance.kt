package com.blend.basic.NyetHack


/**
 *为了进一步定制化类型参数，Kotlin提供了in和out两个关键字，进行智能类型转换
 *
 * 这就类似于java中的通配符
 *
 * Work extend Person
 *
 * out:可读而不可写的生产者角色（协变），类似于java中的 ？ extends Person（Person是一个基类）
 * 可读不可写的原因是：把泛型改成了协变的，意思就是上界咱们定为了Person，但是咱们的类型写的是 ? ，编译器并不知道咱们给它的具体类型
 * 是什么，只要是继承自 Person 的类就可以，所以 get 出的对象肯定是 Person 的子类型，根据多态的特性，所以能够直接赋值给 Person ，
 * 但是 add 就不可以了，咱们可能添加的是 Person 或 及其子类，所以编译器无法确定咱们添加的到底是什么类型就无法继续执行了，肯定就报错了
 *
 * in:可写而不可读的消费者角色（逆变），类似于java中的？ super Work。
 * 可写而不可读的原因是：因为Worker一定是这个未知类型的子类型，所以是可以add的。这里也没有get的限制，会变成Object ，
 * 因为在Java中所有类型都是Object的子类。”
 */
class Barrel<out T>(val item: T)


fun main(args: Array<String>) {

    var fedoraBarrel: Barrel<Fedora> = Barrel(Fedora("a generic-looking fedora", 15))

    var lootBarrel: Barrel<Loot> = Barrel(Coin(15))

    /**
     *既然泛型参数是生产者，item变量值就不能变，这样就可以安全的把fedoraBarrel赋值给lootBarrel，因为lootBarrel.item现在
     * 已经表示Fedora了，且不能改变
     *
     * Fedora和Coin都是Loot类的子类，但是两者并没有关系，只是他们的父类都有item属性
     */
    lootBarrel = fedoraBarrel

    val myFedora: Fedora = lootBarrel.item  //这里发生了智能类型转换



    inAndOut()

}


/**
 * 协变和逆变
 * 协变：协就是妥协，向基类妥协，out：输出
 * 逆变：逆就是反向，能一直逆到Any去，因为Any是任何类的父类，in：写入
 */
open class Person(val name: String, val age: Int) {
    open fun toWork() {
        println("我是工人$name，我要好好干活！！！")
    }
}

class Worker1(name: String, age: Int) : Person(name, age) {
    override fun toWork() {
        println("我是1工人$name，我要好好干活！！！")
    }
}

class Worker2(name: String, age: Int) : Person(name, age) {
    override fun toWork() {
        println("我是2工人$name，我也要好好干活！！！")
    }
}


/*
    这个方法提醒咱们这里的out关键字可以省略掉！不对啊，那省略了就会报错啊?
    原因：这其实就是Kotlin为咱们做的事，Kotlin中List是只读的，所以说肯定是安全的，所以官方在定义List接口的时候就直接定义成了协变的！
 */
fun setWorkOut(studentList: List<out Person>) {
    for (o in studentList) {
        o.toWork()
    }
}

/*
    修改成逆变，取出的都是object
 */
fun setWorkIn(studentList: MutableList<in Worker2>) {
    for (o in studentList) {
        o.toString()
    }
}

fun inAndOut() {
    val personArrayList: MutableList<Person> = ArrayList()
    personArrayList.add(Person("aaa", 11))
    personArrayList.add(Worker1("bbb", 12))
    personArrayList.add(Worker2("ccc", 13))

    val personArrayList1: MutableList<Worker1> = ArrayList()
    personArrayList1.add(Worker1("ddd", 14))
    val personArrayList2: MutableList<Worker2> = ArrayList()
    personArrayList2.add(Worker2("eee", 15))

    setWorkOut(personArrayList)
    setWorkOut(personArrayList1)
    setWorkOut(personArrayList2)

    setWorkIn(personArrayList)
    setWorkIn(personArrayList2)
}