package com.example.hcliq_priya.activity_admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.hcliq_priya.R;

public class SignatureScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_screen);
        ImageView signImage =(ImageView)findViewById(R.id.imageview_signature);
        Button buttonGetSignature=(Button)findViewById(R.id.button_get_signature);
        String imagePath = getIntent().getStringExtra("imagePath");
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        signImage.setImageBitmap(bitmap);
        buttonGetSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SignatureScreen.this,SignatureScreen1.class);
                startActivity(i);
            }
        });
    }
}
