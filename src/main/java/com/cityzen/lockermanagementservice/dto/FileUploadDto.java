package com.cityzen.lockermanagementservice.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadDto {

    @NotBlank
    @Pattern(regexp = "^\\d{12}$", message = "Aadhar must be 12 digits")
    private String aadharNumber;

    @NotBlank
    private String fileName;
    @NotBlank
    private String filePath;
}
