package com.example.crudmysql.model;

import org.springframework.data.repository.CrudRepository;

import com.example.crudmysql.model.User;

// Esta interface e AUTO IMPLEMENTADA pelo Spring no Bean chamadado de UserRepository
//Faz toda a comunicacao e alteracao (CRUD - Create, Read, Update, Delete) no banco de dados 

@SuppressWarnings("unused")
public interface UserRepository extends CrudRepository<User, Integer> {

}