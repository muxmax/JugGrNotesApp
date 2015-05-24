package com.github.muxmax.juggrnotesapp.presentation.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.etsy.android.grid.StaggeredGridView;
import com.github.muxmax.juggrnotesapp.R;
import com.github.muxmax.juggrnotesapp.domain.model.Note;
import com.github.muxmax.juggrnotesapp.domain.model.NoteStore;
import com.github.muxmax.juggrnotesapp.presentation.di.BaseActionBarActivity;
import com.github.muxmax.juggrnotesapp.presentation.util.NavigationUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;


public class NotesOverviewActivity extends BaseActionBarActivity {

    @Inject NoteStore noteStore;
    @InjectView(R.id.listView) StaggeredGridView listView;

    private NoteListAdapter listAdapter;
    private List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_overview_activity);
        ButterKnife.inject(this);

        notes = noteStore.findAll();
        listAdapter = new NoteListAdapter(this, notes);
        listView.setAdapter(listAdapter);
    }

    @OnItemClick(R.id.listView)
    public void onItemClickedInlistView(AdapterView<?> parent, View view, int position, long id) {
        NavigationUtils
                .goToNoteDetailActivity(NotesOverviewActivity.this, notes.get(position).getId());
    }

    @OnClick(R.id.buttonCreateNewNote)
    public void onButtonAddNoteClicked(View v) {
        NavigationUtils.goToNoteDetailActivity(NotesOverviewActivity.this, 0l);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listAdapter.notifyDataSetChanged();
    }
}
