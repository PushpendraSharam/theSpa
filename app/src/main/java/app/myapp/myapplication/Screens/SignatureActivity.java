package app.myapp.myapplication.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import app.myapp.myapplication.R;
import app.myapp.myapplication.databinding.ActivitySignatureBinding;

public class SignatureActivity extends AppCompatActivity {
    ActivitySignatureBinding binding;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignatureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        binding.date.setText(sdf.format(calendar.getTime()));
        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(android.icu.util.Calendar.YEAR, year);
                        calendar.set(android.icu.util.Calendar.MONTH, monthOfYear);
                        calendar.set(android.icu.util.Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                        binding.date.setText(sdf.format(calendar.getTime()));
                    }
                },
                calendar.get(android.icu.util.Calendar.YEAR),
                calendar.get(android.icu.util.Calendar.MONTH),
                calendar.get(android.icu.util.Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

}