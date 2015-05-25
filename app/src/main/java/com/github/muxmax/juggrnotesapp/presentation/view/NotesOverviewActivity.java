package com.github.muxmax.juggrnotesapp.presentation.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.github.muxmax.juggrnotesapp.R;
import com.github.muxmax.juggrnotesapp.domain.model.Note;
import com.github.muxmax.juggrnotesapp.presentation.di.BaseActionBarActivity;
import com.github.muxmax.juggrnotesapp.presentation.presenter.NotesPresenter;
import com.github.muxmax.juggrnotesapp.presentation.util.NavigationUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;


public class NotesOverviewActivity extends BaseActionBarActivity
        implements NotesPresenter.NotesView {

    @Inject NotesPresenter presenter;
    @InjectView(R.id.listView) StaggeredGridView listView;

    private NoteListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate(this);
        setContentView(R.layout.notes_overview_activity);
        ButterKnife.inject(this);
    }

    @OnItemClick(R.id.listView)
    public void onListViewItemClicked(AdapterView<?> parent, View view, int position, long id) {
        presenter.onPressedNote(listAdapter.getNotes().get(position));
    }

    @OnClick(R.id.buttonCreateNewNote)
    public void onButtonAddNoteClicked(View v) {
        presenter.onPressedAddNoteButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void displayNotes(List<Note> notes) {
        if (listAdapter == null) {
            listAdapter = new NoteListAdapter(this, notes);
            listView.setAdapter(listAdapter);
        }
        listAdapter.setNotes(notes);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayLoadingError() {
        Toast.makeText(this, R.string.notes_could_not_be_loaded, Toast.LENGTH_LONG).show();
    }

    @Override
    public void openNoteDetails(long noteId) {
        NavigationUtils
                .goToNoteDetailActivity(NotesOverviewActivity.this, noteId);
    }
}
