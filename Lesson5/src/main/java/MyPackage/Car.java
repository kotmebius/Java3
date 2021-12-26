package MyPackage;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Car implements Runnable {
    private static int CARS_COUNT;
//добавил переменную для хранения имени победителя
    private static String winnerName;

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private CyclicBarrier cyclicBarrier[];
    Lock winner;
    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CyclicBarrier cyclicBarrier[], Lock winner) {
        this.race = race;
        this.speed = speed;
        this.cyclicBarrier=cyclicBarrier;
        this.winner=winner;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            cyclicBarrier[0].await(); //первый барьер, после него сообщение о готовности
            cyclicBarrier[1].await(); //второй барьер, после него начинается движуха
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        System.out.println(this.name + " закончил гонку");
//пытаемся щёлкнуть защёлкой lock, если получается, значит мы выиграли,выводим сообщение об этом и заносим своё имя в
// в переменную с именем победителя
        if (winner.tryLock()){
            System.out.println(this.name + " ПОБЕДИЛ В ГОНКЕ!!! ПОЗДРАВИМ ЕГО!!!");
            winnerName=this.name;
        }
        try {
            cyclicBarrier[2].await(); //третий барьер, для вывода сообщения, что гонка закончилась
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public static String getWinnerName() {
        return winnerName;
    }
}
