package com.archer.note.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.archer.note.R;
import com.archer.note.constant.Constant;
import com.archer.note.db.Note;
import com.archer.note.db.NoteDB;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Notes.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class NoteFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    TextView tvEmptyTip;
    MyNoteRecyclerViewAdapter myNoteRecyclerViewAdapter;
    List<Note> noteList;

    public NoteFragment() {
    }

    public static NoteFragment newInstance(int columnCount) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);
        tvEmptyTip = (TextView) view.findViewById(R.id.tv_empty_tip);
        if(NoteDB.getNoteCount() > 0) {
            noteList = NoteDB.getAllNote();
        } else {
            tvEmptyTip.setVisibility(View.VISIBLE);
            noteList = new ArrayList<>();
        }

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            myNoteRecyclerViewAdapter = new MyNoteRecyclerViewAdapter(noteList, mListener);
            recyclerView.setAdapter(myNoteRecyclerViewAdapter);
        }
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == Activity.RESULT_OK) {
                    tvEmptyTip.setVisibility(View.GONE);
                    if (data.getBooleanExtra("isAdd", true)) {
                        Note note = data.getBundleExtra(Constant.ACTION_CHANGE_NOTE).getParcelable(Constant.ACTION_CHANGE_NOTE);
                        noteList.add(note);
                        myNoteRecyclerViewAdapter.notifyItemInserted(noteList.size() - 1);
                    } else {
                        noteList.set(data.getIntExtra("position", -1), (Note) data.getBundleExtra(Constant.ACTION_CHANGE_NOTE).getParcelable(Constant.ACTION_CHANGE_NOTE));
                        myNoteRecyclerViewAdapter.notifyItemChanged(data.getIntExtra("position", -1));
                    }
                }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Note item, int position);
    }
}
