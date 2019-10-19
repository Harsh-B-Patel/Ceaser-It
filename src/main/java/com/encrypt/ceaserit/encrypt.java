package com.encrypt.ceaserit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class encrypt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);
    }

    private void displayThis(int id, String newContent){
        View view = findViewById(id);
        TextView textview = (TextView) view;
        textview.setText(newContent);
    }

    private String getInput(int id){
        View view = findViewById(id);
        EditText editText = (EditText) view;
        String input = editText.getText().toString();
        return input;
    }

    public void encrypt(View view)throws Exception {

       displayThis(R.id.output, Ceaser.encrypt( Integer.parseInt(getInput(R.id.shift)), getInput(R.id.input)));



    }

}
