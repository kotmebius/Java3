package my;

import my.Fruits.Apple;
import my.Fruits.Box;
import my.Fruits.Orange;

import java.util.Arrays;

public class main {


    public static void main(String[] args) {
// Первая задача
        task1 myArr1 = new task1<>("text", 10, 'c', 25, "test");   //Инициализируем массив
        System.out.println(Arrays.toString(myArr1.getArr()));           //распечатываем массив
        myArr1.chageElements(2, 5);                                      //меняем местами элементы 2 и 5
        System.out.println(Arrays.toString(myArr1.getArr()));           //распечатываем для проверки


//Вторая задача
        task2 <Integer> myArr2Int = new task2<>(5,8,9,1,556,33,76); //создаём объект с массивом интов
        task2 <String> myArr2str = new task2<>("text", "4", "c");   //создаем объект с массивом строк
        System.out.println(Arrays.toString(myArr2Int.convertToArrList().toArray())); //выводим сконвертированный ArraList


//Третья задача
        Box box1 = new Box();                   //Создаем коробки
        Box box2 = new Box();
        Box box3 = new Box();

        box1.addFruit(new Apple());             //В первую коробку кладем 3 яблока и пытаемся положить один апельсин
        box1.addFruit(new Apple());
        box1.addFruit(new Orange());
        box1.addFruit(new Apple());

        System.out.println(box1.getWeight());   //Выводим вес первой и второй коробки, они отличаются, сравниваем веса, получаем ложь.
        System.out.println(box2.getWeight());
        System.out.println(box1.compare(box2));

        box2.addFruit(new Orange());            //добавляем во вторую коробку 2 апельсина для одинакового веса и сравниваем
        box2.addFruit(new Orange());
        System.out.println(box1.compare(box2));


        box3.addFruit(new Orange());            //В третью коробку закидываем один апельсин

        box1.fillAnotherBox(box3);              //Пытаемся из первой коробки пересыпать яблоки в третью коробку, не получается

        box2.fillAnotherBox(box3);              //Пытаемся из второй коробки пересыпать апельсины в третью коробку - получается

        box1.printContent();                    //распечатываем содержимое коробок
        box2.printContent();
        box3.printContent();
    }
}
