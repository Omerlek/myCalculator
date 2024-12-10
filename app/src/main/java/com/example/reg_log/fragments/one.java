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
import android.widget.Toast;

import com.example.reg_log.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link one#newInstance} factory method to
 * create an instance of this fragment.
 */
public class one extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseAuth mAuth;

    public one() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment one.
     */
    // TODO: Rename and change types and number of parameters
    public static one newInstance(String param1, String param2) {
        one fragment = new one();
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




    public void FuncLogin(String password, String email, LoginCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            callback.onLoginResult(1);
                        }
                        else {
                            callback.onLoginResult(0);
                        }
                    }
                });
    }


    public interface LoginCallback {
        void onLoginResult(int result);
    }


    public boolean isPasswordValid(String password) {

        if (password.length() < 6) {

            return false;
        }

        return true;
    }

    public boolean areFieldsNotEmpty(String email, String password) {
        return !(email.isEmpty() || password.isEmpty());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_one, container, false);

        Button button1 = view.findViewById(R.id.buttonRegister);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).navigate(R.id.action_one_to_three);

            }
        });


        Button button2 = view.findViewById(R.id.buttonLogin);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText passwordField = view.findViewById(R.id.editTextPasswordLogin);
                EditText emailField = view.findViewById(R.id.editTextEmailLogin);

                String password = passwordField.getText().toString();
                String email = emailField.getText().toString();

                if (!areFieldsNotEmpty(email, password)) {
                    Toast.makeText(getContext(), "לא כל השדות מלאים!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (isPasswordValid(password)) {

                        FuncLogin(password, email, new LoginCallback() {
                            @Override
                            public void onLoginResult(int result) {
                                if (result == 1) {
                                    Toast.makeText(getContext(), "התחברות הצליחה!", Toast.LENGTH_LONG).show();
                                    Navigation.findNavController(view).navigate(R.id.action_one_to_two);
                                }
                                else {

                                    Toast.makeText(getContext(), "התחברות נכשלה, פרטים שגויים.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(getContext(), "סיסמא לא תקינה, צריך לפחות 6 תווים/ספרות", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

         return view;
    }
}