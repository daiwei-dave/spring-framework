package com.roger.core.model;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Long id;
    private String userName;
    private Integer age;
    private String phone;
    private Date createdTime;
    private Date updatedTime;
}
