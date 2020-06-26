package com.bignerdranch.android.shopping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ListFragment extends Fragment implements Observer {
    private static ItemsDB itemsDB;

    private Button mDeleteItems;
    private TextView mView;
    private RecyclerView mItemsList;
    private ItemAdapter mAdapter;

    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mWhatTextView, mWhereTextView;

        public ItemHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mWhatTextView = (TextView) itemView.findViewById(R.id.what_recycler);
            mWhereTextView = (TextView) itemView.findViewById(R.id.where_recycler);;
        }

        public void bind(Item item, int position) {
            mWhatTextView.setText("Buy " + item.getWhat());
            mWhereTextView.setText(" in: " + item.getWhere());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getContext(),
                    mWhatTextView.getText().subSequence(4, mWhatTextView.getText().length())
                            .toString().toUpperCase()
                            + " WAS DELETED!!",
                    Toast.LENGTH_SHORT).show();
            itemsDB.deleteOneItem(mWhatTextView.getText()
                    .subSequence(4, mWhatTextView.getText()
                            .length()).toString().trim());
        }
    } // ViewHolder private inner class

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {
        private List<Item> mItems;

        public ItemAdapter() {
            mItems = itemsDB.getItemsDB();
        }

        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.one_row, parent, false);
            return new ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
            final Item item = itemsDB.getItemsDB().get(position);
            holder.bind(item, position);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public void setItems() {
            mItems = itemsDB.getItemsDB();
        }
    } // ItemAdapter private inner class

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsDB = ItemsDB.get(getActivity());
        itemsDB.addObserver(this);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mItemsList = (RecyclerView) view.findViewById(R.id.listItems);
        mItemsList.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        // Delete all items event
        mDeleteItems = (Button) view.findViewById(R.id.del_items);
        mDeleteItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemsDB.deleteItemsDB();
            }
        });
        return view;
    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new ItemAdapter();
            mItemsList.setAdapter(mAdapter);
        } else {
            mAdapter.setItems();
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        updateUI();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_menu_bar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
