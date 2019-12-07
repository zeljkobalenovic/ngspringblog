package zeljko.ngspringblog.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * Post
 */

@Entity
@Data
 public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String title;
    @Lob
    private String content;
    private Instant createdOn;
//  ovako bi se postavila default vrednost ovog polja u mysql bazi pa ako se bilo kako updejtuje post automatski ce biti upisano tekuce vreme
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant updatedOn;
    @NotBlank
    private String userName;
}