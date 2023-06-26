package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User bob;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        bob = new User();
    }

    @BeforeEach
    void setUp() {
        bob.setLogin("Bob");
        bob.setPassword("User187235");
        bob.setAge(28);
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_nullAge_NotOk() {
        bob.setAge(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_SmallAge_NotOk() {
        bob.setAge(12);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_nullLogin_NotOk() {
        bob.setLogin(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_SmallPassword_NotOk() {
        bob.setPassword("asd");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_SuchLoginExists_NotOk() {
        registrationService.register(bob);

        assertThrows(RuntimeException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_SuchUserDoesNotExists_Ok() {
        User test1 = registrationService.register(bob);

        assertTrue(Storage.people.contains(test1));
    }
}
