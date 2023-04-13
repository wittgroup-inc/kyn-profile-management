package com.gowittgroup.kyn.profile.services;

import com.gowittgroup.kyn.profile.client.AddressClient;
import com.gowittgroup.kyn.profile.db.entities.ProfileEntity;
import com.gowittgroup.kyn.profile.db.repositories.ProfileRepository;
import com.gowittgroup.kyn.profile.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final AddressClient addressClient;
    private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;


    public List<Profile> findAll() {
        return profileRepository.findAll(Sort.by("id"))
                .stream()
                .map(this::mapToProfile)
                .collect(Collectors.toList());
    }

    public Profile get(final UUID id) {
        return profileRepository.findById(id)
                .map(this::mapToProfile)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public UUID create(final Profile profile) {
        return profileRepository.save(mapToProfileEntity(profile, new ProfileEntity())).getId();
    }

    public UUID createUser(User user) {
        isUserAlreadyExists(user.getUserName(), user.getEmail(), user.getMobileNumber());
        Settings settings = new Settings(getValidUserName(IdGenerator.generateUsername()), user.getPassword(), user.getEmail());
        Profile profile = new Profile(user.getFirstName(), user.getLastName(), user.getDob(), user.getSex(), Privacy.DEFAULT, settings);
        return mapToProfile(profileRepository.save(mapToProfileEntity(profile, new ProfileEntity()))).getId();
    }

    private String getValidUserName(String username) {
        Optional<ProfileEntity> user = profileRepository.findBySettingsUsername(username);
        if (user.isPresent()) {
            getValidUserName(IdGenerator.generateUsername());
        }
        return username;
    }

    private boolean isUserAlreadyExists(String username, String email, String mobileNumber) throws ResponseStatusException {
        Optional<ProfileEntity> user;
        if (username != null && mobileNumber != null) {
            user = profileRepository.findBySettingsUsernameOrSettingsPrimaryEmailOrSettingsRegisteredMobileNumber(username, email, mobileNumber);
            if (user.isPresent()) {
                String reason = (user.get().getSettings().getUsername().equals(username) ? "Username: " + username + ", " : "") +
                        (user.get().getSettings().getPrimaryEmail().equals(email) ? "Email: " + email + " and " : "") +
                        (user.get().getSettings().getRegisteredMobileNumber().equals(mobileNumber) ? "Mobile Number: " + mobileNumber + " " : "") +
                        "already registered with us.";
                throw new ResponseStatusException(HttpStatus.CONFLICT, reason);
            }
        } else if (username == null && mobileNumber != null) {
            user = profileRepository.findBySettingsPrimaryEmailOrSettingsRegisteredMobileNumber(email, mobileNumber);

            if (user.isPresent()) {
                String reason = (user.get().getSettings().getPrimaryEmail().equals(email) ? "Email: " + email + " and " : "") +
                        (user.get().getSettings().getRegisteredMobileNumber().equals(mobileNumber) ? "Mobile Number: " + mobileNumber + " " : "") +
                        "are already registered with us.";
                throw new ResponseStatusException(HttpStatus.CONFLICT, reason);
            }
        } else if (username != null) {
            user = profileRepository.findBySettingsUsernameOrSettingsPrimaryEmail(username, email);
            if (user.isPresent()) {
                String reason = (user.get().getSettings().getUsername().equals(username) ? "Username: " + username + " and " : "") +
                        (user.get().getSettings().getPrimaryEmail().equals(email) ? "Email: " + email + " " : "") +
                        "are already registered with us.";
                throw new ResponseStatusException(HttpStatus.CONFLICT, reason);
            }
        } else {
            user = profileRepository.findBySettingsPrimaryEmail(email);
            if (user.isPresent()) {
                String reason = (user.get().getSettings().getPrimaryEmail().equals(email) ? "Email: " + email + " " : "") +
                        "already registered with us.";
                throw new ResponseStatusException(HttpStatus.CONFLICT, reason);
            }
        }

        return false;
    }

    private boolean isUserExists(String username, String email, String mobileNumber) {
        try {
            return isUserAlreadyExists(username, email, mobileNumber);
        } catch (ResponseStatusException e) {
            return true;
        }
    }

    public void update(final UUID id, final Profile profile) {
        final ProfileEntity entity = profileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToProfileEntity(profile, entity);
        profileRepository.save(entity);
    }

    public void delete(final UUID id) {
        profileRepository.deleteById(id);
    }

    public User findUserByUsernameOrEmailOrMobileNumber(String q) {
        return profileRepository.findBySettingsUsernameOrSettingsPrimaryEmailOrSettingsRegisteredMobileNumber(q, q, q).map(this::toUser).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private User toUser(ProfileEntity entity) {
        User user = new User(entity.getFirstName(), entity.getLastName(), entity.getDob(), entity.getSex(), entity.getSettings().getPrimaryEmail(), entity.getSettings().getPassword());
        user.setId(entity.getId());
        user.setUserName(entity.getSettings().getUsername());
        user.setMobileNumber(entity.getSettings().getRegisteredMobileNumber());
        return user;
    }

    private Profile mapToProfile(final ProfileEntity entity) {
        Profile profile = new Profile(entity.getFirstName(), entity.getLastName(), entity.getDob(), entity.getSex(), entity.getPrivacy(), entity.getSettings());
        profile.setId(entity.getId());
        profile.setProfilePicUrl(entity.getProfilePicUrl());
        profile.setSex(entity.getSex());
        if (entity.getAddresses() != null) {
            profile.setAddress(entity.getAddresses().isEmpty() ? List.of() : entity.getAddresses().stream().map(this::findAddressById).toList());
        }
        profile.setPrivacy(entity.getPrivacy());
        profile.setSettings(entity.getSettings());
        profile.setContact(entity.getContact());
        return profile;
    }

    private ProfileEntity mapToProfileEntity(final Profile profile, final ProfileEntity entity) {
        entity.setId(profile.getId());
        entity.setFirstName(profile.getFirstName());
        entity.setLastName(profile.getLastName());
        entity.setDob(profile.getDob());
        entity.setProfilePicUrl(profile.getProfilePicUrl());
        entity.setSex(profile.getSex());
        if (profile.getAddress() != null) {
            entity.setAddresses(profile.getAddress().stream().map(this::createAddress).map(Address::getId).toList());
        }
        entity.setPrivacy(profile.getPrivacy());
        entity.setSettings(profile.getSettings());
        entity.setContact(profile.getContact());
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
