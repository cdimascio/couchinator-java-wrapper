package io.github.cdimascio.couchinatorw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class CouchinatorTests {
    private String url = "http://localhost:5984";
    private String resourcePath = "./src/test/resources/fixtures";
    private Couchinator couchinator = Couchinator.configure().resources(resourcePath).prefix("test-").build();
    @Test
    public void createDb() {
        try {
            couchinator.create();
        } catch (CouchinatorException e) {
            fail(e);
        }
    }
    @Test
    public void reCreateDb() {
        try {
            couchinator.reCreate();
        } catch (CouchinatorException e) {
            fail(e);
        }
    }

    @Test
    public void destroy() {
        try {
            couchinator.destroy();
        } catch (CouchinatorException e) {
            fail(e);
        }
    }

    @Test
    public void resoursePathNotFound() {
        Couchinator couchinator = Couchinator.configure().url(url).resources("./does-not-exist").build();
        Assertions.assertThrows(CouchinatorException.class, couchinator::create);
    }
}
