package com.dddryinside.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
@NoArgsConstructor
public class UpdateProfileRequest {
    private String name;
    private String about;
    private MultipartFile avatar;
}
