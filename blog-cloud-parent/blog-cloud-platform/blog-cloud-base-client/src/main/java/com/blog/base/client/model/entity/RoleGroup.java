package com.blog.base.client.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RoleGroup {

    private String id;

    private String label;

    private String roleCode;

    private List<Role> children = new ArrayList<>();

}
