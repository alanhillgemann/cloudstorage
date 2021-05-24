package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) { this.fileMapper = fileMapper; }

    public boolean isFilenameAvailable(String username, Integer userId) {
        return fileMapper.getFileByName(username, userId) == null;
    }

    public ArrayList<File> getFiles(Integer userId) { return fileMapper.getFiles(userId); }

    public File getFile(Integer fileId) { return fileMapper.getFileById(fileId); }

    public Integer createFile(MultipartFile file, Integer userId) throws IOException {
        File newFile = new File();
        newFile.setFilename(file.getOriginalFilename());
        newFile.setContentType(file.getContentType());
        newFile.setFileData(file.getBytes());
        newFile.setFileSize(file.getSize());
        newFile.setUserId(userId);
        fileMapper.insert(newFile);
        return newFile.getFileId();
    }

    public void deleteFile(Integer fileId) { fileMapper.delete(fileId); }
}
