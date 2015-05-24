package com.github.muxmax.juggrnotesapp.presentation.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.github.muxmax.juggrnotesapp.R;
import com.github.muxmax.juggrnotesapp.presentation.util.BundleArguments;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * This UI component allows to choose colors.
 */
public class ColorChooserDialogFragment extends DialogFragment {


    private Dialog dialog;

    private Callback callback;

    public static DialogFragment newInstance(String color, Callback callback) {
        ColorChooserDialogFragment instance = new ColorChooserDialogFragment();

        Bundle args = new Bundle();
        if (color != null) {
            args.putString(BundleArguments.COLOR, color);
        }
        instance.setArguments(args);
        instance.callback = callback;

        return instance;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.color_chooser_fragment, container, false);
        ButterKnife.inject(this, root);
        return root;
    }

    @OnClick({R.id.colorViewBlue,
            R.id.colorViewGreen,
            R.id.colorViewOrange,
            R.id.colorViewRed,
            R.id.colorViewWhite})
    public void onClickColorView(View view) {
        ColorDrawable drawable = (ColorDrawable) view.getBackground();
        int color = drawable.getColor();
        callback.onChosenColor(color);
        dialog.dismiss();
    }

    /**
     * An interface to interact with that dialog.
     */
    public interface Callback {
        /**
         * Is called on pressing the choose button of the dialog.
         *
         * @param color The chosen color.
         */
        void onChosenColor(int color);
    }

}
