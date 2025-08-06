package com.cityzen.lockermanagementservice.clients;


import com.cityzen.lockermanagementservice.dto.TokenResponseDto;
import com.cityzen.lockermanagementservice.payload.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "UserManagementService", url = "https://auth-backend-2-k3ph.onrender.com")
public interface UserInterface {

    @GetMapping("/api/auth/userInfo/{aadharNumber}")
    ResponseEntity<ApiResponse<?>> getUserByAadharNumber(@PathVariable("aadharNumber") String aadharNumber);
//    @GetMapping("/api/auth/validate")
//    ResponseEntity<TokenResponseDto>  validateUser(@RequestHeader("token")  String token);

}
