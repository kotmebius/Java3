package TestPack;

import java.lang.reflect.Method;


//Данный класс содаржит приритет Метода и сам метод.
//Также в нем переопределён метод CompareTo для использования TreeSet

public class preparedMethods implements Comparable <preparedMethods> {
    int order;
    Method preparedMethod;

    preparedMethods (int order, Method preparedMethod){
        this.order=order;
        this.preparedMethod=preparedMethod;
    }

    @Override
    public int compareTo(preparedMethods p){
        if (order>p.order) {
            return 1;
        } else {
            return -1;
        }
    }

    //Getter для метода
    public Method getPreparedMethod() {
        return preparedMethod;
    }
}
