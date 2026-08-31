package Lab1.Hardtask;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class CustomClassLoader extends ClassLoader {

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        // Якщо це наш клас, ми НЕ звертаємося до батьківського завантажувача (super.loadClass)
        if (name.equals("Lab1.Hardtask.TestModule")) {
            return findClass(name);
        }
        // Для всіх інших (наприклад, java.lang.String, Object) залишаємо стандартну поведінку
        return super.loadClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (name.equals("Lab1.Hardtask.TestModule")) {
            try {

                String filePath = "src/" + name.replace('.', '/') + ".class";
                File file = new File(filePath);

                if (!file.exists()) {
                    throw new ClassNotFoundException("Файл не знайдено: " + file.getPath());
                }

                byte[] classBytes = Files.readAllBytes(file.toPath());
                return defineClass(name, classBytes, 0, classBytes.length);

            } catch (IOException e) {
                throw new ClassNotFoundException("Не вдалося завантажити клас " + name, e);
            }
        }
        return super.findClass(name);
    }
}
