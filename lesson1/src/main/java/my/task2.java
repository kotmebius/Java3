package my;

import java.util.ArrayList;

public class task2<T> {
    private T[] myArr;

    public task2(T... myArr) {
        this.myArr = myArr;
    }


// Метод конвертвции массива в arraylist
    public ArrayList<T> convertToArrList() {
        ArrayList<T> myList = new ArrayList<>();
        for (int i=0; i<this.myArr.length; i++)
            myList.add(myArr[i]);
        return myList;
    }
}
