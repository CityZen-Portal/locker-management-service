package com.cityzen.lockermanagementservice.service;

import com.cityzen.lockermanagementservice.dto.FileReponse;
import com.cityzen.lockermanagementservice.dto.FileUploadDto;
import com.cityzen.lockermanagementservice.entity.File;
import com.cityzen.lockermanagementservice.entity.Locker;
import com.cityzen.lockermanagementservice.repository.LockerRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LockerService {

    private final LockerRepo lockerRepo;

    public LockerService(LockerRepo lockerRepo) {
        this.lockerRepo = lockerRepo;
    }

    @Transactional(readOnly = true)
    public List<File> getList(String aadharNumber) {
        Optional<Locker> locker = lockerRepo.findByAadharNumber(aadharNumber);
        if(locker.isEmpty()){
            return new ArrayList<>();
        }
        return locker.get().getFiles();
    }

    public FileReponse addDocument(FileUploadDto dto) {
        Locker locker = lockerRepo.findByAadharNumber(dto.getAadharNumber()).orElseGet(() -> {
            Locker newLocker = new Locker();
            newLocker.setAadharNumber(dto.getAadharNumber());
            return lockerRepo.save(newLocker);
        });

        File file = File.builder()
                .fileName(dto.getFileName())
                .filePath(dto.getFilePath())
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

    public FileReponse updateDocument(FileUploadDto dto) {
        Locker locker = lockerRepo.findByAadharNumber(dto.getAadharNumber())
                .orElseThrow(() -> new EntityNotFoundException("No locker found for Aadhar: " + dto.getAadharNumber()));

        if (dto.getFileId() == null) {
            throw new IllegalArgumentException("fileId is required to update a document");
        }

        File file = locker.getFiles().stream()
                .filter(f -> f.getFileId().equals(dto.getFileId()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No file found with id: " + dto.getFileId()));

        if (dto.getFileName() != null && !dto.getFileName().isBlank()) {
            file.setFileName(dto.getFileName());
        }
        if (dto.getFilePath() != null && !dto.getFilePath().isBlank()) {
            file.setFilePath(dto.getFilePath());
        }

        lockerRepo.save(locker);

        return FileReponse.builder()
                .id(file.getFileId())
                .fileName(file.getFileName())
                .filePath(file.getFilePath())
                .creationDate(file.getCreationDate())
                .build();
    }

    public void deleteDocument(String aadharNumber, String fileId) {
        Locker locker = lockerRepo.findByAadharNumber(aadharNumber)
                .orElseThrow(() -> new EntityNotFoundException("No locker found for Aadhar: " + aadharNumber));

        boolean removed = false;
        Iterator<File> it = locker.getFiles().iterator();
        while (it.hasNext()) {
            File f = it.next();
            if (f.getFileId().equals(fileId)) {
                it.remove();
                removed = true;
                break;
            }
        }

        if (!removed) {
            throw new EntityNotFoundException("No file found with id: " + fileId);
        }

        lockerRepo.save(locker);
    }
}
