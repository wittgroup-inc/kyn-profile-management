package com.wittgroup.kyn.profile.services;

import com.wittgroup.kyn.profile.client.AddressClient;
import com.wittgroup.kyn.profile.db.repositories.UserRepository;
import com.wittgroup.kyn.profile.models.User;
import com.wittgroup.kyn.profile.models.Address;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.wittgroup.kyn.profile.db.entities.UserEntity;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UserService {

    private UserRepository userRepository;
    private AddressClient addressClient;

    private Resilience4JCircuitBreakerFactory circuitBreakerFactory;


    public UserService(final UserRepository userRepository,
                       final AddressClient addressClient,
                       final Resilience4JCircuitBreakerFactory circuitBreakerFactory) {
        this.userRepository = userRepository;
        this.addressClient = addressClient;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    public List<User> findAll() {
        return userRepository.findAll(Sort.by("id"))
                .stream()
                .map(entity -> mapToUser(entity, new User()))
                .collect(Collectors.toList());
    }

    public User get(final UUID id) {
        return userRepository.findById(id)
                .map(entity -> mapToUser(entity, new User()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public UUID create(final User user) {
        final UserEntity entity = new UserEntity();
        mapToUserEntity(user, entity);
        return userRepository.save(entity).getId();
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

    private User mapToUser(final UserEntity user, final User userDTO) {
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setProfilePicUrl(user.getProfilePicUrl());
        userDTO.setSex(user.getSex());
        userDTO.setAddress(user.getAddresses().isEmpty() ? List.of() : user.getAddresses().stream().map(this::findAddressById).toList());
        userDTO.setPrivacy(user.getPrivacy());
        userDTO.setSettings(user.getSettings());
        userDTO.setContact(user.getContact());
        return userDTO;
    }

    private UserEntity mapToUserEntity(final User user, final UserEntity entity) {
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setProfilePicUrl(user.getProfilePicUrl());
        entity.setSex(user.getSex());
        entity.setAddresses(user.getAddress().stream().map(this::createAddress).map(Address::getId).toList());
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

    private Address createAddress(Address address) {
        Resilience4JCircuitBreaker circuitBreaker = circuitBreakerFactory.create("address-management");
        Address result = findAddressById(address.getId());
        if (result != null)
            return result;
        Supplier<Address> addressSupplier = () -> addressClient.createAddress(address);
        return circuitBreaker.run(addressSupplier, throwable -> handleAddressServiceErrorCase());
    }

}
