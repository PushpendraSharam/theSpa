package app.myapp.myapplication.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import app.myapp.myapplication.APIs.Controller;

import app.myapp.myapplication.databinding.ActivitySignatureBinding;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignatureActivity extends AppCompatActivity {
    private static final int GALLERY_REQUEST_CODE = 122;
    ActivitySignatureBinding binding;
    Calendar calendar;
    String date_;
    String pdfPath = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignatureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.loadingBar.setVisibility(View.GONE);
        binding.openPdf.setVisibility(View.GONE);
        String fName = getIntent().getStringExtra("FIRST_NAME");
        String lName = getIntent().getStringExtra("LAST_NAME");
        String mobile = getIntent().getStringExtra("MOBILE_NUMBER");
        String medication = getIntent().getStringExtra("MEDICATION_TYPE");
        String appointment = getIntent().getStringExtra("APPOINTMENT_TYPE");
        String question = getIntent().getStringExtra("QUESTION_TYPE");
        String room = getIntent().getStringExtra("ROOM_NUMBER");
        String therapyName = getIntent().getStringExtra("THERAPY_NAME");
        String inTime = getIntent().getStringExtra("IN_TIME");
        String inTimeType = getIntent().getStringExtra("IN_TIME_TYPE");
        String outTime = getIntent().getStringExtra("OUT_TIME");
        String outTimeType = getIntent().getStringExtra("OUT_TIME_TYPE");
        String modeOfPayment = getIntent().getStringExtra("MODE_OF_PAYMENT");
        String proofImagePath = getIntent().getStringExtra("PROOF_IMAGE_PATH");
        String packagePrice = getIntent().getStringExtra("PACKAGE_PRICE");
        String therapyType = getIntent().getStringExtra("THERAPY_TYPE");
        String massageName = getIntent().getStringExtra("MASSAGE_NAME");
        String massageTime = getIntent().getStringExtra("MASSAGE_TIME");
        String discount = getIntent().getStringExtra("USER_DISCOUNT");

        Log.d("Data_is", fName);
        Log.d("Data_is", lName);
        Log.d("Data_is", mobile);
        Log.d("Data_is", medication);
        Log.d("Data_is", appointment);
        Log.d("Data_is", question);
        Log.d("Data_is", room);
        Log.d("Data_is", therapyName);
        Log.d("Data_is", inTime);
        Log.d("Data_is", inTimeType);
        Log.d("Data_is", outTime);
        Log.d("Data_is", outTimeType);
        Log.d("Data_is", modeOfPayment);
        Log.d("Data_is", proofImagePath);
        Log.d("Data_is", packagePrice);
        Log.d("Data_is", therapyType);
        Log.d("Data_is", massageName);
        Log.d("Data_is", massageTime);
        calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        binding.date.setText(sdf.format(calendar.getTime()));
        Log.d("Date_type", sdf.format(calendar.getTime()));

        date_ = sdf.format(calendar.getTime());
        binding.waitTv.setText("");
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
        binding.clearSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.signatureView.clear();
                binding.signatureView.getSignatureBitmap();

            }
        });
        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.signatureView.isEmpty()) {
                    Toast.makeText(SignatureActivity.this, "Please sign the Signature", Toast.LENGTH_SHORT).show();
                } else {
                    File signImage = null;
                    File proofImage = null;
                    try {
                        signImage = new File(getSignImagePath(binding.signatureView.getSignatureBitmap()));
                        proofImage = new File(proofImagePath);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Log.d("File_Details", proofImage.getPath());
                    Log.d("File_Details", signImage.getName());

                    RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), proofImage);
                    MultipartBody.Part captured_image = MultipartBody.Part.createFormData("captured_image", proofImage.getName(), imageBody);

                    RequestBody sImageBody = RequestBody.create(MediaType.parse("image/*"), signImage);
                    MultipartBody.Part signatureData = MultipartBody.Part.createFormData("signatureData", proofImage.getName(), sImageBody);

                    RequestBody f_name = RequestBody.create(MediaType.parse("multipart/form-data"), fName);
                    RequestBody l_name = RequestBody.create(MediaType.parse("multipart/form-data"), lName);
                    RequestBody phone_number = RequestBody.create(MediaType.parse("multipart/form-data"), mobile);
                    RequestBody medication_val = RequestBody.create(MediaType.parse("multipart/form-data"), medication);
                    RequestBody appointment_source = RequestBody.create(MediaType.parse("multipart/form-data"), appointment);
                    RequestBody question_ = RequestBody.create(MediaType.parse("multipart/form-data"), question);
                    RequestBody massage_type = RequestBody.create(MediaType.parse("multipart/form-data"), massageName);
                    RequestBody price = RequestBody.create(MediaType.parse("multipart/form-data"), packagePrice);
                    RequestBody massage_time = RequestBody.create(MediaType.parse("multipart/form-data"), massageTime);
                    RequestBody rooms_type = RequestBody.create(MediaType.parse("multipart/form-data"), room);
                    RequestBody therpay_name = RequestBody.create(MediaType.parse("multipart/form-data"), therapyName);
                    RequestBody intime_time = RequestBody.create(MediaType.parse("multipart/form-data"), inTime);
                    RequestBody intime_time_type = RequestBody.create(MediaType.parse("multipart/form-data"), inTimeType);
                    RequestBody out_time = RequestBody.create(MediaType.parse("multipart/form-data"), outTime);
                    RequestBody out_time_type = RequestBody.create(MediaType.parse("multipart/form-data"), outTimeType);
                    RequestBody mode_of_payment = RequestBody.create(MediaType.parse("multipart/form-data"), modeOfPayment);
                    RequestBody gender_type = RequestBody.create(MediaType.parse("multipart/form-data"), therapyType);
                    RequestBody user_data = RequestBody.create(MediaType.parse("multipart/form-data"), date_);
                    Log.d("the data is ", date_);
                    RequestBody discountBody = RequestBody.create(MediaType.parse("multipart/form-data"), discount);

                    binding.loadingBar.setVisibility(View.VISIBLE);
                    binding.waitTv.setText("Downloading PDF wait..");

                    Call<ResponseBody> bookingApi = Controller.getInstance().fillBookingForm(captured_image, signatureData, f_name, l_name, phone_number, medication_val, appointment_source, question_, massage_type, price, massage_time, rooms_type, therpay_name, intime_time, intime_time_type, out_time, out_time_type, mode_of_payment, gender_type, user_data, discountBody);

                    bookingApi.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            binding.loadingBar.setVisibility(View.GONE);
                            if (response.isSuccessful()) {
                                // Save the PDF to a temporary file
                                binding.waitTv.setText("");
                                savePDFToDownloads(response.body());
                                binding.openPdf.setVisibility(View.VISIBLE);


                            } else {
                                Toast.makeText(SignatureActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            binding.loadingBar.setVisibility(View.GONE);
                            Toast.makeText(SignatureActivity.this, "Check your Internet", Toast.LENGTH_SHORT).show();
                            Log.d("Error_Message", t.getLocalizedMessage().toString());


                        }
                    });
                }

            }
        });
        binding.openPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("PDF_Path", pdfPath);
                Uri pdfUri = FileProvider.getUriForFile(SignatureActivity.this, "com.example.myapp.fileprovider", new File(pdfPath));

                // Create an Intent to open the PDF file.
                Intent intent = new Intent(Intent.ACTION_VIEW);

                // Set the data type to PDF and grant read permission to the receiving app.
                intent.setDataAndType(pdfUri, "application/pdf");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                // Attempt to open the PDF file.
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // Handle the case where no PDF viewer app is installed.
                    Toast.makeText(SignatureActivity.this, "No PDF viewer app found", Toast.LENGTH_SHORT).show();
                }
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

    public String getSignImagePath(Bitmap bitmap) throws IOException {
        FileOutputStream fileStream = null;
        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File(sdCard.getAbsolutePath() + "/Download");
        String fileName = String.format("%d.jpg", System.currentTimeMillis());

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

    private void savePDFToDownloads(ResponseBody body) {
        try {
            // Get the download directory
            String downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

            // Create a file with a unique name, e.g., timestamped name
            String fileName = "downloaded_pdf_" + System.currentTimeMillis() + ".pdf";
            Log.d("PDF_Path", downloadDir + fileName);
            File file = new File(downloadDir, fileName);
            pdfPath = file.getAbsolutePath();

            // Write the PDF content to the file
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                }

                outputStream.flush();

                // Notify the user that the PDF has been downloaded
                Toast.makeText(this, "PDF downloaded and saved to Downloads folder", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}