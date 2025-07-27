package com.cityzen.lockermanagementservice.clients;


import com.cityzen.lockermanagementservice.payload.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "UserManagementService", url = "${user.service.url}")
public interface UserInterface {

    @GetMapping("/api/auth/userInfo/{aadharNumber}")
    ResponseEntity<ApiResponse<?>> getUserByAadharNumber(@PathVariable("aadharNumber") String aadharNumber);

}
