import MyTasks.Tasks;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.fail;

public class TasksTests {

//самый обычный тест
    @Test
    public void testTaskTwo1() {
        Assertions.assertArrayEquals(new int[]{1, 3}, Tasks.taskTwoMethod(new int[]{2, 5, 4, 1, 3}));
    }

//самый обычный тест с ошибкой
    @Test
    public void testTaskTwo2() {
        Assertions.assertArrayEquals(new int[]{1, 3, 5}, Tasks.taskTwoMethod(new int[]{2, 5, 4, 1, 3}));
    }

//самый обычный тест
    @Test
    public void testTaskTwo3() {
        Assertions.assertArrayEquals(new int[]{1, 3}, Tasks.taskTwoMethod(new int[]{4, 5, 4, 1, 3}));
    }

//Тест на исключение
    @Test
    public void testTaskTwo4() {
        Throwable exception = Assertions.assertThrows(RuntimeException.class, () -> {
            Tasks.taskTwoMethod(new int[]{2, 5, 1, 3});
        });
    }



    @ParameterizedTest
    @MethodSource("testTaskThree")
    public void testTaskThree(int[] array, boolean result) {
        Assertions.assertEquals(result, Tasks.taskThreeMethod(array));
    }

    public static Stream<Arguments> testTaskThree() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new int[] { 1, 6, 6 }, true));
        out.add(Arguments.arguments(new int[] { 2, 2, 2 }, false));
        out.add(Arguments.arguments(new int[] { 1, 2, 4 }, true));
        out.add(Arguments.arguments(new int[] { 1, 10, 5, 4 }, true));
        return out.stream();
    }
}
