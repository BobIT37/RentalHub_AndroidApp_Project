package com.karayadar.app.activities.ui.send;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.karayadar.app.LoginActivity;
import com.karayadar.app.R;
import com.karayadar.app.SharedPrefManager;
import com.karayadar.app.activities.MainMenu;
import com.karayadar.app.models.User;

import java.util.Objects;

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;
    SharedPreferences preferences;
    String terms;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_send, container, false);
        final TextView textView = root.findViewById(R.id.text_send);
        sendViewModel.getText().observe(Objects.requireNonNull(getActivity()), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        preferences = getActivity().getSharedPreferences("PREFS", 0);


        root.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = SharedPrefManager.getInstance(getContext()).getUser();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("name", user.getName());
                editor.apply();
                editor.putString("pass", user.getEmail());
                editor.apply();

               // Toast.makeText(getContext(), "A"+user.getEmail()+user.getPassword()+user.getAddress()+user.getPhoneno(), Toast.LENGTH_SHORT).show();

                SharedPrefManager.getInstance(getActivity()).logout();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                Objects.requireNonNull(getActivity()).finish();
            }
        });

        root.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), MainMenu.class));
            }
        });
        return root;
    }
}