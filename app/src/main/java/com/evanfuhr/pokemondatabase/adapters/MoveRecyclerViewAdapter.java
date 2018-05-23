package com.evanfuhr.pokemondatabase.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.evanfuhr.pokemondatabase.R;
import com.evanfuhr.pokemondatabase.fragments.MoveListFragment.OnListFragmentInteractionListener;
import com.evanfuhr.pokemondatabase.models.Move;

import java.util.ArrayList;
import java.util.List;

public class MoveRecyclerViewAdapter extends RecyclerView.Adapter<MoveRecyclerViewAdapter.ViewHolder> {

    private final List<Move> mValues, _filteredList;
    private final OnListFragmentInteractionListener mListener;

    public MoveRecyclerViewAdapter(List<Move> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;

        _filteredList = new ArrayList<>();
        _filteredList.addAll(mValues);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_single_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = _filteredList.get(position);
        holder._button.setId(_filteredList.get(position).getId());
        holder._button.setText(_filteredList.get(position).getName());
        holder._button.setBackgroundColor(Color.parseColor(_filteredList.get(position).getType().getColor()));

        holder._button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return _filteredList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final Button _button;
        Move mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            _button = view.findViewById(R.id.singleButton);
        }
    }

    public void filter(final String filterText) {
        _filteredList.clear();

        // If there is no search value, then add all original list items to filter list
        if (TextUtils.isEmpty(filterText)) {

            _filteredList.addAll(mValues);

        } else {
            // Iterate in the original List and add it to filter list...
            for (Move move : mValues) {
                if (move.getName().toLowerCase().contains(filterText.toLowerCase())) {
                    // Adding Matched items
                    _filteredList.add(move);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void injectMoves(List<Move> moves) {
        mValues.clear();
        mValues.addAll(moves);
        filter("");
    }
}
