package com.github.muxmax.juggrnotesapp.presentation.di;

import com.github.muxmax.juggrnotesapp.data.NoteStore;
import com.github.muxmax.juggrnotesapp.domain.interactors.GetAllNotes;
import com.github.muxmax.juggrnotesapp.domain.interactors.GetAllNotesInteractor;
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
                GetAllNotesInteractor.class
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

    @Singleton
    @Provides
    public NoteStore provideNoteStore() {
        return new NoteStore();
    }

}
