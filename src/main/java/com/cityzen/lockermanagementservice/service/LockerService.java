package com.cityzen.lockermanagementservice.service;


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
    private LockerRepo lockerRepo;


    public List<File> getList(String aadharNumber) {
        Locker locker = lockerRepo.findByAadharNumber(aadharNumber).orElse(null);
        return locker.getFiles();
    }

    public File addDocument(MultipartFile file, String aadharNumber) {
        Locker locker = lockerRepo.findByAadharNumber(aadharNumber).orElseGet(() -> {
            Locker newLocker = new Locker();
            newLocker.setAadharNumber(aadharNumber);
            return lockerRepo.save(newLocker);
        });
        File uploadedFile = null;
        locker.getFiles().add(uploadedFile);
        lockerRepo.save(locker);
        return uploadedFile;
    }



    public File updateDocument(MultipartFile file, String aadharNumber, Long fileId) {
        Locker locker = lockerRepo.findByAadharNumber(aadharNumber).orElse(null);
        if(locker == null){
            return null;
        }
        File targetFile = null;
        for(File f : locker.getFiles()){
            if(f.getId().equals(fileId)){
                targetFile = f;
                break;
            }
        }

        if (targetFile == null) {
            throw new RuntimeException("File not found");
        }

        File uploadFile = null;
        targetFile.setFileName(uploadFile.getFileName());
        targetFile.setFilePath(uploadFile.getFilePath());
        targetFile.setUploadedAt(Instant.now());
        lockerRepo.save(locker);

        return targetFile;

    }


    public void deletedDocument(String aadharNumber, Long fileId) {
        Locker locker = lockerRepo.findByAadharNumber(aadharNumber)
                .orElseThrow(() -> new RuntimeException("Locker not found"));

        for (int i = 0; i < locker.getFiles().size(); i++) {
            if (locker.getFiles().get(i).getId().equals(fileId)) {
                locker.getFiles().remove(i);
                lockerRepo.save(locker);
                return;
            }
        }
        throw new RuntimeException("File not found");
    }
}
