package com.example.unicodelookup.ui.main;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unicodelookup.DetailActivity;
import com.example.unicodelookup.R;
import com.example.unicodelookup.Unicode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavoritesRecyclerViewAdapter extends RecyclerView.Adapter<FavoritesRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Unicode> mCharacters;
    private Context mContext;
    private LayoutInflater mInflater;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text, desc;
        public Button copyButton, favoriteButton;

        public ViewHolder(final View itemView){
            super(itemView);

            text = itemView.findViewById(R.id.recyclerTextView1);
            desc = itemView.findViewById(R.id.recyclerTextView2);
            copyButton = itemView.findViewById(R.id.CopyButton);
            favoriteButton = itemView.findViewById(R.id.FavoriteButton);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra("UNICODE_OBJECT", mCharacters.get(getAdapterPosition()));
                    mContext.startActivity(intent);
                }
            });

            copyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clipboard = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData contents = ClipData.newPlainText("unicode character", text.getText());
                    clipboard.setPrimaryClip(contents);
                    Toast.makeText(mContext, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                }
            });

            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    Unicode current_object = mCharacters.get(position);
                    boolean buttonState = current_object.isFavorited;
                    current_object.isFavorited = !buttonState;

                    SharedPreferences prefs = mContext.getSharedPreferences("NAME", Context.MODE_PRIVATE);
                    Gson gson = new Gson();
                    SharedPreferences.Editor editor = prefs.edit();

                    String json2 = prefs.getString("UnicodeString", "nothing");

                    Type type = new TypeToken<List<Unicode>>() {
                    }.getType();
                    ArrayList<Unicode> arr2 = gson.fromJson(json2, type);

                    Unicode item_to_change = null;
                    removeItem(position);

                    // loop unicode array
                    for (Unicode item : arr2){
                        if (item.getCharacter().compareTo(current_object.getCharacter()) == 0)
                            item_to_change = item;
                    }
                    if (item_to_change != null){
                        arr2.get(arr2.indexOf(item_to_change)).isFavorited = false;
                    }

                    String arr_string = gson.toJson(mCharacters);
                    String arr_string2 = gson.toJson(arr2);
                    editor.putString("FavoritesString", arr_string);
                    editor.putString("UnicodeString", arr_string2);
                    editor.commit();
                }
            });
        }
    }

    public FavoritesRecyclerViewAdapter(Context context, ArrayList<Unicode> characters){
        mCharacters = characters;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_recyclerview_detail, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;

        vh.text.setText(mCharacters.get(position).getCharacter());
        vh.desc.setText(mCharacters.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mCharacters.size();
    }

    public void removeItem(int position){
        mCharacters.remove(position);
        notifyDataSetChanged();
    }
}
