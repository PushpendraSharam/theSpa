package app.myapp.myapplication.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import app.myapp.myapplication.Modals.ItemModal;
import app.myapp.myapplication.ProductAdapter;
import app.myapp.myapplication.R;
import app.myapp.myapplication.databinding.ActivityFormBinding;

public class Form extends AppCompatActivity {
    ActivityFormBinding binding;
    ArrayList<String> sourceList, time, therapyType;
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sourceList = new ArrayList<>();
        time = new ArrayList<>();

        sourceList.add("Please Select");
        sourceList.add("JustDial");
        sourceList.add("Google");
        sourceList.add("Ads");
        sourceList.add("Telecaller");
        sourceList.add("Direct Walk In");
        sourceList.add("Satish");
        time.add("60 MIN");
        time.add("90 MIN");
        time.add("120 MIN");

        calendar = Calendar.getInstance();


        ArrayAdapter<String> sourceAdapter = new ArrayAdapter<>(Form.this, R.layout.spinner_item, sourceList);
        binding.sourceOfAppointment.setAdapter(sourceAdapter);
        binding.productsRecycleView.setLayoutManager(new LinearLayoutManager(Form.this));
        List<ItemModal> itemModalList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, time);

        itemModalList.add(new ItemModal("https://files.jotform.com/jufs/thespacloud/form_files/Thai%20Yoga%20Therapy.png?md5=NhnONIrfoDr1zErc9mQzRA&expires=1695386505", "Thai Yoga Therapy", new ArrayAdapter<>(this, R.layout.spinner_item, time), new Double[]{2500.00, 2900.00, 3500.00}));
        itemModalList.add(new ItemModal("https://files.jotform.com/jufs/thespacloud/form_files/Balinese%20(Aroma)%20Therapy.png?md5=GdSS0yTnUpMIwBLrwPmYFg&expires=1695386670", "Balinese (Aroma) Therapy", new ArrayAdapter<>(this, R.layout.spinner_item, time),  new Double[]{2500.00, 3100.00, 3700.00}));
        itemModalList.add(new ItemModal("https://files.jotform.com/jufs/thespacloud/form_files/Swedish%20Therapy.png?md5=i4GdC1qNiI3YyCDBNkPE9A&expires=1695387678", "Swedish Therapy", new ArrayAdapter<>(this, R.layout.spinner_item, time),  new Double[]{2800.00, 3400.00, 4000.00}));
        binding.productsRecycleView.setAdapter(new ProductAdapter(itemModalList,binding));
        binding.inTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(Form.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                String amPm;
                                if (hourOfDay < 12) {
                                    amPm = "AM";
                                } else {
                                    amPm = "PM";
                                }
                                binding.inTimeBtn.setText(hourOfDay + " : " + minute + " : " + amPm);
                            }
                        }, hour, minute, false);

                timePickerDialog.show();
            }
        });
        binding.outTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(Form.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                String amPm;
                                if (hourOfDay < 12) {
                                    amPm = "AM";
                                } else {
                                    amPm = "PM";
                                }
                                binding.outTime.setText(hourOfDay + " : " + minute + " : " + amPm);
                            }
                        }, hour, minute, false);

                timePickerDialog.show();
            }
        });
        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Form.this, Privacy_policy.class));
            }
        });


    }


}