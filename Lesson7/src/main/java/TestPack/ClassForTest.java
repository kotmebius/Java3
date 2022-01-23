package TestPack;

import java.lang.annotation.*;

// Тестовый класс

public class ClassForTest {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Test {
        int value();
    }
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface BeforeSuite {};

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface AfterSuite{};

    @Test(value = 1)
    public void write1() {
        System.out.println("Первый раздел вечеринки");
    }

    @BeforeSuite
    public void before(){
        System.out.println("Начинаем вечеринку");
    }

    @AfterSuite
    public void after(){
        System.out.println("Вечеринка закончена");
    }

    @Test(value = 5)
    public void write5() {
        System.out.println("Пятый раздел вечеринки");
    }

    @Test(value = 5)
    public void write5Part2() {
        System.out.println("Пятый раздел вечеринки - подраздел 2");
    }

    @Test(value = 7)
    public void write7() {
        System.out.println("Седьмой раздел вечеринки");
    }

    @Test(value = 5)
    public void write5Part3() {
        System.out.println("Пятый раздел вечеринки - подраздел 3");
    }
}
