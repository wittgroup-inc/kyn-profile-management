package com.gowittgroup.kyn.profile.api;

import com.gowittgroup.kyn.profile.models.Profile;
import com.gowittgroup.kyn.profile.models.User;
import com.gowittgroup.kyn.profile.services.ProfileService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


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
    public ResponseEntity<UUID> createUser(@RequestBody @Valid final User user) {
        return new ResponseEntity<>(profileService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/loadUser/{q}")
    public ResponseEntity<User> loadUser(@PathVariable final String q) {
        return ResponseEntity.ok(profileService.findUserByUsernameOrEmailOrMobileNumber(q));
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
