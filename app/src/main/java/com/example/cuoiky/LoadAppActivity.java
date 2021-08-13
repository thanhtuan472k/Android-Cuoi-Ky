package com.example.cuoiky;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoadAppActivity extends AppCompatActivity {
    final String DATABASE_NAME = "quanlisinhviennew.db";
    //    private static int SPLASH_SCREEN = 3000;
    Button btnLogin, btnSingUp;
    EditText edtEmail, edtPass;
    CheckBox CheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_app);
        MainActivity.sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
        btnLogin = findViewById(R.id.btlg);
        edtEmail = findViewById(R.id.editUser);
        edtPass = findViewById(R.id.editPass);
        CheckBox = findViewById(R.id.showpass);
        btnSingUp = findViewById(R.id.btdk);
        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(LoadAppActivity.this);
                dialog.setTitle("Hộp thoại đăng kí");
                dialog.setCancelable(false);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.signup);
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                EditText edtk = (EditText) dialog.findViewById(R.id.editUser_SU);
                final EditText edmk = (EditText) dialog.findViewById(R.id.editPass_SU);
                Button btndongy = (Button) dialog.findViewById(R.id.btndongy);
                Button btnback = (Button) dialog.findViewById(R.id.btnthoat);
                btnback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                btndongy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (edtk.getText().length() != 0 && edmk.getText().length() != 0) {
                            if (checkusername(edtk.getText().toString()) == false) {
                                Toast.makeText(LoadAppActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                                MainActivity.sqLiteDatabase.execSQL("INSERT INTO account values('" + edtk.getText().toString() + "','" + edmk.getText().toString() + "')");
                                dialog.cancel();
                            } else {
                                Toast.makeText(LoadAppActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoadAppActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtEmail.getText().length() != 0 && edtPass.getText().length() != 0) {
                    if (checkusernamePassword(edtEmail.getText().toString(), edtPass.getText().toString()) == true) {
                        Toast.makeText(LoadAppActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoadAppActivity.this, MainActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(LoadAppActivity.this, "Tài khoản hoặc mật khẩu sai", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoadAppActivity.this, "Vui lòng nhập tài khoản và mật khẩu", Toast.LENGTH_SHORT).show();
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

    public Boolean checkusernamePassword(String username, String password) {
        Cursor cursor = MainActivity.sqLiteDatabase.rawQuery("select * from account where username = ? and password =?", new String[]{username, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkusername(String username) {
        Cursor cursor = MainActivity.sqLiteDatabase.rawQuery("select * from account where username = ?", new String[]{username});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
}