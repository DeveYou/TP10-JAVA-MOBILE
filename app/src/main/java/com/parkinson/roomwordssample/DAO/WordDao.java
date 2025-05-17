package com.parkinson.roomwordssample.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.parkinson.roomwordssample.model.Word;

import java.util.List;

@Dao
public interface WordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    @Query("DELETE FROM word")
    void deleteAll();

    @Query("SELECT * FROM word ORDER BY word ASC")
    LiveData<List<Word>> getAlphabetizedWords();

    @Update
    void update(Word word);

    @Delete
    void delete(Word word);
}
