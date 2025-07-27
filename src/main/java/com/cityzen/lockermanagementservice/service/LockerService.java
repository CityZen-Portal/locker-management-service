package com.cityzen.lockermanagementservice.service;


import com.cityzen.lockermanagementservice.dto.FileReponse;
import com.cityzen.lockermanagementservice.dto.FileUploadDto;
import com.cityzen.lockermanagementservice.entity.File;
import com.cityzen.lockermanagementservice.entity.Locker;
import com.cityzen.lockermanagementservice.repository.LockerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;

@Service
public class LockerService {

    @Autowired
    private  LockerRepo lockerRepo;


    public List<File> getList(String aadharNumber) {
        Locker locker = lockerRepo.findByAadharNumber(aadharNumber).orElse(null);
        return locker.getFiles();
    }



    public FileReponse addDocument(FileUploadDto userLocker) {
        Locker locker = lockerRepo.findByAadharNumber(userLocker.getAadharNumber()).orElseGet(() -> {
            Locker newLocker = new Locker();
            newLocker.setAadharNumber(userLocker.getAadharNumber());
            return lockerRepo.save(newLocker);
        });
        File file = File.builder()
                .fileName(userLocker.getFileName())
                .filePath(userLocker.getFilePath())
                .creationDate(Instant.now())
                .build();

        locker.getFiles().add(file);
        lockerRepo.save(locker);
        return FileReponse.builder()
                .id(file.getFileId())
                .fileName(file.getFileName())
                .filePath(file.getFilePath())
                .creationDate(file.getCreationDate())
                .build();
    }

}
