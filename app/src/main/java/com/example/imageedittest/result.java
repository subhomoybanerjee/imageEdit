package com.example.imageedittest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class result extends AppCompatActivity {

    ImageView result,share;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        result=findViewById(R.id.imageView6);
        share=findViewById(R.id.imageView10);

        getSupportActionBar().hide();
        result.setImageURI(getIntent().getData());

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(result.this,"hello",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                intent.setType("image/*");
                intent.putExtra(intent.EXTRA_STREAM,getIntent().getData());
                Intent chooser=Intent.createChooser(intent,"huihui.... ");
                startActivity(chooser);
            }
        });
    }
}