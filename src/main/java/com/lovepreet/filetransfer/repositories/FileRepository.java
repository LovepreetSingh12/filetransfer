package com.lovepreet.filetransfer.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lovepreet.filetransfer.models.FileDocument;

public interface FileRepository extends MongoRepository<FileDocument, String>{

}
