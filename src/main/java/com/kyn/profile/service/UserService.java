package com.kyn.profile.service;

import com.kyn.profile.domain.Address;
import com.kyn.profile.domain.Contact;
import com.kyn.profile.domain.Privacy;
import com.kyn.profile.domain.Settings;
import com.kyn.profile.domain.User;
import com.kyn.profile.model.UserDTO;
import com.kyn.profile.repos.AddressRepository;
import com.kyn.profile.repos.ContactRepository;
import com.kyn.profile.repos.PrivacyRepository;
import com.kyn.profile.repos.SettingsRepository;
import com.kyn.profile.repos.UserRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PrivacyRepository privacyRepository;
    private final SettingsRepository settingsRepository;
    private final ContactRepository contactRepository;

    public UserService(final UserRepository userRepository,
            final AddressRepository addressRepository, final PrivacyRepository privacyRepository,
            final SettingsRepository settingsRepository,
            final ContactRepository contactRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.privacyRepository = privacyRepository;
        this.settingsRepository = settingsRepository;
        this.contactRepository = contactRepository;
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
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setProfilePicUrl(user.getProfilePicUrl());
        userDTO.setSex(user.getSex());
        userDTO.setAddress(user.getAddresses() == null ? null : user.getAddresses().stream()
                .map(address -> address.getId())
                .collect(Collectors.toList()));
        userDTO.setPrivacy(user.getPrivacy() == null ? null : user.getPrivacy().getId());
        userDTO.setSettings(user.getSettings() == null ? null : user.getSettings().getId());
        userDTO.setContact(user.getContact() == null ? null : user.getContact().getId());
        return userDTO;
    }

    private <T> List<T> iterableToList(final Iterable<T> iterable) {
        final List<T> list = new ArrayList<T>();
        iterable.forEach(item -> list.add(item));
        return list;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setProfilePicUrl(userDTO.getProfilePicUrl());
        user.setSex(userDTO.getSex());
        final List<Address> address = iterableToList(addressRepository.findAllById(
                userDTO.getAddress() == null ? Collections.emptyList() : userDTO.getAddress()));
        if (address.size() != (userDTO.getAddress() == null ? 0 : userDTO.getAddress().size())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "one of address not found");
        }
        user.setAddresses(address.stream().collect(Collectors.toSet()));
        final Privacy privacy = userDTO.getPrivacy() == null ? null : privacyRepository.findById(userDTO.getPrivacy())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "privacy not found"));
        user.setPrivacy(privacy);
        final Settings settings = userDTO.getSettings() == null ? null : settingsRepository.findById(userDTO.getSettings())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "settings not found"));
        user.setSettings(settings);
        final Contact contact = userDTO.getContact() == null ? null : contactRepository.findById(userDTO.getContact())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "contact not found"));
        user.setContact(contact);
        return user;
    }

}
