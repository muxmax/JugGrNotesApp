package com.github.muxmax.juggrnotesapp.presentation.di;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * An Activity implementation to be used as base class for all activities that need Dependency
 * Injection.
 */
public class BaseActionBarActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Application) getApplication()).getObjectGraph().inject(this);
    }
}
