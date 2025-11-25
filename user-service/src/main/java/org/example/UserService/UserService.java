package org.example.UserService;

import org.example.UserService.token.ProductionToxins;
import org.example.UserService.token.TokenForUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Service
@RequestMapping("/api/Users")
public class UserService {
    private final UserRepository userRepository;
    private final HashingPasswords hashingPasswords;

    @PostMapping("/login")
    public TokenForUser login(@RequestBody DTO_user request) {
        return login(request.getName(), request.getSurname(), request.getPatronymic(), request.getPassword());
    }

    @PostMapping("/register")
    public User register(@RequestBody DTO_user request) {
        return register(request.getName(), request.getSurname(), request.getPatronymic(), request.getPassword());
    }

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.hashingPasswords = new HashingPasswords();
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
            user.setPassword(HashingPasswords.hashPassword(password));
            return userRepository.save(user);
        }
        else {
            if (userRepository.findByFullUsername(name + " " + surname).isPresent()) {
                throw new RuntimeException("Пользователь с таким именем уже существует");
            }
            User user = new User();
            user.setName(name);
            user.setSurname(surname);
            user.setPassword(HashingPasswords.hashPassword(password));
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
        user.setPassword(HashingPasswords.hashPassword(newPassword));
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
