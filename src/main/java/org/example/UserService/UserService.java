package org.example.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.UserService.token.ProductionToxins;
import org.example.UserService.token.TokenForUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final HashingPasswords hashingPasswords;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.hashingPasswords = new HashingPasswords(); // создаем энкодер
    }

    public User register(String name, String surname, String patronymic, String password) {
        if (false == "".equals(patronymic)) {
            if (userRepository.findByFullUsername(name + " " + surname + " " + patronymic).isPresent()) {
                throw new RuntimeException("Пользователь с таким именем уже существует");
            }
            User user = new User();
            user.setName(name);
            user.setSurname(surname);
            user.setPatronymic(patronymic);
            user.setPassword(password);
            return userRepository.save(user);
        }
        else {
            if (userRepository.findByFullUsername(name + " " + surname).isPresent()) {
                throw new RuntimeException("Пользователь с таким именем уже существует");
            }
            User user = new User();
            user.setName(name);
            user.setSurname(surname);
            user.setPassword(password);
            return userRepository.save(user);
        }
    }

    public TokenForUser login(String name, String surname, String patronymic, String password) {
        ProductionToxins productionToxins = new ProductionToxins();
        Optional<User> userOpt;
        if (false == "".equals(patronymic)) {
            userOpt = userRepository.findByFullUsername(name + " " + surname + " " + patronymic);
        }
        else {
            userOpt = userRepository.findByFullUsername(name + " " + surname);
        }
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Пользователь не найден");
        }
        User user = userOpt.get();
        if (hashingPasswords.checkPassword(password, user.getPassword())) {
            String token = productionToxins.generateJwtToken(user);
            return new TokenForUser(token, user);
        } else {
            throw new RuntimeException("Неверный пароль");
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, String newPassword) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Пользователь не найден");
        }
        User user = userOpt.get();
        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
