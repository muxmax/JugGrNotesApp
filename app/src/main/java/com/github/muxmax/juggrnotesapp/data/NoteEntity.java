package com.github.muxmax.juggrnotesapp.data;

import android.graphics.Color;

import com.activeandroid.Model;

/**
 * The database model.
 */
public class NoteEntity extends Model {

    private String title;
    private String content;
    private String imagePath;
    private Integer color;

    public NoteEntity() {
        title = "";
        content = "";
        color = Color.WHITE;
    }

    /**
     * Getter for property 'title'.
     *
     * @return Value for property 'title'.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for property 'title'.
     *
     * @param title Value to set for property 'title'.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for property 'content'.
     *
     * @return Value for property 'content'.
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter for property 'content'.
     *
     * @param content Value to set for property 'content'.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Getter for property 'imagePath'.
     *
     * @return Value for property 'imagePath'.
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Setter for property 'imagePath'.
     *
     * @param imagePath Value to set for property 'imagePath'.
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * Getter for property 'color'.
     *
     * @return Value for property 'color'.
     */
    public Integer getColor() {
        return color;
    }

    /**
     * Setter for property 'color'.
     *
     * @param color Value to set for property 'color'.
     */
    public void setColor(Integer color) {
        this.color = color;
    }

}