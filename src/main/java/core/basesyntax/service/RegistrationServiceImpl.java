package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_PASSWORD = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() == null) {
            throw new NullPointerException("User's age is null");
        } else if (user.getLogin() == null) {
            throw new NullPointerException("User's login is null");
        }

        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("User's age is under 18");
        } else if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new RuntimeException("Your password is too short");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Such user already exists");
        }

        return storageDao.add(user);
    }
}
