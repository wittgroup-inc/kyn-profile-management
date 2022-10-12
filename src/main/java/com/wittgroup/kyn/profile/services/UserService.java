package com.wittgroup.kyn.profile.services;

import com.wittgroup.kyn.profile.client.AddressClient;
import com.wittgroup.kyn.profile.db.repositories.UserRepository;
import com.wittgroup.kyn.profile.models.User;
import com.wittgroup.kyn.profile.models.Address;

import java.util.*;
import java.util.stream.Collectors;

import com.wittgroup.kyn.profile.db.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UserService {

    private UserRepository userRepository;
    private AddressClient addressClient;


    public UserService(final UserRepository userRepository,
                       final AddressClient addressClient) {
        this.userRepository = userRepository;
        this.addressClient = addressClient;
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
        return addressClient.getAddressById(uuid.toString());
    }

    private Address createAddress(Address address) {
        Address found = findAddressById(address.getId());
        if (found != null)
            return found;
        return addressClient.createAddress(address);
    }

}
