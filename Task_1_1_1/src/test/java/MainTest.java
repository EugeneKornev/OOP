import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for Main.
 */
public class MainTest {

    @Test
    public void testMainOutput() {
        // Захватываем вывод в консоль
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            // Запускаем главный метод
            Main.main(new String[]{});

            // Проверяем, что вывод содержит ожидаемые строки
            String output = outContent.toString();
            assertTrue(output.contains("Original array"),
                    "Output should contain 'Original array'");
            assertTrue(output.contains("Sorted array"),
                    "Output should contain 'Sorted array'");
            assertTrue(output.contains("5") && output.contains("3") && output.contains("8"),
                    "Output should contain array elements");
        } finally {
            // Восстанавливаем стандартный вывод
            System.setOut(originalOut);
        }
    }

    @Test
    public void testMainWithEmptyArgs() {
        // Этот тест проверяет, что метод main не падает с пустыми аргументами
        Main.main(new String[]{});
        // Если мы дошли до этой точки без исключений - тест пройден
    }
}