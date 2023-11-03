package com.work.ecart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse <T>{
    T data;
    String message;
    boolean isSuccess;
}
