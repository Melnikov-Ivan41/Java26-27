package Lab1.Hardtask;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        // Отримуємо системний компілятор Java
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            System.err.println("Помилка: JDK не знайдено. Переконайтеся, що ви використовуєте JDK, а не JRE.");
            return;
        }

        File sourceFile = new File("src/Lab1/Hardtask/TestModule.java");
        long lastModifiedTime = 0;

        System.out.println("Запуск програми. Очікування змін у TestModule.java...");

        // Безкінечний цикл: програма продовжує висіти в пам'яті
        while (true) {
            try {
                // Перевіряємо, чи файл існує і чи був він змінений з моменту останньої перевірки
                if (sourceFile.exists() && sourceFile.lastModified() > lastModifiedTime) {

                    // 1. Перекомпіляція класу
                    int compilationResult = compiler.run(null, null, null, sourceFile.getPath());

                    if (compilationResult == 0) {
                        // Оновлюємо час останньої зміни
                        lastModifiedTime = sourceFile.lastModified();

                        // 2. Створюємо НОВИЙ екземпляр нашого завантажувача
                        CustomClassLoader loader = new CustomClassLoader();

                        // 3. Завантажуємо оновлений клас в JVM
                        Class<?> testModuleClass = loader.loadClass("Lab1.Hardtask.TestModule");

                        // 4. Створюємо екземпляр класу TestModule
                        Object instance = testModuleClass.getDeclaredConstructor().newInstance();

                        // 5. Викликаємо System.out.println(t)
                        System.out.println("Оновлено: " + instance.toString());
                    } else {
                        System.err.println("Помилка компіляції. Перевірте синтаксис TestModule.java.");
                    }
                }

                // Пауза 2 секунди, щоб не навантажувати процесор
                Thread.sleep(2000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
