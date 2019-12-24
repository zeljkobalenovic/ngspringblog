package zeljko.ngspringblog.model;

import lombok.Data;

/**
 * PostDto
 */

@Data
 public class PostDto {

    private Long id;
    private String title;
    private String content;
    private String userName;

}