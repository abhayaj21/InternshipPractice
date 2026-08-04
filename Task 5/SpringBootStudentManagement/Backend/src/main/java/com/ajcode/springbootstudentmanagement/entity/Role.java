package com.ajcode.springbootstudentmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name",nullable = false,length = 100)
    private String roleName;

    @Column(name = "description",nullable = false,length = 250)
    private String description;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private List<UserModel> users = new ArrayList<>();

    public Role(String roleName, String description) {
        this.roleName = roleName;
        this.description = description;
    }

    public Role() {

    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
