package com.cityzen.lockermanagementservice.controller;


import com.cityzen.lockermanagementservice.dto.FileUploadDto;
import com.cityzen.lockermanagementservice.entity.File;
import com.cityzen.lockermanagementservice.entity.Locker;
import com.cityzen.lockermanagementservice.service.LockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/lock/")
public class LockerController {
    @Autowired
    private LockerService lockerService;


    @GetMapping("/list-documents")
    public List<File> findAll(@PathVariable String aadharNumber) {
        return lockerService.getList(aadharNumber);
    }


    @PostMapping("/add")
    public File addDocument(@RequestPart MultipartFile file, @PathVariable String aadharNumber) {
        return lockerService.addDocument(file, aadharNumber);
    }


    @PutMapping("/update")
    public File updateDocument(@RequestPart MultipartFile file, @PathVariable String aadharNumber, @PathVariable Long fileId ) {
        return lockerService.updateDocument(file, aadharNumber,  fileId);
    }

    @DeleteMapping("/delete")
    public File deleteDocument(@PathVariable String aadharNumber, @PathVariable Long fileId) {
        return lockerService.deletedDocument(aadharNumber, fileId);
    }


}
