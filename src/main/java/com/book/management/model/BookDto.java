package com.book.management.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String author;

    @NotNull
    private Integer price;
}
