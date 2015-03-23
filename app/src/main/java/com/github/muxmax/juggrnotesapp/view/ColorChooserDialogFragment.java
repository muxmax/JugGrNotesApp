package com.github.muxmax.juggrnotesapp.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.github.muxmax.juggrnotesapp.R;
import com.github.muxmax.juggrnotesapp.util.BundleArguments;


/**
 * This UI component allows to choose colors.
 */
public class ColorChooserDialogFragment extends DialogFragment implements View.OnClickListener {


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

        root.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancel();
            }
        });
        root.findViewById(R.id.colorViewBlue).setOnClickListener(this);
        root.findViewById(R.id.colorViewGreen).setOnClickListener(this);
        root.findViewById(R.id.colorViewOrange).setOnClickListener(this);
        root.findViewById(R.id.colorViewRed).setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        ColorDrawable drawable = (ColorDrawable) view.getBackground();
        int color = drawable.getColor();
        callback.onChosenColor(color);
        dialog.dismiss();
    }

    public void onCancel() {
        dialog.cancel();
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
        public void onChosenColor(int color);
    }

}
