package com.github.muxmax.juggrnotesapp.presentation.presenter;

import com.github.muxmax.juggrnotesapp.domain.interactors.GetAllNotes;
import com.github.muxmax.juggrnotesapp.domain.model.Note;
import com.github.muxmax.juggrnotesapp.domain.model.SampleNotes;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * The test cases for displaying notes which is handled by the {@link NotesPresenter}.
 */
@Config(emulateSdk = 18, manifest = "src/main/AndroidManifest.xml")
@RunWith(RobolectricTestRunner.class)
public class NotesPresenterTest {

    @Inject NotesPresenter presenter;
    @Inject NotesPresenter.NotesView viewMock;
    @Inject GetAllNotes getAllNotesMock;
    private List<Note> loadedNotes = SampleNotes.generate(5);

    @Before
    public void setUp() {
        ObjectGraph graph = ObjectGraph.create(new TestModule());
        graph.inject(this);
    }

    @Test
    public void at_the_beginning_all_notes_are_loaded_and_displayed_successfully() {
        // when
        presenter.onCreate(viewMock);
        presenter.onResume();

        // then
        verify(getAllNotesMock).execute(eq(presenter));
        // after short time the asynchronous execution of GetAllNotes has finished.
        presenter.onSuccess(loadedNotes);

        verify(viewMock).displayNotes(loadedNotes);
    }

    @Test
    public void errors_caused_by_loading_notes_are_displayed_in_the_view() {
        // when
        presenter.onCreate(viewMock);
        presenter.onResume();

        // then
        verify(getAllNotesMock).execute(eq(presenter));
        // after short time the asynchronous execution of GetAllNotes finishes with an error.
        presenter.onError();

        verify(viewMock).displayLoadingError();
    }

    @Test
    public void when_notes_are_added_the_presenter_updates_the_view_to_display_those() {
        // given
        presenter.onCreate(viewMock);

        // when
        // the view is paused as something else gets into foreground.
        // Then the model changes as notes were added. Then it gets resumed.
        List<Note> updatedNotes = SampleNotes.generate(30);
        presenter.onResume();
        presenter.onSuccess(updatedNotes);

        // then
        verify(viewMock).displayNotes(updatedNotes);
    }

    @Test
    public void pressing_on_a_single_note_will_open_the_detail_view() {
        // given
        Note pressedNote = loadedNotes.get(4);
        presenter.onCreate(viewMock);

        // when
        presenter.onPressedNote(pressedNote);

        // then
        verify(viewMock).openNoteDetails(eq(pressedNote.getId()));
    }

    @Test
    public void pressing_the_add_note_button_will_open_the_detail_view_for_a_new_note() {
        // given
        presenter.onCreate(viewMock);

        // when
        presenter.onPressedAddNoteButton();

        // then
        verify(viewMock).openNoteDetails(eq(0l));
    }

    /**
     * A dagger module specifying injection points for application tests.
     */
    @Module(
            injects = {
                    NotesPresenterTest.class,
                    NotesPresenter.class
            },
            library = true)
    static class TestModule {
        @Singleton
        @Provides
        GetAllNotes provideGetAllNotes() {
            return mock(GetAllNotes.class);
        }

        @Provides
        NotesPresenter.NotesView provideNotesView() {
            return mock(NotesPresenter.NotesView.class);
        }
    }
}
