package com.encrypt.ceaserit;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void decrypt(View view){
        Button button = (Button)view;
        startActivity(new Intent(getApplicationContext(), decrypt.class));
    }

    public void encrypt(View view) {
        Button button = (Button) view;
        startActivity(new Intent(getApplicationContext(), encrypt.class));
    }


}
