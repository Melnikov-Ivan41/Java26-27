package Lab2.BaseTask;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

public class PersonTest {

    @Test
    public void checkEqualsContract() {
        EqualsVerifier.forClass(Person.class)
                .usingGetClass() // <--- Добавляем эту строчку
                .verify();
    }
}
