package com.wittgroup.kyn.profile.client;

import com.wittgroup.kyn.profile.models.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "address-management")
public interface AddressClient {
    @GetMapping("/api/address/{id}")
    Address getAddressById(@PathVariable String id);

    @PostMapping("/api/address")
    Address createAddress(@RequestBody Address address);

}
