package my;

public class task1<T> {
    private T[] arr;
    private T buf;

    public task1(T... arr) {
        this.arr = arr;
    }

    public T[] getArr() {
        return arr;
    }

    public void chageElements(int element1, int element2) {
        if (element1 <= this.arr.length && element2 <= this.arr.length && element2 != element1) {
            buf = this.arr[element1 - 1];
            this.arr[element1 - 1] = this.arr[element2 - 1];
            this.arr[element2 - 1] = buf;
        }
    }
}
