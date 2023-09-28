package app.myapp.myapplication.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.myapp.myapplication.APIs.Controller;
import app.myapp.myapplication.Modals.CouponResponse;
import app.myapp.myapplication.Modals.ItemModal;
import app.myapp.myapplication.ProductAdapter;
import app.myapp.myapplication.R;
import app.myapp.myapplication.Utils.RealPathUtil;
import app.myapp.myapplication.databinding.ActivityFormBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Form extends AppCompatActivity {
    private static final int REQUEST_SELECT_IMAGE = 100;
    private static final int CAMERA_PERMISSION_REQUEST = 5000;
    ActivityFormBinding binding;
    ArrayList<String> sourceList, time, oneTime;
    Calendar calendar;
    String proofImagePath = "";

    String fName = "";
    String lName = "";
    String mobile = "";
    String medication = "";
    String appointment = "";
    String question = "";
    String room = "";
    String therapyName = "";
    String inTime = "";
    String inTimeType = "";
    String outTime = "";
    String outTimeType = "";
    String modeOfPayment = "";
    String massageName = "";
    String massageTime = "";
    String discount = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.couponResult.setText("");
        sourceList = new ArrayList<>();
        time = new ArrayList<>();
        oneTime = new ArrayList<>();

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
        oneTime.add("90 MIN");

        isCameraPermissionAllowed();


        calendar = Calendar.getInstance();


        ArrayAdapter<String> sourceAdapter = new ArrayAdapter<>(Form.this, R.layout.spinner_item, sourceList);
        binding.sourceOfAppointment.setAdapter(sourceAdapter);
        binding.productsRecycleView.setLayoutManager(new LinearLayoutManager(Form.this));
        List<ItemModal> itemModalList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, time);
        binding.sourceOfAppointment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    appointment = "";
                } else {
                    appointment = sourceList.get(i);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        itemModalList.add(new ItemModal("https://files.jotform.com/jufs/thespacloud/form_files/Thai%20Yoga%20Therapy.png?md5=NhnONIrfoDr1zErc9mQzRA&expires=1695386505", "Thai Yoga Therapy", new ArrayAdapter<>(this, R.layout.spinner_item, time), new Double[]{2500.00, 2900.00, 3500.00}));

        itemModalList.add(new ItemModal("https://files.jotform.com/jufs/thespacloud/form_files/Balinese%20(Aroma)%20Therapy.png?md5=GdSS0yTnUpMIwBLrwPmYFg&expires=1695386670", "Balinese (Aroma) Therapy", new ArrayAdapter<>(this, R.layout.spinner_item, time), new Double[]{2500.00, 3100.00, 3700.00}));

        itemModalList.add(new ItemModal("https://files.jotform.com/jufs/thespacloud/form_files/Swedish%20Therapy.png?md5=i4GdC1qNiI3YyCDBNkPE9A&expires=1695387678", "Swedish Therapy", new ArrayAdapter<>(this, R.layout.spinner_item, time), new Double[]{2800.00, 3400.00, 4000.00}));

        itemModalList.add(new ItemModal("https://files.jotform.com/jufs/thespacloud/form_files/Deep%20tissue%20therapy.png?md5=gSIarEMHFy7TgLpjQ270eA&expires=1695709765", "Deep tissue therapy", new ArrayAdapter<>(this, R.layout.spinner_item, time), new Double[]{3000.00, 3700.00, 4200.00}));

        itemModalList.add(new ItemModal("https://files.jotform.com/jufs/thespacloud/form_files/Fruit%20Therapy%20(Real%20Fruit).png?md5=hVZYa3BD9XZLOiV0LGOPLA&expires=1695710075", "Fruit Therapy (Real Fruit)", new ArrayAdapter<>(this, R.layout.spinner_item, time), new Double[]{3000.00, 3700.00, 4200.00}));

        itemModalList.add(new ItemModal("https://files.jotform.com/jufs/thespacloud/form_files/Red%20Wine%20Therapy%20(Original%20Wine).png?md5=BxuJGSH7VdCs6NW6Y0D9Tw&expires=1695710249", "Red Wine Therapy (Original Wine)", new ArrayAdapter<>(this, R.layout.spinner_item, time), new Double[]{3500.00, 4000.00, 4500.00}));

        itemModalList.add(new ItemModal("https://files.jotform.com/jufs/thespacloud/form_files/Milk%20Therapy%20(Original%20Milk).png?md5=CP5iTMXUiEmCbQaE1lGgxQ&expires=1695710356", "Milk Therapy (Original Milk)", new ArrayAdapter<>(this, R.layout.spinner_item, time), new Double[]{3500.00, 4000.00, 4500.00}));

        itemModalList.add(new ItemModal("https://files.jotform.com/jufs/thespacloud/form_files/Chocolate%20Therapy.png?md5=XDEcI-GRUpDjWDhEFlte_w&expires=1695710443", "Chocolate Therapy", new ArrayAdapter<>(this, R.layout.spinner_item, time), new Double[]{3500.00, 4000.00, 4500.00}));

        itemModalList.add(new ItemModal("https://files.jotform.com/jufs/thespacloud/form_files/Body%20Scrub%20Therapy.png?md5=shXyfyPcqt4FEJ5-yVB5Fg&expires=1695710701", "Body Scrub Therapy", new ArrayAdapter<>(this, R.layout.spinner_item, time), new Double[]{4500.00, 5500.00, 6000.00}));

        itemModalList.add(new ItemModal("https://files.jotform.com/jufs/thespacloud/form_files/Four%20Hands%20Therapy.png?md5=yvtojfYXOTkxbhD5hzLrsQ&expires=1695710801", "Four Hands Therapy", new ArrayAdapter<>(this, R.layout.spinner_item, time), new Double[]{4500.00, 5500.00, 6000.00}));

        itemModalList.add(new ItemModal("https://files.jotform.com/jufs/thespacloud/form_files/Body%20Polish%20Therapy.png?md5=qTVbSl4m6N8xRpxTHEOgfA&expires=1695710863", "Body Polish Therapy", new ArrayAdapter<>(this, R.layout.spinner_item, oneTime), new Double[]{6500.00}));

        binding.productsRecycleView.setAdapter(new ProductAdapter(itemModalList, binding, new ProductAdapter.MassageDetails() {
            @Override
            public void details(String name, String time) {
                massageName = name;
                massageTime = time;
            }
        }));
        binding.medication.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton selectedRadioButton = findViewById(i);
                if (selectedRadioButton != null) {
                    medication = selectedRadioButton.getText().toString();
                }
            }
        });
        binding.questionRation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton selectedRadioButton = findViewById(i);
                if (selectedRadioButton != null) {
                    question = selectedRadioButton.getText().toString();
                }
            }
        });
        binding.roomRadion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton selectedRadioButton = findViewById(i);
                if (selectedRadioButton != null) {
                    room = selectedRadioButton.getText().toString();
                }
            }
        });
        binding.therepyNameRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton selectedRadioButton = findViewById(i);
                if (selectedRadioButton != null) {
                    therapyName = selectedRadioButton.getText().toString();
                }
            }
        });
        binding.paymentRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton selectedRadioButton = findViewById(i);
                if (selectedRadioButton != null) {
                    modeOfPayment = selectedRadioButton.getText().toString();
                }
            }
        });
        binding.inTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(Form.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        String amPm;
                        if (hourOfDay < 12) {
                            amPm = "AM";
                        } else {
                            amPm = "PM";
                        }
                        binding.inTimeBtn.setText(hourOfDay + " : " + minute + " : " + amPm);
                        inTime = hourOfDay + " : " + minute;
                        inTimeType = amPm;
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(Form.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        String amPm;
                        if (hourOfDay < 12) {
                            amPm = "AM";
                        } else {
                            amPm = "PM";
                        }
                        binding.outTime.setText(hourOfDay + " : " + minute + " : " + amPm);
                        outTime = hourOfDay + " : " + minute;
                        outTimeType = amPm;
                    }
                }, hour, minute, false);

                timePickerDialog.show();
            }
        });
        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fName = binding.firstName.getEditText().getText().toString();
                lName = binding.lastName.getEditText().getText().toString();
                mobile = binding.mobileNumber.getEditText().getText().toString();
                String price = getPriceNumber(binding.totalPrice.getText().toString());

                if (fName.isEmpty()) {
                    showErrorMessage("First Name Can't be Empty");
                } else if (lName.isEmpty()) {
                    showErrorMessage("Last Name Can't be Empty");
                } else if (mobile.isEmpty()) {
                    showErrorMessage("Mobile Number Can't be Empty");
                } else if (medication.isEmpty()) {
                    showErrorMessage("Choose Medication");
                } else if (appointment.isEmpty()) {
                    showErrorMessage("Select Appointment");
                } else if (question.isEmpty()) {
                    showErrorMessage("Select a Question");
                } else if (room.isEmpty()) {
                    showErrorMessage("Room Can't be Empty");
                } else if (therapyName.isEmpty()) {
                    showErrorMessage("Choose a Therapy");
                } else if (inTime.isEmpty()) {
                    showErrorMessage("In Time Can't be Empty");
                } else if (outTime.isEmpty()) {
                    showErrorMessage("Out Time Can't be Empty");
                } else if (modeOfPayment.isEmpty()) {
                    showErrorMessage("Mode of Payment Can't be Empty");
                } else if (proofImagePath.isEmpty()) {
                    showErrorMessage("Please Select an Image ");
                } else if (price.isEmpty() || price.equals("0.0")) {
                    showErrorMessage("Please Select any package");
                } else {
                    Intent intent = new Intent(Form.this, Privacy_policy.class);
                    intent.putExtra("FIRST_NAME", fName);
                    intent.putExtra("LAST_NAME", lName);
                    intent.putExtra("MOBILE_NUMBER", mobile);
                    intent.putExtra("MEDICATION_TYPE", medication);
                    intent.putExtra("APPOINTMENT_TYPE", appointment);
                    intent.putExtra("QUESTION_TYPE", question);
                    intent.putExtra("ROOM_NUMBER", room);
                    intent.putExtra("THERAPY_NAME", therapyName);

                    intent.putExtra("IN_TIME", inTime);
                    intent.putExtra("IN_TIME_TYPE", inTimeType);
                    intent.putExtra("OUT_TIME", outTime);
                    intent.putExtra("OUT_TIME_TYPE", outTimeType);
                    intent.putExtra("MODE_OF_PAYMENT", modeOfPayment);
                    intent.putExtra("PROOF_IMAGE_PATH", proofImagePath);
                    intent.putExtra("PACKAGE_PRICE", price);

                    intent.putExtra("MASSAGE_NAME", massageName);
                    intent.putExtra("MASSAGE_TIME", massageTime);

                    intent.putExtra("USER_DISCOUNT", discount);


                    startActivity(intent);
                }


            }
        });
        binding.chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCameraPermissionAllowed()) {
                    openGallery();
                } else {
                    Toast.makeText(Form.this, "Camera Permission not Allowed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.couponBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String subTotalPrice = getPriceNumber(binding.totalPrice.getText().toString());
                double price = Double.parseDouble(subTotalPrice);
                if (price > 1.0) {
                    String code = binding.couponCodeText.getText().toString();
                    if (code.isEmpty()) {
                        binding.couponResult.setTextColor(getResources().getColor(R.color.error_color, getTheme()));
                        binding.couponResult.setText("Coupon Code is Empty");
                    } else {
                        binding.couponCodeText.setText("Please wait...");
                        Call<CouponResponse> couponApi = Controller.getInstance().checkCoupon(code);
                        couponApi.enqueue(new Callback<CouponResponse>() {
                            @Override
                            public void onResponse(Call<CouponResponse> call, Response<CouponResponse> response) {
                                binding.couponCodeText.setText("");
                                if (response.body() != null) {
                                    binding.couponCodeText.setText("");
                                    CouponResponse result = response.body();
                                    if (result.getSuccess()) {

                                        discount = result.getDiscount();
                                        String subTotalPrice = getPriceNumber(binding.totalPrice.getText().toString());
                                        String discount = result.getDiscount();
                                        Double sPrice = Double.parseDouble(subTotalPrice);
                                        Double dPrice = Double.parseDouble(discount);
                                        Double priceResult = sPrice - dPrice;
                                        String discountTv = String.valueOf(priceResult);
                                        binding.discountPrice.setText(discount + " INR");
                                        binding.priceAfterDiscount.setText(discountTv + "INR");
                                        binding.couponResult.setTextColor(getResources().getColor(R.color.success_color, getTheme()));
                                        binding.couponResult.setText("Coupon Apply Successfully");
                                    } else {
                                        discount = result.getDiscount();
                                        String subTotalPrice = getPriceNumber(binding.totalPrice.getText().toString());
                                        String discount = result.getDiscount();
                                        Double sPrice = Double.parseDouble(subTotalPrice);
                                        Double dPrice = 0.0;
                                        Double priceResult = sPrice - dPrice;
                                        String discountTv = String.valueOf(priceResult);
                                        binding.discountPrice.setText(discount + " INR");
                                        binding.priceAfterDiscount.setText(discountTv + "INR");
                                        binding.couponResult.setTextColor(getResources().getColor(R.color.error_color, getTheme()));
                                        binding.couponResult.setText("Coupon code not found");
                                        binding.couponCodeText.setText("");
                                    }
                                } else {
                                    binding.couponCodeText.setText("");
                                    Toast.makeText(Form.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<CouponResponse> call, Throwable t) {
                                binding.couponCodeText.setText("");
                                binding.couponResult.setText("");
                                Log.d("Error_coupon", t.getLocalizedMessage());
                                Toast.makeText(Form.this, "Check your internet", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                } else {
                    Toast.makeText(Form.this, "Please select a Package", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void openGallery() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_SELECT_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            binding.transactionImage.setVisibility(View.VISIBLE);
            Bitmap img = (Bitmap) (data.getExtras().get("data"));
            binding.transactionImage.setImageBitmap(img);
            try {
                proofImagePath = getSignImagePath(img);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }


    private void showErrorMessage(String msg) {

        final Snackbar snackbar = Snackbar.make(binding.formContainer, msg, Snackbar.LENGTH_LONG)
                .setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).setBackgroundTint(getResources().getColor(R.color.error_color, getTheme())).setTextColor(getResources().getColor(R.color.white, getTheme()));
        snackbar.show();


    }

    String getPriceNumber(String input) {
        String matchedValue = "me";

        // Define a regular expression pattern to match the double value
        Pattern pattern = Pattern.compile("([0-9]+\\.[0-9]+)");

        // Create a Matcher object
        Matcher matcher = pattern.matcher(input);

        // Find the first match
        if (matcher.find()) {
            // Extract and parse the matched double value
            matchedValue = matcher.group(1).toString();


        } else {
            System.out.println("No match found.");
        }
        return matchedValue;
    }

    public String getSignImagePath(Bitmap bitmap) throws IOException {
        FileOutputStream fileStream = null;
        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File(sdCard.getAbsolutePath() + "/Download");
        String fileName = String.format("%d.jpg", System.currentTimeMillis());
        binding.imageName.setText(fileName);

        File outFile = new File(directory, fileName);
        String path = outFile.getAbsolutePath(); // Use the absolute path

        Log.d("Image is", path);

        try {
            fileStream = new FileOutputStream(outFile);
            // Convert the Bitmap to JPEG format
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileStream);
            fileStream.flush();
            fileStream.close();

            // Trigger media scan to make the image available in the gallery
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(outFile));
            sendBroadcast(intent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return path;
    }

    boolean isCameraPermissionAllowed() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {

            return true;
        } else {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST);
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted by the user. You can proceed with camera-related tasks.

            } else {
                Toast.makeText(this, "Camera Permission not Allowed", Toast.LENGTH_SHORT).show();
            }
        }
    }

}





        