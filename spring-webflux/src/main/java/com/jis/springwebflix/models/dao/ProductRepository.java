package com.jis.springwebflix.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.jis.springwebflix.models.documents.Producto;

public interface ProductRepository extends ReactiveMongoRepository<Producto, String>{

}
