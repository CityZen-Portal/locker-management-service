package com.cityzen.lockermanagementservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadDto {
    private String fileName;
    private String filePath;
}
