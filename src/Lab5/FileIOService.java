package Lab5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;

public class FileIOService {

    public String findLineWithMaxWords(String filePath) throws IOException {
        String maxWordsLine = null;
        int maxWordsCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String trimmedLine = line.trim();
                if (trimmedLine.isEmpty()) {
                    continue;
                }

                String[] words = trimmedLine.split("\\s+");
                int currentWordsCount = words.length;

                if (currentWordsCount > maxWordsCount) {
                    maxWordsCount = currentWordsCount;
                    maxWordsLine = line;
                }
            }
        }

        return maxWordsLine;
    }

    public void encryptTextFile(String inputPath, String outputPath, char key) throws IOException {
        try (java.io.FileReader reader = new java.io.FileReader(inputPath);
             CipherWriter writer = new CipherWriter(new java.io.FileWriter(outputPath), key)) {

            int c;
            while ((c = reader.read()) != -1) {
                writer.write(c);
            }
        }
    }

    public void decryptTextFile(String inputPath, String outputPath, char key) throws IOException {
        try (DecipherReader reader = new DecipherReader(new java.io.FileReader(inputPath), key);
             java.io.FileWriter writer = new java.io.FileWriter(outputPath)) {

            int c;
            while ((c = reader.read()) != -1) {
                writer.write(c);
            }
        }
    }

    public void saveObject(Object obj, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(obj);
        }
    }

    public Object readObject(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return ois.readObject();
        }
    }
}
