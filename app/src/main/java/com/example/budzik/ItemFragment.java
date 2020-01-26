package com.example.budzik;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ItemFragment extends Fragment {

    private MyItemRecyclerViewAdapter mRecyclerViewAdapter;
    private OnListFragmentInteractionListener mListener;

    public void notifyDataChange(){

        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    public ItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            mRecyclerViewAdapter=new MyItemRecyclerViewAdapter(TaskListContent.ITEMS,mListener);
            recyclerView.setAdapter(mRecyclerViewAdapter);
        }
        return view;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==Activity.RESULT_OK){
            if(data!=null){
                boolean changeDataSet=data.getBooleanExtra(TaskListActivity.DATA_CHANGED_KEY,false);
                if(changeDataSet){
                    notifyDataChange();
                }
            }
        }
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteractionListener(TaskListContent.Task task, int position);
        void onListFragmentDeleteInteractionListener(TaskListContent.Task task, int position);
    }
}
