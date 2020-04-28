package com.example.contactsample;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {

    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // VIEW 찾기
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        //버튼이벤트
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //앨범 앱 -Intent
                chooseContacts(); // 갤러리앱열기
            }
        });
        //권한체크
        AutoPermissions.Companion.loadAllPermissions(this,101);

    } // E of oncreat

    //
    public void chooseContacts(){                   //전화번호부 띄우기
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, 101);
    }   //forResult랑 밑의 onActivityResult랑 연관잇음

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 101)  {
            try {
                Uri contactsUri = data.getData();
                String id = contactsUri.getLastPathSegment();
                getContacts(id);
            }catch  (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getContacts(String id){
        Cursor cursor = null;
        String name = "";
        try{ // 커서는 result set과 같다
            cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                                                null, //프로젝션은 컬럼
                    ContactsContract.Data.CONTACT_ID + "=?", //셀렉션은 where조건절
                    new String[]{id},
                    null); //한꺼번에 전화번호 여러개 선택가능하기 때문에
            if(cursor.moveToFirst()) {                                  //전화번호부에서 이름만 가져오고 싶을때
                name = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DATA1));
                textView.setText(name +": "+phone);                     //이위치에있는 텍스트컬럼을 뿌릴거야

                String columns[] = cursor.getColumnNames(); //모든 컬럼정보를 다 가져옴
                for(String column : columns) { //컬럼이름 다찍고
                    int index = cursor.getColumnIndex(column); //컬럼네임을 찍으면 몇번째 컬럼인지 나온다 . 컴럼명만 알고 몇번째 인덱스인지 모를때
                    String columnOutput = ("#"+index + "->["+ column +"]" + cursor.getString(index));
                    Log.d("Sample contact", columnOutput);      //내가 선택한 전화번호부 목록을 모두 보여줌
                }
                cursor.close();
            }
        }catch (Exception e){
            e.printStackTrace();
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
}
