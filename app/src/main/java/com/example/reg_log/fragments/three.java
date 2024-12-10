package com.example.reg_log.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reg_log.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link three#newInstance} factory method to
 * create an instance of this fragment.
 */
public class three extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseAuth mAuth;

    public three() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment three.
     */
    // TODO: Rename and change types and number of parameters
    public static three newInstance(String param1, String param2) {
        three fragment = new three();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mAuth = FirebaseAuth.getInstance();
    }

    public void FuncReg(String password,String email) {


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "הרשמה הצליחה!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getContext(), "הרשמה נכשלה!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public boolean isPasswordValid(String password, String confirmPassword) {

        if (!password.equals(confirmPassword)) {

            return false;
        }

        if (password.length() < 6) {

            return false;
        }

        return true;
    }

    public boolean areFieldsNotEmpty(String email, String password, String confirmPassword, String phone) {
        return !(email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty());
    }

    public boolean isPhoneNumberValid(String phone) {

        return phone.startsWith("05") && phone.length() == 10;
    }




     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                          Bundle savedInstanceState) {
     // Inflate the layout for this fragment
     View view = inflater.inflate(R.layout.fragment_three, container, false);

     Button button3 = view.findViewById(R.id.buttonregnow);

     button3.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             EditText passwordField = view.findViewById(R.id.editTextPassword);
             EditText confirmPasswordField = view.findViewById(R.id.editTextPasswordconfirm);
             EditText emailField = view.findViewById(R.id.editTextfillEmail);
             EditText phoneField = view.findViewById(R.id.editTextPhone);


             String password = passwordField.getText().toString();
             String confirmPassword = confirmPasswordField.getText().toString();
             String email = emailField.getText().toString();
             String phone = phoneField.getText().toString();


             if (!areFieldsNotEmpty(email, password, confirmPassword, phone)) {

                 Toast.makeText(getContext(), "לא כל השדות מלאים!", Toast.LENGTH_SHORT).show();
             }
             else {
                 if (!isPhoneNumberValid(phone)) {

                     Toast.makeText(getContext(), "מספר הטלפון לא תקין. עליך להכניס מספר טלפון שמתחיל ב-05 וכולל בדיוק 10 ספרות", Toast.LENGTH_SHORT).show();
                 }
                 else if (isPasswordValid(password, confirmPassword)) {
                     FuncReg(password,email);
                     Navigation.findNavController(view).navigate(R.id.action_three_to_one2);
                 }
                 else {

                     Toast.makeText(getContext(), "הסיסמאות לא תואמות או צריך לפחות 6 תווים/ספרות ", Toast.LENGTH_SHORT).show();
                 }

             }
         }
     });
     return view;
    }
}