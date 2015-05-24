package com.github.muxmax.juggrnotesapp.presentation.view;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;

import com.etsy.android.grid.StaggeredGridView;
import com.github.muxmax.juggrnotesapp.R;
import com.github.muxmax.juggrnotesapp.domain.model.Note;
import com.github.muxmax.juggrnotesapp.domain.model.NoteStore;
import com.github.muxmax.juggrnotesapp.presentation.util.NavigationUtils;
import com.shamanland.fab.FloatingActionButton;

import java.util.List;


public class NotesOverviewActivity extends ActionBarActivity {

    private NoteListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_overview_activity);

        FloatingActionButton buttonAddNote =
                (FloatingActionButton) findViewById(R.id.buttonCreateNewNote);
        buttonAddNote.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NavigationUtils.goToNoteDetailActivity(NotesOverviewActivity.this, 0l);
                    }
                }
        );

        final List<Note> notes =
                NoteStore.getInstance().findAll();
        listAdapter =
                new NoteListAdapter(this, notes);
        StaggeredGridView listView = (StaggeredGridView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NavigationUtils.goToNoteDetailActivity(NotesOverviewActivity.this, notes.get(position).getId());
            }
        });
        listView.setAdapter(listAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listAdapter.notifyDataSetChanged();
    }
}
