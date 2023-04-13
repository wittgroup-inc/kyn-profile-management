package com.gowittgroup.kyn.profile.client;

import com.gowittgroup.kyn.profile.models.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "address-management")
public interface AddressClient {
    @GetMapping("/api/address/{id}")
    Address getAddressById(@PathVariable String id);

    @PostMapping("/api/address")
    Address createAddress(@RequestBody Address address);

    @PutMapping("/api/address/{id}")
    ResponseEntity<Void> updateAddress(@PathVariable String id, @RequestBody Address address);
}
