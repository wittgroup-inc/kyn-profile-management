package com.wittgroup.kyn.profile.api;

import com.wittgroup.kyn.profile.models.Profile;
import com.wittgroup.kyn.profile.models.User;
import com.wittgroup.kyn.profile.services.ProfileService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProfileResource {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<List<Profile>> getAllProfile() {
        return ResponseEntity.ok(profileService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfile(@PathVariable final UUID id) {
        return ResponseEntity.ok(profileService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createProfile(@RequestBody @Valid final Profile profile) {
        return new ResponseEntity<>(profileService.create(profile), HttpStatus.CREATED);
    }

    @PostMapping("/createUser")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Profile> createUser(@RequestBody @Valid final User user) {
        return new ResponseEntity<>(profileService.createUser(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProfile(@PathVariable final UUID id,
                                              @RequestBody @Valid final Profile profileDTO) {
        profileService.update(id, profileDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteProfile(@PathVariable final UUID id) {
        profileService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
