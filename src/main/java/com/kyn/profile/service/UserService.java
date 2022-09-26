package com.kyn.profile.service;

import com.kyn.profile.domain.*;
import com.kyn.profile.model.UserDTO;
import com.kyn.profile.repos.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UserService {
    private final UserRepository userRepository;

    private RestTemplate restTemplate;

    public UserService(final UserRepository userRepository,
                       final RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll(Sort.by("id"))
                .stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .collect(Collectors.toList());
    }

    public UserDTO get(final UUID id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public UUID create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final UUID id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final UUID id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
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

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setProfilePicUrl(userDTO.getProfilePicUrl());
        user.setSex(userDTO.getSex());
        user.setAddresses(userDTO.getAddress().stream().map(this::createAddress).map(Address::getId).toList());
        user.setPrivacy(userDTO.getPrivacy());
        user.setSettings(userDTO.getSettings());
        user.setContact(userDTO.getContact());
        return user;
    }


    private Address findAddressById(UUID uuid) throws ResponseStatusException {
        return restTemplate.getForObject("http://localhost:8082/api/address/" + uuid, Address.class);
    }

    private Address createAddress(Address address) {
        Address found = findAddressById(address.getId());
        if (found != null)
            return found;
        return restTemplate.postForObject("http://localhost:8082/api/address", address, Address.class);
    }

}
