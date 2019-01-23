package test.api.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import test.api.data.Image;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "images")
@Entity
public class ImageAnnotated implements Serializable {

    @Id
    @Column(name="IID")
    private Long id;
    @Column(name="Path")
    private String path;
}
