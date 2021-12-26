package MyPackage;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainClass {
    public static final int CARS_COUNT = 4;

    public static void main(String[] args) {
// Три циклических барьера, первый отрабатывает после того как все приготовились и выводится сообщение о всеобщей готовности, второй отрабатывает как старт,
// после данного сообщения, третий барьер отрабатывает в конце, чтобы вывести сообщение о конце гонки,
// Количествопотоков для блокировки = количество машин + 1 (основной поток)
        CyclicBarrier cyclicBarrier[] = {new CyclicBarrier(CARS_COUNT+1), new CyclicBarrier(CARS_COUNT+1), new CyclicBarrier(CARS_COUNT+1)};
//Семафор передаётся в туннель
        Semaphore smp = new Semaphore(CARS_COUNT/2);
//Защёлка winner защёлкивается первым финалистом
        final Lock winner = new ReentrantLock();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(smp), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), cyclicBarrier, winner);
        }

        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        try {
            cyclicBarrier[0].await(); //первый барьер, для вывода сообщения о готовности
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        try {
            cyclicBarrier[1].await();//второй барьер для старта гонки
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        try {
            cyclicBarrier[2].await();//третий барьер для окончания гонки
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!! Победил в гонке "+Car.getWinnerName());
    }
}

