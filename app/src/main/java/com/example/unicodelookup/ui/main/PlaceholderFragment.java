package com.example.unicodelookup.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unicodelookup.R;
import com.example.unicodelookup.Unicode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PlaceholderFragment extends Fragment {

    ArrayList<Unicode> arr_original;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = view.getContext();
        final RecyclerView list = view.findViewById(R.id.recyclerView1);

        SharedPreferences prefs = context.getSharedPreferences("NAME", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("UnicodeString", "nothing");
        Type type = new TypeToken<List<Unicode>>(){}.getType();
        arr_original = gson.fromJson(json, type);

        recyclerViewAdapter adapter = new recyclerViewAdapter(context, arr_original);
        list.setLayoutManager(new LinearLayoutManager(context));
        list.setAdapter(adapter);

        EditText searchbox = view.findViewById(R.id.searchbox);
        searchbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        RecyclerView list = getView().findViewById(R.id.recyclerView1);
        // update data
        SharedPreferences prefs = getContext().getSharedPreferences("NAME", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("UnicodeString", "nothing");
        Type type = new TypeToken<List<Unicode>>(){}.getType();
        arr_original = gson.fromJson(json, type);

        recyclerViewAdapter adapter = (recyclerViewAdapter) list.getAdapter();
        adapter.updateItems(arr_original);
    }

    public void filter(String keyword){
        ArrayList<Unicode> filtered_list = new ArrayList<Unicode>();

        for (Unicode item : arr_original){
            if (item.getName().toLowerCase().contains(keyword.toLowerCase())){
                filtered_list.add(item);
            }
        }
        RecyclerView list = getView().findViewById(R.id.recyclerView1);
        recyclerViewAdapter adapter = (recyclerViewAdapter) list.getAdapter();
        adapter.updateItems(filtered_list);
    }
}