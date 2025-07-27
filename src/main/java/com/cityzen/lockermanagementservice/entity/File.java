package com.cityzen.lockermanagementservice.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class File {
        private String fileId = UUID.randomUUID().toString();
        private String fileName;
        private String filePath;
        private Instant creationDate;
}
