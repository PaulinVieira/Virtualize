package com.paulotec.virtualize.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;


@Data
public class table_Usuarios {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_usuario;
    private String username;
    private String password;
    private String email;
    private String role;
    private int ativo = 1;
}
