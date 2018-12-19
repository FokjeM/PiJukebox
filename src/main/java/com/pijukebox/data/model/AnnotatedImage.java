package com.pijukebox.data.model;

import com.pijukebox.data.InterImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="images")
public class AnnotatedImage implements InterImage {
    @Id
    @Column(name="IID")
    private Long id;

    @Column(name="Path")
    private String path;
}
