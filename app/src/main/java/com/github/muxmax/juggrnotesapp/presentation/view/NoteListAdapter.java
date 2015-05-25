package com.github.muxmax.juggrnotesapp.presentation.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.muxmax.juggrnotesapp.R;
import com.github.muxmax.juggrnotesapp.domain.model.Note;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * An adapter that provides views for a list of {@link com.github.muxmax.juggrnotesapp.domain.model.Note}s.
 */
public class NoteListAdapter extends BaseAdapter {

    private final Context context;
    private List<Note> notes;

    public NoteListAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return notes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView =
                    LayoutInflater.from(context).inflate(R.layout.note_preview_layout, null, false);
            viewHolder = createViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Note note = notes.get(position);

        if (note.getImagePath() != null) {

            if (note.getTitle().trim().isEmpty() && note.getContent().trim().isEmpty()) {

                viewHolder.imageView.setMaxHeight(context.getResources().getDimensionPixelSize(
                        R.dimen.note_preview_image_height) * 2);
                viewHolder.textViewPreviewText.setVisibility(View.GONE);
            }

            viewHolder.imageView.setVisibility(View.VISIBLE);
            Picasso.with(context).load(new File(note.getImagePath()))
                    // actually we needed here to put in a proportional y value calculated from the image ratio.
                    .fit().centerCrop()
                    .into(viewHolder.imageView);
        } else {

            viewHolder.imageView.setVisibility(View.GONE);
            viewHolder.textViewPreviewText.setVisibility(View.VISIBLE);
        }

        viewHolder.textViewPreviewText.setBackgroundColor(note.getColor());
        viewHolder.textViewPreviewText.setText(getPreviewTextFor(note));

        return convertView;
    }

    private String getPreviewTextFor(Note note) {
        if (!note.getTitle().isEmpty()) {
            return note.getTitle();
        } else if (!note.getContent().isEmpty()) {
            return note.getContent().substring(0, max(note.getContent())) + " ...";
        } else {
            return "";
        }
    }

    private int max(String content) {
        if (content.length() < 160) {
            return content.length() - 1;
        } else {
            return 159;
        }
    }


    private ViewHolder createViewHolder(View convertView) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
        viewHolder.textViewPreviewText =
                (TextView) convertView.findViewById(R.id.textViewPreviewText);
        return viewHolder;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textViewPreviewText;
    }
}
