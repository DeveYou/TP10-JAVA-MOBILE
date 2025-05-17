package com.parkinson.roomwordssample.adapter;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parkinson.roomwordssample.R;
import com.parkinson.roomwordssample.UpdateWordActivity;
import com.parkinson.roomwordssample.model.Word;
import com.parkinson.roomwordssample.viewModel.WordViewModel;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private List<Word> mWords; // Copie en cache des mots
    private WordViewModel mWordViewModel;

    public WordListAdapter(WordViewModel viewModel) {
        this.mWordViewModel = viewModel;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recyclerview_item, parent, false);

        final WordViewHolder holder = new WordViewHolder(itemView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && mWords != null) {
                    Word selectedWord = mWords.get(position);

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Choose an action")
                            .setMessage("Word: " + selectedWord.getWord())
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mWordViewModel.delete(selectedWord);
                                    Toast.makeText(v.getContext(), "Delete clicked for " + selectedWord.getWord(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Update", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    try{
                                        Intent intent = new Intent(v.getContext(), UpdateWordActivity.class);
                                        intent.putExtra("word", selectedWord.getWord());
                                        intent.putExtra("id", selectedWord.getId());
                                        v.getContext().startActivity(intent);
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }

                                    Toast.makeText(v.getContext(), "Update clicked for " + selectedWord.getWord(), Toast.LENGTH_SHORT).show();
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        if (mWords != null) {
            Word current = mWords.get(position);
            holder.wordItemView.setText(current.getWord());

        } else {
            // Couvre le cas où les données ne sont pas encore prêtes.
            holder.wordItemView.setText("No Word");
        }
    }

    public void setWords(List<Word> words){
        mWords = words;
        notifyDataSetChanged();
    }

    // getItemCount() est appelé plusieurs fois, et lorsqu'il est appelé pour la première fois,
    // mWords n'a pas été mis à jour (cela signifie qu'initialement, il est nul, et nous ne pouvons pas retourner nul).
    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private WordViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }
}
