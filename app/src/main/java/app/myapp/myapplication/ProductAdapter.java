package app.myapp.myapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.myapp.myapplication.Modals.ItemModal;
import app.myapp.myapplication.databinding.ActivityFormBinding;
import app.myapp.myapplication.databinding.ProductItemBinding;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Holder> {
    List<ItemModal> itemModalList;
    ActivityFormBinding classBinding;


    HashMap<Integer, Integer> indexList = new HashMap<>();
    HashMap<Integer, Double> priceList = new HashMap<>();
    boolean isCheck;

    public ProductAdapter(List<ItemModal> itemModalList, ActivityFormBinding binding) {
        this.itemModalList = itemModalList;
        this.classBinding = binding;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ItemModal modal = itemModalList.get(holder.getAdapterPosition());
        Picasso.get().load(modal.getImageUrl()).into(holder.binding.image);
        holder.binding.Name.setText(modal.getProductName());
        holder.binding.price.setText(modal.getPriceByTime()[0] + " INR");
        indexList.put(holder.getAdapterPosition(), 0);

        holder.binding.timeAdapter.setAdapter(modal.getAdapter());
        holder.binding.timeAdapter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                indexList.put(holder.getAdapterPosition(), i);
                Double total = 0.0;
                holder.binding.price.setText(modal.getPriceByTime()[i] + " INR");
                if (priceList.containsKey(position)) {
                    priceList.put(holder.getAdapterPosition(), modal.getPriceByTime()[indexList.get(position)]);
                    for (Map.Entry<Integer, Double> entry : priceList.entrySet()) {
                        total += entry.getValue();
                    }
                    classBinding.totalPrice.setText(total + " INR");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        holder.binding.itemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Double total = 0.0;
                    priceList.put(holder.getAdapterPosition(), modal.getPriceByTime()[indexList.get(position)]);
                    for (Map.Entry<Integer, Double> entry : priceList.entrySet()) {
                        total += entry.getValue();
                    }
                    classBinding.totalPrice.setText(total + " INR");
                } else {
                    Double total = 0.0;

                    priceList.remove(holder.getAdapterPosition());
                    for (Map.Entry<Integer, Double> entry : priceList.entrySet()) {
                        total += entry.getValue();
                    }
                    classBinding.totalPrice.setText(total + " INR");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemModalList.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        ProductItemBinding binding;


        public Holder(@NonNull View itemView) {
            super(itemView);
            binding = ProductItemBinding.bind(itemView);

        }
    }

    double getNumber(String input) {
        double result = 0.0;

        // Define a regular expression pattern to match the double value
        Pattern pattern = Pattern.compile("([0-9]+\\.[0-9]+)");

        // Create a Matcher object
        Matcher matcher = pattern.matcher(input);

        // Find the first match
        if (matcher.find()) {
            // Extract and parse the matched double value
            String matchedValue = matcher.group(1);
            result = Double.parseDouble(matchedValue);
            System.out.println(result);
        } else {
            System.out.println("No match found.");
        }
        return result;
    }


}
