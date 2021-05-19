package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface NoteMapper {
    @Results(id = "noteResultMap", value = {
            @Result(property = "noteId", column = "noteid"),
            @Result(property = "noteTitle", column = "notetitle"),
            @Result(property = "noteDescription", column = "notedescription"),
            @Result(property = "userId", column = "userid")
    })
    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    ArrayList<Note> getNotes(int noteId);

    @ResultMap("noteResultMap")
    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
    Note getNote(int noteId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Update("UPDATE NOTES set notetitle = #{noteTitle}, notedescription = #{noteDescription}, userid = #{userId} WHERE noteid = #{noteId}")
    int update(Note note);

    @Delete("DELETE NOTES WHERE noteid = #{noteId}")
    int delete(Integer noteId);
}
