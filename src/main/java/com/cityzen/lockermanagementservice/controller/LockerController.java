package com.cityzen.lockermanagementservice.controller;

import com.cityzen.lockermanagementservice.clients.UserInterface;
import com.cityzen.lockermanagementservice.dto.FileReponse;
import com.cityzen.lockermanagementservice.dto.FileUploadDto;
import com.cityzen.lockermanagementservice.entity.File;
import com.cityzen.lockermanagementservice.entity.Locker;
import com.cityzen.lockermanagementservice.payload.ApiResponse;
import com.cityzen.lockermanagementservice.payload.CommonResponse;
import com.cityzen.lockermanagementservice.payload.Status;
import com.cityzen.lockermanagementservice.service.LockerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Path;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/lock/")
public class LockerController {
    @Autowired
    private LockerService lockerService;

    @Autowired
    private UserInterface userInterface;

    @PostMapping("/add")
    public ResponseEntity<CommonResponse<?>> addDocument( @RequestBody FileUploadDto fileUploadDto, HttpServletRequest request) {
        try {


            Long aadharNumber = Long.parseLong(fileUploadDto.getAadharNumber());
            ApiResponse<?> aadharExist = userInterface.getUserByAadharNumber(Long.toString(aadharNumber)).getBody();
            if(aadharExist == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CommonResponse<>(Status.FAILED, aadharExist.getData(), aadharExist.getMessage(), request.getRequestURI()));
            }

            if(!(boolean)aadharExist.getData()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonResponse<>(Status.FAILED, aadharExist.getData(), "NOT FOUND", request.getRequestURI()));
            }

            FileReponse fileResponse = lockerService.addDocument(fileUploadDto);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new CommonResponse<>(Status.ACCEPTED, fileResponse, "FILE SAVED SUCCESSFULLY", request.getRequestURI()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CommonResponse<>(Status.REJECTED, e.getMessage(), "INTERNAL_SERVER_ERROR :" + e.getMessage(), request.getRequestURI()));
        }
    }


    @GetMapping("/listDocument/{aadharNumber}")
    public ResponseEntity<CommonResponse<?>> listDocument(@PathVariable("aadharNumber") String aadharNumber, HttpServletRequest request) {
        try {
            List<File> file = lockerService.getList(aadharNumber);
            return ResponseEntity.ok(new CommonResponse<>(Status.ACCEPTED, file, "FILE LIST SUCCESSFULLY", request.getRequestURI()));

        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CommonResponse<>(Status.REJECTED, e, "INTERNAL_SERVER_ERROR :" + e.getMessage(), request.getRequestURI()));
        }
    }


}
