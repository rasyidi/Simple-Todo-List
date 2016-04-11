package com.syidi.simpletodolist.database;

import com.orm.SugarRecord;

/**
 * Created by Rasyidi on 11/04/2016.
 */
public class Todo extends SugarRecord {

    String title, description;
    boolean done;

    public Todo() {

    }

    public Todo(String title, String description) {
        this.title = title;
        this.description = description;
        this.done = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
