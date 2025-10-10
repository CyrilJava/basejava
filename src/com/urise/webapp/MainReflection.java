package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//import static com.sun.glass.ui.View.accessible;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume();
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        Method[] methods = r.getClass().getDeclaredMethods();
        for (Method method : methods){
            System.out.println(method.getName());
        }
        Field [] fields = r.getClass().getDeclaredFields();
        for (Field field1:fields){
            System.out.println(field1.getName());
        }
        System.out.println("----------------");
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        System.out.println(r);
        Method method = r.getClass().getMethod("toString");
        System.out.println("invoke r.toString via reflection: "+ method.invoke(r));
    }
}