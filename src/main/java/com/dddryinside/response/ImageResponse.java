package com.dddryinside.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageResponse {
    private Long id;
    private String title;
    private String description;
    private String url;
    private Long likesAmount;
    private String postDateTime;
    private String updateDateTime;
}
