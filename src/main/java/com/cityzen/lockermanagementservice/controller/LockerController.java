package com.cityzen.lockermanagementservice.controller;

import com.cityzen.lockermanagementservice.clients.UserInterface;
import com.cityzen.lockermanagementservice.dto.FileReponse;
import com.cityzen.lockermanagementservice.dto.FileUploadDto;
import com.cityzen.lockermanagementservice.dto.TokenResponseDto;
import com.cityzen.lockermanagementservice.entity.File;
import com.cityzen.lockermanagementservice.payload.ApiResponse;
import com.cityzen.lockermanagementservice.payload.CommonResponse;
import com.cityzen.lockermanagementservice.payload.Status;
import com.cityzen.lockermanagementservice.service.LockerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;



@RestController
@RequestMapping("/api/lock")
public class LockerController {

    @Autowired
    private  LockerService lockerService;

    @Autowired
    private  UserInterface userInterface;

    @PostMapping("/add")
    public ResponseEntity<CommonResponse<?>> addDocument(@RequestHeader("token") String token, @RequestBody FileUploadDto fileUploadDto,
                                                         HttpServletRequest request) {
        try {

            FileReponse fileResponse = lockerService.addDocument(fileUploadDto);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new CommonResponse<>(Status.ACCEPTED, fileResponse, "FILE SAVED SUCCESSFULLY", request.getRequestURI()));

        } catch (EntityNotFoundException | IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new CommonResponse<>(Status.FAILED, null, ex.getMessage(), request.getRequestURI()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CommonResponse<>(Status.REJECTED, null, "INTERNAL_SERVER_ERROR : " + e.getMessage(), request.getRequestURI()));
        }
    }

    @GetMapping("/listDocument/{aadharNumber}")
    public ResponseEntity<CommonResponse<?>> listDocument( @RequestHeader("token") String auth, @PathVariable("aadharNumber") String aadharNumber,
                                                          HttpServletRequest request) {
        try {

            List<File> files = lockerService.getList(aadharNumber);
            return ResponseEntity.ok(new CommonResponse<>(Status.ACCEPTED, files, "FILE LIST SUCCESSFULLY", request.getRequestURI()));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CommonResponse<>(Status.FAILED, null, ex.getMessage(), request.getRequestURI()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CommonResponse<>(Status.REJECTED, null, "INTERNAL_SERVER_ERROR : " + e.getMessage(), request.getRequestURI()));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<CommonResponse<?>> updateDocument(@RequestHeader("token") String token, @RequestBody FileUploadDto fileUploadDto,
                                                            HttpServletRequest request) {
        try {

            FileReponse file = lockerService.updateDocument(fileUploadDto);
            return ResponseEntity.ok(new CommonResponse<>(Status.ACCEPTED, file, "FILE UPDATED SUCCESSFULLY", request.getRequestURI()));
        } catch (EntityNotFoundException | IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new CommonResponse<>(Status.FAILED, null, ex.getMessage(), request.getRequestURI()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CommonResponse<>(Status.REJECTED, null, "INTERNAL SERVER ERROR : " + e.getMessage(), request.getRequestURI()));
        }
    }

    @DeleteMapping("/delete/{aadharNumber}/{fileId}")
    public ResponseEntity<CommonResponse<?>> deleteDocument (@RequestHeader("token") String token ,@PathVariable String aadharNumber,
                                                            @PathVariable String fileId,
                                                            HttpServletRequest request) {
        try {

            lockerService.deleteDocument(aadharNumber, fileId);
            return ResponseEntity.ok(new CommonResponse<>(Status.ACCEPTED, null, "FILE DELETED SUCCESSFULLY", request.getRequestURI()));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CommonResponse<>(Status.FAILED, null, ex.getMessage(), request.getRequestURI()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CommonResponse<>(Status.REJECTED, null, "INTERNAL SERVER ERROR : " + e.getMessage(), request.getRequestURI()));
        }
    }




    private void validateAadharWithUserService(String aadharNumber, HttpServletRequest request) {
        Long aadhar = Long.parseLong(aadharNumber);
        ApiResponse<?> resp = userInterface.getUserByAadharNumber(Long.toString(aadhar)).getBody();

        if (resp == null) {
            throw new IllegalStateException("User service returned NULL");
        }

        if (!(resp.getData() instanceof Boolean exist) || !exist) {
            throw new javax.persistence.EntityNotFoundException("AADHAR NOT FOUND");
        }
    }

}
