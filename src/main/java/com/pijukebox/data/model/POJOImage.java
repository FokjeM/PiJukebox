package com.pijukebox.data.model;

import com.pijukebox.data.InterImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class POJOImage implements InterImage {
    private Long id;
    private String path;
}
