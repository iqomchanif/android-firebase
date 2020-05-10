package com.iyus.gardenIOT.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iyus.gardenIOT.Adapter.ItemAdapter;
import com.iyus.gardenIOT.R;
import com.iyus.gardenIOT.model.ListPlant;

import java.util.ArrayList;

public class FragmentList extends Fragment {

    RecyclerView rvItem;
    ItemAdapter itemAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View orderView = inflater.inflate(R.layout.fragment_list, container, false);

        rvItem= orderView.findViewById(R.id.rvItem);
        itemAdapter= new ItemAdapter(getActivity());
        rvItem.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvItem.setAdapter(itemAdapter);
        return orderView;


    }
    public void setRvItem(ArrayList<ListPlant> listPlants){
        itemAdapter.setListItem(listPlants);
    }

}
