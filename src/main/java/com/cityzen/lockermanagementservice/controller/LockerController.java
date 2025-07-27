package com.cityzen.lockermanagementservice.controller;

import com.cityzen.lockermanagementservice.dto.FileUploadDto;
import com.cityzen.lockermanagementservice.entity.File;
import com.cityzen.lockermanagementservice.entity.Locker;
import com.cityzen.lockermanagementservice.payload.CommonResponse;
import com.cityzen.lockermanagementservice.payload.Status;
import com.cityzen.lockermanagementservice.service.LockerService;
import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("/list-documents")
    public ResponseEntity<CommonResponse<?>> findAll(@PathVariable String aadharNumber, HttpServletRequest request) {
        try {
            if (aadharNumber == null || aadharNumber.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new CommonResponse<>(Status.FAILED, null, "INVALID AADHAR", request.getRequestURI()));
            }
            List<File> listFiles = lockerService.getList(aadharNumber);
            if (listFiles == null || listFiles.isEmpty()) {
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(new CommonResponse<>(Status.ACCEPTED, new ArrayList<>(), "DOCUMENTS FILE :" + 0, request.getRequestURI()));
            }

            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new CommonResponse<>(Status.ACCEPTED, listFiles, "SUCCESS", request.getRequestURI()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CommonResponse<>(Status.REJECTED, e, "INTERNAL_SERVER_ERROR :" + e.getMessage(), request.getRequestURI()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<CommonResponse<?>> addDocument(@RequestPart MultipartFile file, @PathVariable String aadharNumber) {
        String path = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new CommonResponse<>(Status.FAILED, null, "FILE IS EMPTY", path));
            }
            File saved = lockerService.addDocument(file, aadharNumber);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CommonResponse<>(Status.ACCEPTED, saved, "FILE UPLOADED", path));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CommonResponse<>(Status.REJECTED, e, "INTERNAL_SERVER_ERROR :" + e.getMessage(), path));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<CommonResponse<?>> updateDocument(@RequestPart MultipartFile file, @PathVariable String aadharNumber, @PathVariable Long fileId) {
        String path = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new CommonResponse<>(Status.FAILED, null, "FILE IS EMPTY", path));
            }
            File updated = lockerService.updateDocument(file, aadharNumber, fileId);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new CommonResponse<>(Status.ACCEPTED, updated, "FILE UPDATED", path));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CommonResponse<>(Status.REJECTED, e, "INTERNAL_SERVER_ERROR :" + e.getMessage(), path));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CommonResponse<?>> deleteDocument(@PathVariable String aadharNumber, @PathVariable Long fileId) {
        String path = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
        try {
            lockerService.deletedDocument(aadharNumber, fileId);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new CommonResponse<>(Status.ACCEPTED, null, "Deleted successfully", path));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CommonResponse<>(Status.REJECTED, e, "INTERNAL_SERVER_ERROR :" + e.getMessage(), path));
        }
    }
}
