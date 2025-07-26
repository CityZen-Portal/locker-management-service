package com.cityzen.lockermanagementservice.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class File {

        private String id;
        private String fileName;
        private String filePath;
        private Instant uploadedAt;

}
