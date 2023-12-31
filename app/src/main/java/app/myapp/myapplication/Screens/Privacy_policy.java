package app.myapp.myapplication.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import app.myapp.myapplication.R;
import app.myapp.myapplication.databinding.ActivityPrivacyPolicyBinding;

public class Privacy_policy extends AppCompatActivity {
    ActivityPrivacyPolicyBinding binding;
    ArrayList<String> therapyType;
    String therapyPerson = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrivacyPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
        String massageName = getIntent().getStringExtra("MASSAGE_NAME");
        String massageTime = getIntent().getStringExtra("MASSAGE_TIME");
        String discount=getIntent().getStringExtra("USER_DISCOUNT");




        therapyType = new ArrayList<>();
        therapyType.add("Please Select");
        therapyType.add("Female Therapy");
        therapyType.add("Male Therapy");
        ArrayAdapter<String> therapyAdapter = new ArrayAdapter<>(Privacy_policy.this, R.layout.spinner_item, therapyType);
        binding.therepyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    therapyPerson = "";
                } else {
                    therapyPerson = therapyType.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.textPg.setText("Keeping in mind the strict code of conduct imposed by the Social Cell of the Andhra Pradesh Police.hereby undertake and accept the following points mentioned below I understand that my therapy can be supervised anytime during the session by the spa management, this is as per the law by government of India to avoid misconduct I exploitation of staff: Lights cannot be switched off completely." + "\n\n" + "I agree to wear the clothing offered to me by the Spa and will NOT insist on wearing the clothes of my ownchoice." + "\n\n" + "I understand that any illicit or sexually suggestive remarks made during the therapy made by me will result in the immediate termination of the session and I will be black listed in all Spas and all the Spaassociations in India. I'll also be liable for Payment of the schedule service. I will also coordinate with the staff and will not try to do such things which will lead to termination of my session. (Strict action will be taken against the person if found guilty) as per the law by the government of India.And I also have therights to stop my therapy in between and speak to the manager, if I find the therapist passing sexuallysuggestive remarks during the therapy. No complains after the therapy will be entertained." + "\n\n" + "I understand that is not responsible or accept the liability for any damage, lost or stolen personal belongings of individuals.I understand that the massage therapy i receive for the basic purpose of relaxation and relies of muscularrension. Il further understand that massage therapy should not be construed as a subsitule for medicalexamination, diagnosis, treatment and that I should see a physician or other qualified medical specialist any physician aliment of which I am aware." + "\n\n" + "I promise NOT to give any tip to the therapist inside the therapy room or any other place other thanreception counter. I understand the important of silence in the Spa and hence will take my services 1 silently without disturbing the other customers of Spa. Also I agree to keep my mobile on Silent mode." + "\n\n" + "Any misbehavior from my side. I will not only black listed, but also my info will be circulated to all Spas.Spa association bodies in case of occurrence of any of the above incident, I agree to stop the therapy andinform the Manager/Receptionist at that very moment. In case if I do not act as mentioned and foundguilty, the Management is NOT responsible/liable." + "\n\n" + "Insisting for Sex I Abusing therapist is a criminal offence and punishable under in Indian penal code,management has no responsibility offence prosecuted. The Consumption Of Smoking & alcohol With InThe Spa are Prohibited." + "\n\n" + "We are fully committed to full compliance with the requirements of the Data Protection Act 1998. We have a Data Protection Policy to ensure that the Trust, and people working on its behalf (including employees,temporary staff,contractors, volunteers, consultants, partners and their staff, and Members of theCouncil) are aware of their obligations under the Data Protection Act 1998 and comply fully with that Act." + "\n\n" + "Our Company Follow The right to life and personal liberty guaranteed in Article 21 also includes implicitly aright to privacy. This right to privacy is seen as possessing both inherent value, in that it is important for every person’s basic dignity, and instrumental value, in that it furthers a person’s ability to live life free of interference. It includes a right to bodily autonomy, a right to informational privacy and a right to a privacy of choice." + "\n");
        binding.importantNote.setText("Note:-Any Type Of illicit any Sexually Suggesitive Remarks made during The Therapy in Room WithPermission of Both Clients And Therapist Than Clint Will Be Totally Be responsibile For Any Kind Ofissues for After Company Will Not Be Responsibile Thereafter." + "\n\n" + "I attest that the information provided above is true and complete and therefore I give my authorization for my treatment. I have paid money by any UPI or By Card or By Cash Purely for SPA Purpose only  Not for Any other purposeType Of like illicit any Sexually." + "\n\n" + "I Undersinged have Read,Understood Agreed And Accepted The Above Points Any Disputes Will BeSubjects To Andhrapradesh Jurisdiction Only." + "\n");

        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (therapyPerson.isEmpty()) {
                    showErrorMessage("Choose Therapy Type");
                } else if (!binding.confirmCheckBox.isChecked()) {
                    showErrorMessage("Please Check the box");
                } else {
                    Intent intent = new Intent(Privacy_policy.this, SignatureActivity.class);
                    intent.putExtra("THERAPY_TYPE", therapyPerson);
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
                    intent.putExtra("PACKAGE_PRICE", packagePrice);

                    intent.putExtra("MASSAGE_NAME",massageName);
                    intent.putExtra("MASSAGE_TIME",massageTime);
                    intent.putExtra("USER_DISCOUNT", discount);



                    startActivity(intent);
                }

            }
        });
        binding.therepyType.setAdapter(therapyAdapter);
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void showErrorMessage(String msg) {

        final Snackbar snackbar = Snackbar.make(binding.privacyContainer, msg, Snackbar.LENGTH_LONG)
                .setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).setBackgroundTint(getResources().getColor(R.color.error_color, getTheme())).setTextColor(getResources().getColor(R.color.white, getTheme()));
        snackbar.show();


    }

}