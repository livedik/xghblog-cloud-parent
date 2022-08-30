package com.xghblog.services.demoSpring.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Users {

    private Integer id;
    private String username;
    private String password;
}
