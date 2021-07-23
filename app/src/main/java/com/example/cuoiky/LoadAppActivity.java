package com.example.cuoiky;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoadAppActivity extends AppCompatActivity {

//    private static int SPLASH_SCREEN = 3000;
    Button btnLogin;
    EditText edtEmail, edtPass;
    CheckBox CheckBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_app);

        btnLogin = findViewById(R.id.btlg);
        edtEmail = findViewById(R.id.editUser);
        edtPass = findViewById(R.id.editPass);
        CheckBox = findViewById(R.id.showpass);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                String pass = edtPass.getText().toString();

                // Neu tai khoan mat khau tru thi se qua mainactivity
                if (email.equals("admin") && pass.equals("admin")) {
                    Intent intent = new Intent(LoadAppActivity.this, MainActivity.class);
                    Toast.makeText(LoadAppActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LoadAppActivity.this, "Đăng nhập thất bại ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (b) {
                    edtPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    edtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }
}