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

    @Before
    public void setUp() {
        ObjectGraph graph = ObjectGraph.create(new TestModule());
        graph.inject(this);
    }

    @Test
    public void at_the_beginning_all_notes_are_loaded_and_displayed_successfully() {
        // given
        List<Note> loadedNotes = SampleNotes.generate(5);

        // when
        presenter.onCreate(viewMock, null);

        // then
        verify(getAllNotesMock).execute(eq(presenter));
        // after short time the asynchronous execution of GetAllNotes has finished.
        presenter.onSuccess(loadedNotes);

        verify(viewMock).displayNotes(loadedNotes);
        verify(viewMock).setDisplayPosition(eq(0));
    }

    @Test
    public void errors_caused_by_loading_notes_are_displayed_in_the_view() {
        // when
        presenter.onCreate(viewMock, null);

        // then
        verify(getAllNotesMock).execute(eq(presenter));
        // after short time the asynchronous execution of GetAllNotes finishes with an error.
        presenter.onError();

        verify(viewMock).displayLoadingError();
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
