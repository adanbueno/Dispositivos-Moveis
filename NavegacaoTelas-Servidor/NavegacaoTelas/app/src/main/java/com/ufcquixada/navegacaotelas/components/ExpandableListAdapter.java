package com.ufcquixada.navegacaotelas.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.ufcquixada.navegacaotelas.R;
import com.ufcquixada.navegacaotelas.controllers.BookController;
import com.ufcquixada.navegacaotelas.model.Book;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    Context context;
    BookController bookController;

    public ExpandableListAdapter(Context context, BookController bookController) {
        this.context = context;
        this.bookController = bookController;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        Book book = ( Book ) getGroup( i );

        if( view == null ) {
            LayoutInflater inflater = ( LayoutInflater ) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_group, null);
        }

        TextView title = (TextView) view.findViewById( R.id.listTitleBook );

        title.setText(  book.getTitle() );

        return view;
    }

    @Override
    public Object getGroup(int i) {
        return bookController.getBook( bookController.getIdByPosition( i ) );
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.info_book, null);
        }

        TextView id = (TextView) view.findViewById(R.id.textView_IdBook);
        TextView author = (TextView) view.findViewById(R.id.textView_AuthorBook);
        TextView value = (TextView) view.findViewById(R.id.textView_ValueBook);

        Book book = ( Book ) getGroup( i );

        id.setText(String.valueOf(book.getId()));
        author.setText(book.getAuthor());
        value.setText(String.valueOf(book.getValue()));

        return view;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return bookController.getIdByPosition( i );
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public int getGroupCount() {
        return bookController.getSizeListBook();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
