package com.example.samplealbum;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {

    Button button;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //화면표시
        setContentView(R.layout.activity_main);

        // VIEW 찾기
        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);

        //버튼이벤트
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //앨범 앱 -Intent
                openGallery(); // 갤러리앱열기
            }
        });

    }// E of oncreate

    //앨범 선택 후 실행할 메서드
     //갤러리 앱 열기
    public void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT); //앱여는 엑티비티이름
        startActivityForResult(intent, 101); // 결과값 가져오는거 안에 숫자는 내가 보낸 그것인지 확인하는 용도
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            Uri fileUri= data.getData();
            ContentResolver resolver = getContentResolver();
            try{
                InputStream instream = resolver.openInputStream(fileUri);
                Bitmap imageBitmap = BitmapFactory.decodeStream(instream);
                imageView.setImageBitmap(imageBitmap);
                instream.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onDenied(int i, String[] strings) {

    }

    @Override
    public void onGranted(int i, String[] strings) {

    }

    @Override // 수락하고나면 이거뒤에 Denied Granted 진행됨
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

} // E of class
