package fr.unice.miage.m1.TP2;

import java.awt.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.List;

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

        System.out.println(new GenericToString().toString(new test()));
    }

    static class test{
        private int [] points;
        public test() {
            this.points = new int[]{12, 34};
        }
    }

    private List<Field> getAllFields(Class type) {
        List<Field> fields = new ArrayList<>();
        for (Class c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }

    private String toString(Object object) {

        StringBuilder stringBuilder = new StringBuilder();
        Class cl = object.getClass();
        stringBuilder.append(cl.getName()).append("[");
        List<Field> fields = getAllFields(cl);
        for (int i = 0; i < fields.size(); i++) {
            fields.get(i).setAccessible(true);
            try {
                if (fields.get(i).getType().isPrimitive()) {
                    stringBuilder.append(fields.get(i).getName()).append("=").append(fields.get(i).get(object));
                } else if (fields.get(i).getType().isArray()) {
                    ArrayToString(object, stringBuilder, fields, i);
                } else {
                    Object field = fields.get(i).get(object);
                    stringBuilder.append(fields.get(i).getName()).append("=");
                    stringBuilder.append(toString(field));
                }
                if (i < fields.size() - 1) {
                    stringBuilder.append("; ");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.append("]").toString();
    }

    private void ArrayToString(Object object, StringBuilder stringBuilder, List<Field> fields, int i) throws IllegalAccessException {
        stringBuilder.append(fields.get(i).getName()).append("{");
        Object array = fields.get(i).get(object);
        int length = Array.getLength(array);

        for (int j = 0; j < length; j++) {
            if (array.getClass().getComponentType().isPrimitive()) {
                stringBuilder.append(Array.get(array, j));
            }
            else {
                stringBuilder.append(toString(Array.get(array, j)));
            }
            if (j < length - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("}");
    }
}
