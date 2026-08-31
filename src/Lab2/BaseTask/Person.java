package Lab2.BaseTask;

import java.util.Objects;

public class Person {
    private final String surname;
    private final String name;
    private final int age;

    public Person(String surname, String name, int age) {
        this.surname = surname;
        this.name = name;
        this.age = age;
    }

    public String getSurname() { return surname; }
    public String getName() { return name; }
    public int getAge() { return age; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(surname, person.surname) &&
                Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, name, age);
    }

    @Override
    public String toString() {
        return "Person{surname='" + surname + "', name='" + name + "', age=" + age + "}";
    }
}
