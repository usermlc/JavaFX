package com.await.repository.impl;

import com.github.javafaker.Faker;
import com.await.persistance.User;
import com.await.repository.contract.UserRepository;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class UserRepositoryImplSingleton implements UserRepository {

    private static final Logger logger = Logger.getLogger(UserRepositoryImplSingleton.class.getName());
    private static UserRepositoryImplSingleton instance;
    DriverManagerDataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private UserRepositoryImplSingleton() {
        try {
            dataSource = new DriverManagerDataSource("jdbc:h2:./data/db");
            dataSource.setDriverClassName("org.h2.Driver");
            jdbcTemplate = new JdbcTemplate(dataSource);
        }
        catch (Exception exception) {
            logger.warning(exception.getMessage());
        }

    }
    public static synchronized UserRepositoryImplSingleton getInstance() {
        if(instance == null) {
            return new UserRepositoryImplSingleton();
        }
        return instance;
    }
    @Override
    public Optional<User> findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{id}, getUserRowMapper());
            return Optional.ofNullable(user);
        } catch (Exception e) {
            logger.warning("User not found: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Set<User> findAll() {
        String sql = "SELECT * FROM USERS";
        try {
            List<User> user = jdbcTemplate.query(sql, getUserRowMapper());
            return new HashSet<>(user);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return new HashSet<>();
        }
    }

    @Override
    public boolean add(User entity) {
        String sql = "INSERT INTO USERS (FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, BIRTH_DATE, ADRESS, USERNAME) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, entity.getFirstName(), entity.getLastName(),
                entity.getEmail(), entity.getPhoneNumber(), entity.getDateOfBirth(),
                entity.getAddress(), entity.getUsername());
            return true;
        }
        catch (Exception e) {
            logger.warning("User not found: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean remove(int id) {
        String sql = "Delete from users where id = ?";
        try {
            jdbcTemplate.update(sql, id);
            return true;
        }
        catch (Exception e) {
            logger.warning("User not found: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Set<User> findByFirstName(String firstName) {
        String sql = "SELECT * FROM USERS where FIRST_NAME = ?";
        try {
            List<User> user = jdbcTemplate.query(sql, getUserRowMapper());
            return new HashSet<>(user);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return new HashSet<>();
        }
    }

    @Override
    public Set<User> findByLastName(String lastName) {
        String sql = "SELECT * FROM USERS where LAST_NAME = ?";
        try {
            List<User> user = jdbcTemplate.query(sql, getUserRowMapper());
            return new HashSet<>(user);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return new HashSet<>();
        }
    }

    @Override
    public Set<User> findByEmail(String email) {
        String sql = "SELECT * FROM USERS where EMAIL = ?";
        try {
            List<User> user = jdbcTemplate.query(sql, getUserRowMapper());
            return new HashSet<>(user);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return new HashSet<>();
        }
    }

    @Override
    public Set<User> findByPhoneNumber(String phoneNumber) {
        String sql = "SELECT * FROM USERS where PHONE_NUMBER = ?";
        try {
            List<User> user = jdbcTemplate.query(sql, getUserRowMapper());
            return new HashSet<>(user);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return new HashSet<>();
        }
    }

    @Override
    public Set<User> findByDateOfBirth(Date dateOfBirth) {
        String sql = "SELECT * FROM USERS where FIRST_NAME = ?";
        try {
            List<User> user = jdbcTemplate.query(sql, getUserRowMapper());
            return new HashSet<>(user);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return new HashSet<>();

        }
    }

    @Override
    public Set<User> findByAddress(String address) {
        String sql = "SELECT * FROM USERS where ADRESS = ?";
        try {
            List<User> user = jdbcTemplate.query(sql, getUserRowMapper());
            return new HashSet<>(user);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return new HashSet<>();
        }
    }

    @Override
    public Set<User> findByUsername(String username) {
        String sql = "SELECT * FROM USERS where USERNAME = ?";
        try {
            List<User> user = jdbcTemplate.query(sql, getUserRowMapper());
            return new HashSet<>(user);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return new HashSet<>();
        }
    }
    private RowMapper<User> getUserRowMapper() {
        return (rs, rowNum) -> new User.Builder()
            .withFirstName(rs.getString("FIRST_NAME"))
            .withLastName(rs.getString("LAST_NAME"))
            .withEmail(rs.getString("EMAIL"))
            .withPhoneNumber(rs.getString("PHONE_NUMBER"))
            .withDateOfBirth(rs.getDate("BIRTH_DATE"))
            .withAddress(rs.getString("ADRESS"))
            .withUsername(rs.getString("USERNAME"))
            .build();
    }
    public void generateData(int size) {
        Faker faker = new Faker();

        for (int i = 0; i < size; i++) {
            User user = new User.Builder().withFirstName(faker.name().firstName())
                .withLastName(faker.name().lastName())
                .withEmail(faker.internet().emailAddress())
                .withPhoneNumber(faker.phoneNumber().cellPhone())
                .withDateOfBirth(faker.date().birthday(18, 60))
                .withAddress(faker.address().streetAddress())
                .withUsername(faker.name().username())
                .build();

            add(user);
        }
    }
}
