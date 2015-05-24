package com.github.muxmax.juggrnotesapp.presentation.di;

import com.github.muxmax.juggrnotesapp.domain.model.NoteStore;
import com.github.muxmax.juggrnotesapp.presentation.view.NoteDetailActivity;
import com.github.muxmax.juggrnotesapp.presentation.view.NotesOverviewActivity;

import dagger.Module;
import dagger.Provides;

/**
 * A dagger module specifying injection points for the productive application.
 */
@Module(
        injects = {Application.class,
                NotesOverviewActivity.class,
                NoteDetailActivity.class
        }
)
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    public NoteStore provideNoteStore() {
        return NoteStore.getInstance();
    }

}
