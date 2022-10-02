package com.example.unicodelookup.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.unicodelookup.R;
import com.example.unicodelookup.Unicode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    public FavoritesFragment() {
        // Required empty public constructor
    }

    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = view.getContext();
        RecyclerView favorites_recyclerview = view.findViewById(R.id.favoritesRecyclerView);

        SharedPreferences prefs = context.getSharedPreferences("NAME", Context.MODE_PRIVATE);
        Gson gson = new Gson();

        String json = prefs.getString("FavoritesString", "nothing");
        Type type = new TypeToken<List<Unicode>>(){}.getType();
        ArrayList<Unicode> arr = gson.fromJson(json, type);

        // recycler view
        RecyclerView.Adapter adapter = new FavoritesRecyclerViewAdapter(context, arr);
        favorites_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        favorites_recyclerview.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        Context context = getContext();

        RecyclerView favorites_recyclerview = getView().findViewById(R.id.favoritesRecyclerView);

        SharedPreferences prefs = context.getSharedPreferences("NAME", Context.MODE_PRIVATE);
        Gson gson = new Gson();

        String json = prefs.getString("FavoritesString", "nothing");
        Type type = new TypeToken<List<Unicode>>(){}.getType();
        ArrayList<Unicode> arr = gson.fromJson(json, type);

        FavoritesRecyclerViewAdapter adapter = new FavoritesRecyclerViewAdapter(context, arr);
        favorites_recyclerview.setAdapter(adapter);
    }
}