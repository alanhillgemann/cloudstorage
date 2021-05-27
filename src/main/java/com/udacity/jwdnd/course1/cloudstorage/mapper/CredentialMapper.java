package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface CredentialMapper {

    @Delete("DELETE CREDENTIALS WHERE credentialid = #{credentialId}")
    void delete(Integer credentialId);

    @Results(id = "credentialResultMap", value = {
            @Result(property = "credentialId", column = "credentialid"),
            @Result(property = "url", column = "url"),
            @Result(property = "username", column = "username"),
            @Result(property = "key", column = "key"),
            @Result(property = "password", column = "password"),
            @Result(property = "userId", column = "userid")
    })
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credential getCredential(Integer credentialId);

    @ResultMap("credentialResultMap")
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    ArrayList<Credential> getCredentials(Integer userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
            "VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    void insert(Credential credential);

    @Update("UPDATE CREDENTIALS set url = #{url}, username = #{username}, key = #{key}, password = #{password}, userid = #{userId} " +
            "WHERE credentialid = #{credentialId}")
    void update(Credential credential);
}
