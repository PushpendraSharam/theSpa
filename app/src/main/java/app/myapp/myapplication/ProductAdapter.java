package app.myapp.myapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;
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
    String name = "";
    String time = "";
    Double tPrice = 0.0;
    MassageDetails obj;

    private int selectedItemPosition = -1; // Initially, no item is selected


    HashMap<Integer, Integer> indexList = new HashMap<>();
    HashMap<Integer, Double> priceList = new HashMap<>();
    boolean isCheck = false;
    ArrayList<MaterialCheckBox> checkBoxesList;

    public ProductAdapter(List<ItemModal> itemModalList, ActivityFormBinding binding, MassageDetails obj) {
        this.itemModalList = itemModalList;
        this.classBinding = binding;
        this.obj = obj;
        checkBoxesList = new ArrayList<>();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        checkBoxesList.add(holder.binding.itemCheckBox);
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
//                Double total = 0.0;
                holder.binding.price.setText(modal.getPriceByTime()[i] + " INR");

//                if (priceList.containsKey(position)) {
//                    priceList.put(holder.getAdapterPosition(), modal.getPriceByTime()[indexList.get(position)]);
//                    for (Map.Entry<Integer, Double> entry : priceList.entrySet()) {
//                        total += entry.getValue();
//                    }
//                    classBinding.totalPrice.setText(total + " INR");
//                }
                if (checkBoxesList.get(holder.getAdapterPosition()).isChecked()) {
                    tPrice = itemModalList.get(holder.getAdapterPosition()).getPriceByTime()[i];
                    classBinding.totalPrice.setText(tPrice + " INR");
                    if (position == itemModalList.size() - 1) {
                        obj.details(tPrice.toString(), "90 MIN");
                    } else {
                        if (i == 0) {
                            obj.details(tPrice.toString(), "60 MIN");
                        }
                        if (i == 1) {
                            obj.details(tPrice.toString(), "90 MIN");
                        }
                        if (i == 2) {
                            obj.details(tPrice.toString(), "120 MIN");
                        }
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        holder.binding.itemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    Double total = 0.0;
//                    priceList.put(holder.getAdapterPosition(), modal.getPriceByTime()[indexList.get(position)]);
//                    for (Map.Entry<Integer, Double> entry : priceList.entrySet()) {
//                        total += entry.getValue();
//                    }
//                    classBinding.totalPrice.setText(total + " INR");
//                } else {
//                    Double total = 0.0;
//
//                    priceList.remove(holder.getAdapterPosition());
//                    for (Map.Entry<Integer, Double> entry : priceList.entrySet()) {
//                        total += entry.getValue();
//                    }
//                    classBinding.totalPrice.setText(total + " INR");
//                }
//            }
//        });
        holder.binding.itemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (holder.getAdapterPosition() == itemModalList.size() - 1) {
                        obj.details(itemModalList.get(holder.getAdapterPosition()).getProductName(), "90 MIN");
                    } else {
                        if (indexList.get(holder.getAdapterPosition()) == 0) {
                            obj.details(itemModalList.get(holder.getAdapterPosition()).getProductName(), "60 MIN");
                        }
                        if (indexList.get(holder.getAdapterPosition()) == 1) {
                            obj.details(itemModalList.get(holder.getAdapterPosition()).getProductName(), "90 MIN");
                        }
                        if (indexList.get(holder.getAdapterPosition()) == 2) {
                            obj.details(itemModalList.get(holder.getAdapterPosition()).getProductName(), "120 MIN");
                        }
                    }

                    tPrice = itemModalList.get(holder.getAdapterPosition()).getPriceByTime()[indexList.get(holder.getAdapterPosition())];
                    for (int i = 0; i < itemModalList.size(); i++) {
                        if (i == holder.getAdapterPosition()) {
                            checkBoxesList.get(i).setChecked(true);
                        } else
                            checkBoxesList.get(i).setChecked(false);

                    }

                }
                for (int i = 0; i < itemModalList.size(); i++) {
                    if (checkBoxesList.get(i).isChecked()) {
                        break;
                    }
                    if (i == itemModalList.size() - 1) {
                        tPrice = 0.0;
                    }
                }
                classBinding.totalPrice.setText(tPrice + " INR");

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

    public interface MassageDetails {
        void details(String name, String time);
    }

}
