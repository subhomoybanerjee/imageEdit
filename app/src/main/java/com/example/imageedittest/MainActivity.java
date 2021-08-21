package com.example.imageedittest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.UriMatcher;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    int imgreqcode=100;
    ImageView edit,cam,settings;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        edit=findViewById(R.id.imageView2);
        cam=findViewById(R.id.imageView3);

        getSupportActionBar().hide();

        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CAMERA},32);
                }
                else{
                    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,14);

                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"hello",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,imgreqcode);
            }
        });
    }
    public void dsphoto(Uri uri){
        Intent dsPhotoEditorIntent = new Intent(MainActivity.this, DsPhotoEditorActivity.class);
        dsPhotoEditorIntent.setData(uri);
        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "imageedittest");
        int[] toolsToHide = {DsPhotoEditorActivity.TOOL_ORIENTATION, DsPhotoEditorActivity.TOOL_CROP};
        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, toolsToHide);
        startActivityForResult(dsPhotoEditorIntent, 200);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==imgreqcode){
            if(data.getData()!=null){
                //Toast.makeText(MainActivity.this,data.getData().toString(),Toast.LENGTH_SHORT).show();
                dsphoto(data.getData());
            }
        }

        if(requestCode==200){
            Intent intent=new Intent(MainActivity.this,result.class);
            intent.setData(data.getData());
            startActivity(intent);
        }

        if (requestCode==14){
            Bitmap photo=(Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bout=new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG,100,bout);
            String path=MediaStore.Images.Media.insertImage(getContentResolver(),photo,"title",null);
            Uri uri=Uri.parse(path);
            dsphoto(uri);
        }
    }
}