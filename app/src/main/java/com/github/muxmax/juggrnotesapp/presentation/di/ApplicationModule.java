package com.github.muxmax.juggrnotesapp.presentation.di;

import com.github.muxmax.juggrnotesapp.data.NoteRepository;
import com.github.muxmax.juggrnotesapp.data.NoteStore;
import com.github.muxmax.juggrnotesapp.data.SqliteDbNoteRepository;
import com.github.muxmax.juggrnotesapp.domain.interactors.GetAllNotes;
import com.github.muxmax.juggrnotesapp.domain.interactors.GetAllNotesInteractor;
import com.github.muxmax.juggrnotesapp.domain.interactors.ProvideNoteWithId;
import com.github.muxmax.juggrnotesapp.domain.interactors.ProvideNoteWithIdInteractor;
import com.github.muxmax.juggrnotesapp.domain.interactors.SaveNote;
import com.github.muxmax.juggrnotesapp.domain.interactors.SaveNoteInteractor;
import com.github.muxmax.juggrnotesapp.presentation.presenter.NoteDetailPresenter;
import com.github.muxmax.juggrnotesapp.presentation.presenter.NotesPresenter;
import com.github.muxmax.juggrnotesapp.presentation.view.NoteDetailActivity;
import com.github.muxmax.juggrnotesapp.presentation.view.NotesOverviewActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * A dagger module specifying injection points for the productive application.
 */
@Module(
        injects = {Application.class,
                NotesOverviewActivity.class,
                NoteDetailActivity.class,
                GetAllNotesInteractor.class,
                ProvideNoteWithIdInteractor.class,
                NoteDetailPresenter.class,
                NotesPresenter.class
        }
)
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    public GetAllNotes provideGetAllNotes() {
        return application.getObjectGraph().get(GetAllNotesInteractor.class);
    }

    @Provides
    public ProvideNoteWithId provideProvideNoteWithId() {
        return application.getObjectGraph().get(ProvideNoteWithIdInteractor.class);
    }

    @Provides
    public SaveNote provideSaveNoteInteractor() {
        return application.getObjectGraph().get(SaveNoteInteractor.class);
    }

    @Singleton
    @Provides
    public NoteStore provideNoteStore() {
        return new NoteStore();
    }

    @Singleton
    @Provides
    public NoteRepository provideNoteRepository() {
        return new SqliteDbNoteRepository(application);
    }


}
