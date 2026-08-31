package Lab1.Hardtask;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            System.err.println("Помилка: JDK не знайдено. Переконайтеся, що ви використовуєте JDK, а не JRE.");
            return;
        }

        File sourceFile = new File("src/Lab1/Hardtask/TestModule.java");
        long lastModifiedTime = 0;

        System.out.println("Запуск програми. Очікування змін у TestModule.java...");

        while (true) {
            try {
                if (sourceFile.exists() && sourceFile.lastModified() > lastModifiedTime) {

                    int compilationResult = compiler.run(null, null, null, sourceFile.getPath());

                    if (compilationResult == 0) {
                        lastModifiedTime = sourceFile.lastModified();

                        CustomClassLoader loader = new CustomClassLoader();

                        Class<?> testModuleClass = loader.loadClass("Lab1.Hardtask.TestModule");

                        Object instance = testModuleClass.getDeclaredConstructor().newInstance();

                        System.out.println("Оновлено: " + instance.toString());
                    } else {
                        System.err.println("Помилка компіляції. Перевірте синтаксис TestModule.java.");
                    }
                }

                Thread.sleep(2000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
