package com.lovepreet.filetransfer.services;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class FileService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    /**
     * Uploads a file to GridFS.
     *
     * @param file the file to upload
     * @return the file ID
     * @throws Exception if an error occurs
     */
    public String uploadFile(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        String fileId = gridFsTemplate.store(inputStream, file.getOriginalFilename(), file.getContentType()).toString();
        inputStream.close();
        return fileId;
    }

    /**
     * Downloads a file from GridFS using its ID.
     *
     * @param id the file ID
     * @return the GridFsResource for the file
     * @throws Exception if the file is not found
     */
    public GridFsResource downloadFile(String id) throws Exception {
        // Query the GridFS metadata for the file with the given ID
        GridFSFile gridFsFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(id)));

        if (gridFsFile == null) {
            throw new Exception("File not found with ID: " + id);
        }

        return gridFsTemplate.getResource(gridFsFile);
    }
}