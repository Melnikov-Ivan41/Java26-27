package Lab2.Base2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JournalEntry {
    private String surname;
    private String name;
    private LocalDate birthDate;
    private String phone;
    private Address address;

    public JournalEntry(String surname, String name, LocalDate birthDate, String phone, Address address) {
        this.surname = surname;
        this.name = name;
        this.birthDate = birthDate;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return String.format("Студент: %s %s | Дата народження: %s | Тел: %s | Адреса: %s",
                surname, name, birthDate.format(formatter), phone, address);
    }
}
