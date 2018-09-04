package com.YankaPt;

public class testHash {
    public static void main(String[] args) {
        MyClass myClass = new MyClass(2);
        System.out.println(myClass.hashCode());
        Object object = myClass;
        System.out.println(object.hashCode());
        System.out.println(((MyClass)object).hashCode());
    }
}
class MyClass {
    int a;

    public MyClass(int a) {
        this.a = a;
    }
}
