package com.example.notalone_covid19.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.notalone_covid19.Adapter_NoteModel;
import com.example.notalone_covid19.R;
import com.example.notalone_covid19.User;

import java.util.ArrayList;

public class PeopleFragment extends Fragment
{

    private RecyclerView list_LST_notes_people;
    private Adapter_NoteModel adapter_noteModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_people, container, false);
        findViews(root);
        initList();

        return root;
    }

    private void initList() {
        ArrayList<User> notes = getNotes();
        adapter_noteModel = new Adapter_NoteModel(getContext(), notes);
        list_LST_notes_people.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        list_LST_notes_people.setLayoutManager(layoutManager);
        list_LST_notes_people.setAdapter(adapter_noteModel);

        adapter_noteModel.SetOnItemClickListener(new Adapter_NoteModel.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, User note) {

//                img_next_note = view.findViewById(R.id.img_next_note);
//                img_next_note.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//                        Toast.makeText(getContext(), "leon is a king", Toast.LENGTH_SHORT).show();                    }
//                });


                openNote(note);
            }
        });
    }

    private void openNote(User note) {
        Toast.makeText(getContext(), note.getFullName(), Toast.LENGTH_SHORT).show();
    }

    private ArrayList<User> getNotes() {
        ArrayList<User> notes = new ArrayList<>();
        User note1 = new User("Guy","xxx@yyy.com","123");
        notes.add(note1);
        User note2 = new User("Avi","xxx@yyy.com","123");
        notes.add(note2);
        User note3 = new User("Ran","xxx@yyy.com","123");
        notes.add(note3);

        return notes;
    }

    private void findViews(View view){
        list_LST_notes_people = view.findViewById(R.id.list_LST_notes_people);
    }
}
