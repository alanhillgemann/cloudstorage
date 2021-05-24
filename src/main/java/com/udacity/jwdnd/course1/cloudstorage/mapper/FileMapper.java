package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface FileMapper {
    @Results(id = "fileResultMap", value = {
            @Result(property = "fileId", column = "fileid"),
            @Result(property = "filename", column = "filename"),
            @Result(property = "contentType", column = "contenttype"),
            @Result(property = "fileSize", column = "filesize"),
            @Result(property = "userId", column = "userid"),
            @Result(property = "fileData", column = "filedata")
    })
    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    ArrayList<File> getFiles(Integer userId);

    @ResultMap("fileResultMap")
    @Select("SELECT * FROM FILES WHERE (filename = #{filename} AND userId = #{userId})")
    File getFileByName(String filename, Integer userId);

    @ResultMap("fileResultMap")
    @Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
    File getFileById(Integer fileId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    void insert(File file);

    @Delete("DELETE FILES WHERE fileid = #{fileId}")
    void delete(Integer fileId);
}
