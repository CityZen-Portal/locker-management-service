package com.cityzen.lockermanagementservice.dto;


import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class FileReponse {
    String id;
    String fileName;
    String filePath;
    Instant creationDate;
}
