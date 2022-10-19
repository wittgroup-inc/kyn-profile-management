package com.wittgroup.kyn.profile.services;

import com.wittgroup.kyn.profile.client.AddressClient;
import com.wittgroup.kyn.profile.db.repositories.UserRepository;
import com.wittgroup.kyn.profile.models.*;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.wittgroup.kyn.profile.db.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AddressClient addressClient;
    private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;


    public List<User> findAll() {
        return userRepository.findAll(Sort.by("id"))
                .stream()
                .map(this::mapToUser)
                .collect(Collectors.toList());
    }

    public User get(final UUID id) {
        return userRepository.findById(id)
                .map(this::mapToUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public UUID create(final User user) {
        return userRepository.save(mapToUserEntity(user, new UserEntity())).getId();
    }

    public User signUp(SignUpRequest request) {
        Settings settings = new Settings(getValidUserName(IdGenerator.generateUsername()), request.getPassword(), request.getEmail());
        User user = new User(request.getFirstName(), request.getLastName(), request.getDob(), request.getSex(), Privacy.DEFAULT, settings);
        return mapToUser(userRepository.save(mapToUserEntity(user, new UserEntity())));
    }

    private String getValidUserName(String username) {
        Optional<UserEntity> user = userRepository.findBySettingsUsername(username);
        if (user.isPresent()) {
            getValidUserName(IdGenerator.generateUsername());
        }
        return username;
    }

    public void update(final UUID id, final User user) {
        final UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToUserEntity(user, entity);
        userRepository.save(entity);
    }

    public void delete(final UUID id) {
        userRepository.deleteById(id);
    }

    private User mapToUser(final UserEntity entity) {
        User user = new User(entity.getFirstName(), entity.getLastName(), entity.getDob(), entity.getSex(), entity.getPrivacy(), entity.getSettings());
        user.setId(entity.getId());
        user.setProfilePicUrl(entity.getProfilePicUrl());
        user.setSex(entity.getSex());
        if (entity.getAddresses() != null) {
            user.setAddress(entity.getAddresses().isEmpty() ? List.of() : entity.getAddresses().stream().map(this::findAddressById).toList());
        }
        user.setPrivacy(entity.getPrivacy());
        user.setSettings(entity.getSettings());
        user.setContact(entity.getContact());
        return user;
    }

    private UserEntity mapToUserEntity(final User user, final UserEntity entity) {
        entity.setId(user.getId());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setDob(user.getDob());
        entity.setProfilePicUrl(user.getProfilePicUrl());
        entity.setSex(user.getSex());
        if (user.getAddress() != null) {
            entity.setAddresses(user.getAddress().stream().map(this::createAddress).map(Address::getId).toList());
        }
        entity.setPrivacy(user.getPrivacy());
        entity.setSettings(user.getSettings());
        entity.setContact(user.getContact());
        return entity;
    }


    private Address findAddressById(UUID uuid) throws ResponseStatusException {
        Resilience4JCircuitBreaker circuitBreaker = circuitBreakerFactory.create("address-management");
        Supplier<Address> addressSupplier = () -> addressClient.getAddressById(uuid.toString());
        return circuitBreaker.run(addressSupplier, throwable -> handleAddressServiceErrorCase());
    }

    private Address handleAddressServiceErrorCase() throws ResponseStatusException {
        throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY);
    }

    private ResponseEntity<Void> handleUpdateAddressServiceErrorCase() throws ResponseStatusException {
        throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY);
    }

    private Address createAddress(Address address) {
        Resilience4JCircuitBreaker circuitBreaker = circuitBreakerFactory.create("address-management");
        Address result = findAddressById(address.getId());
        if (result != null) {
            if (result.equals(address)) {
                return result;
            } else {
                Supplier<ResponseEntity<Void>> addressSupplier = () -> addressClient.updateAddress(address.getId().toString(), address);
                circuitBreaker.run(addressSupplier, throwable -> handleUpdateAddressServiceErrorCase());
                return address;
            }
        }

        Supplier<Address> addressSupplier = () -> addressClient.createAddress(address);
        return circuitBreaker.run(addressSupplier, throwable -> handleAddressServiceErrorCase());
    }

}
