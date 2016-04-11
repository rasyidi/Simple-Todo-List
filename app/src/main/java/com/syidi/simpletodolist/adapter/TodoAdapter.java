package com.syidi.simpletodolist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.syidi.simpletodolist.R;
import com.syidi.simpletodolist.database.Todo;

import java.util.List;

/**
 * Created by Rasyidi on 11/04/2016.
 */
public class TodoAdapter extends BaseAdapter {

    Context context;
    private List<Todo> todoList;

    public TodoAdapter(Context context) {
        this.context = context;
        this.todoList = Todo.listAll(Todo.class);
    }

    @Override
    public int getCount() {
        return todoList.size();
    }

    @Override
    public Object getItem(int position) {
        return todoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.row_todo, null);
            viewHolder = new ViewHolder();
            viewHolder.txtvTitle = (TextView) convertView.findViewById(R.id.txtvTitle);
            viewHolder.txtvDescription = (TextView) convertView.findViewById(R.id.txtvDescription);
            viewHolder.imgvIcon = (ImageView) convertView.findViewById(R.id.imgvIcon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Todo todo = (Todo) getItem(position);
        viewHolder.txtvTitle.setText(todo.getTitle());
        viewHolder.txtvDescription.setText(todo.getDescription());
        if (todo.isDone()) {
            viewHolder.txtvTitle.setAlpha((float) 0.3);
            viewHolder.txtvDescription.setAlpha((float) 0.3);
            viewHolder.imgvIcon.setVisibility(View.VISIBLE);
        } else {
            viewHolder.txtvTitle.setAlpha((float) 1.0);
            viewHolder.txtvDescription.setAlpha((float) 1.0);
            viewHolder.imgvIcon.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView txtvTitle, txtvDescription;
        ImageView imgvIcon;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        todoList = Todo.listAll(Todo.class);
    }
}
