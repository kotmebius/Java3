package my.Fruits;

import java.util.ArrayList;
import java.util.Arrays;

public class Box<T extends Fruit> {
    ArrayList<T> content = new ArrayList<>();
    boolean isEmpty = true;

    public void addFruit(T fruit) {
        if (this.isEmpty || this.content.get(0).getClass().getName().equals(fruit.getClass().getName())) {
            this.content.add(fruit);
            this.isEmpty = false;
        } else {
            System.out.println("В этой коробке находятся " + this.content.get(0).getClass().getSimpleName() + ", а Вы пытаетесь положить " + fruit.getClass().getSimpleName());
        }
    }

    public float getWeight() {
        if (this.isEmpty) {
            return 0f;
        } else {
            float weight = this.content.size() * this.content.get(0).getWeight();
            return weight;
        }
    }

    public boolean compare(Box anotherBox) {
        if (Math.abs(this.getWeight() - anotherBox.getWeight()) < 0.001f) {
            return true;
        } else {
            return false;
        }
    }

    public void fillAnotherBox(Box anotherBox) {
        if (anotherBox.isEmpty || anotherBox.content.get(0).getClass().getName().equals(this.content.get(0).getClass().getName()))
        {
            for (int i = 0; i <= this.content.size(); i++) {
                anotherBox.addFruit(this.content.get(0));
                this.content.remove(0);
            }
        } else{
            System.out.println("Коробки несовместимы");
        }

    }

    public void printContent() {
        System.out.println(Arrays.toString(this.content.toArray()));
    }
}
