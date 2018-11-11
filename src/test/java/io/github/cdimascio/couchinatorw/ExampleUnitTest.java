package io.github.cdimascio.couchinatorw;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExampleUnitTest {
    private Couchinator couchinator = new Couchinator("http://localhost:5984", "./src/test/resources/fixtures");
    @BeforeAll
    void beforeAll() throws CouchinatorException{
        couchinator.create();
    }

    @Test
    public void doStuff() {
        assertNotNull("do db stuff");
    }

    @AfterAll
    void afterAll() throws CouchinatorException{
        couchinator.destroy();
    }
}
