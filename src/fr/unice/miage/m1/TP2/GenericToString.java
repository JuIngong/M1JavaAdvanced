package fr.unice.miage.m1.TP2;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

/**
 * Class created on 05/10/2016
 *
 * @author JuIngong
 */
public class GenericToString {

    static public void main(String[] args) {
        System.out.println(new GenericToString().toString(new Point(12, 24)));

        Polygon pol = new Polygon(new int[]{10, 20, 30}, new int[]{20, 30, 40}, 3);
        pol.getBounds();
        System.out.println(new GenericToString().toString(pol));
    }


    private String toString(Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        Class cl = object.getClass();
        stringBuilder.append(cl.getName()).append("[");
        Field[] fields = cl.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            try {
                if (fields[i].getType().isPrimitive()) {
                    stringBuilder.append(fields[i].getName()).append("=").append(fields[i].get(object));
                }
//                else {
//                    stringBuilder.append(toString(fields[i]));
//                }
                if (i < fields.length - 1) {
                    stringBuilder.append("; ");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.append("]").toString();
    }
}
