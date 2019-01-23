package test.api.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import test.api.data.Image;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagePOJO implements Image {

    private Long id;
    private String path;
}
