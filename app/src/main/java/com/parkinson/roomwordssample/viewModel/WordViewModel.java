package com.parkinson.roomwordssample.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.parkinson.roomwordssample.model.Word;
import com.parkinson.roomwordssample.repository.WordRepository;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private final WordRepository mRepository;

    private final LiveData<List<Word>> mAllWords;

    public WordViewModel (Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() { return mAllWords; }

    public void insert(Word word) { mRepository.insert(word); }
    public void delete(Word word){ mRepository.delete(word);}
    public void update(Word word){mRepository.update(word);}
}
