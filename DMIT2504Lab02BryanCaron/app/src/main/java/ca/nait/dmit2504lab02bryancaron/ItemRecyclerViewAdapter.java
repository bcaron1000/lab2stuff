package ca.nait.dmit2504lab02bryancaron;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
// Step 2: extend RecyclerView.Adapter super class

public class ItemRecyclerViewAdapter extends  RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemViewHolder>{
    // Step 3: Define fields for the data source
    private Context context;
    private List<Item> items;

    // Step 4: Create parameterized constructor
    public ItemRecyclerViewAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    public ItemRecyclerViewAdapter(Context context) {
        this.context = context;
    }
    public void addItem(Item newItem){
        items.add(newItem);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_item_title, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item currentItem = items.get(position);
        holder.itemIdTextView.setText("" + currentItem.getItemID());
        holder.itemNameTextView.setText(currentItem.getItemName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // Step 1: Create a view holder class that defines the views for a single item
    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        //For each item in your list item, define a view for them
        public TextView itemIdTextView;
        public TextView itemNameTextView;



        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemIdTextView = itemView.findViewById(R.id.list_item_id);
            itemNameTextView = itemView.findViewById(R.id.list_item_name);



        }
    }
}
