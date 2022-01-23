package TestPack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.TreeSet;

public class TestClass {
    static ClassForTest cft = new ClassForTest();
    public static void main(String[] args) {

        //Создаем объект тестового класса и передаём его в метод Start
        Class <?> testClass = cft.getClass();
        Method[] methods1 = testClass.getDeclaredMethods();
        start(testClass);
    }

    public static void start(Class<?> testClass) throws RuntimeException {
        //Определяем счётчики количества BeforeSuite и AfterSuite, для выброса исключения
        int countBeforeSuite=0;
        int countAfterSuite=0;
        //Создаём массив методов тестового класса и Treeset для их хранения
        Method[] methods = testClass.getDeclaredMethods();
        TreeSet <preparedMethods> setOfMethods = new TreeSet<>();

        //Разбираем и раскладываем методы в трисет, BeforeSuite c приоритетом 0,
        //AfterSuite с приритетом 11, остальные согласно указанного приоритета
        //для этого используется класс preparedMethods
        for (Method o : methods) {
            if(o.getAnnotation(ClassForTest.BeforeSuite.class) != null ) {
                setOfMethods.add(new preparedMethods(0,o));
                countBeforeSuite++;
            }
            if(o.getAnnotation(ClassForTest.AfterSuite.class) != null ) {
                setOfMethods.add(new preparedMethods(11,o));
                countAfterSuite++;
            }
            if (o.getAnnotation(ClassForTest.Test.class) != null ) {
                setOfMethods.add(new preparedMethods(o.getAnnotation(ClassForTest.Test.class).value(),o));
            }
        }

        //Если количество BeforeSuite или AfterSuite отлично от единицы - кидаем исключение
        if (countBeforeSuite !=1){
            throw new RuntimeException("В тестируемом классе " + countBeforeSuite +" методов BeforeSuite");
        }
        if (countAfterSuite !=1){
            throw new RuntimeException("В тестируемом классе " + countAfterSuite +" методов AfterSuite");
        }

        //Бежим по нашему TreeSet-дереву и пробуем выполнять методы
        Iterator <preparedMethods> iterator = setOfMethods.iterator();
        while (iterator.hasNext()) {
            Method meth = iterator.next().getPreparedMethod();
            try {
                meth.setAccessible(true);
                meth.invoke(cft);
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
