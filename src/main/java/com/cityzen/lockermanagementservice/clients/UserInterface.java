package com.cityzen.lockermanagementservice.clients;


import com.cityzen.lockermanagementservice.payload.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "UserManagementService")
public interface UserInterface {


    @GetMapping("/{aadhar-number}")
     ResponseEntity<ApiResponse<?>> getUserByAadharNumber(@PathVariable("aadhar-number") String aadharNumber);

}
