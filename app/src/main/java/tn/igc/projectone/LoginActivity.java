package tn.igc.projectone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
    EditText userName,userPassword;
    Button connect,mdpforgot,inscrip;
    boolean isPasswordValidated,isUserValidated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inscrip=(Button)findViewById(R.id.inscrip);
        inscrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });
        userName=(EditText)findViewById(R.id.editText);
        userPassword=(EditText)findViewById(R.id.editText2);
        connect=(Button)findViewById(R.id.button2);
        mdpforgot=(Button)findViewById(R.id.button);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUserName = userName.getText().toString().trim();
                String strPassword = userPassword.getText().toString().trim();
                if (null == strUserName || strUserName.length() == 0)
                {
                    // showToast("Enter Your Name");
                    userName.setError( "username is required!" );
                    isUserValidated = false;
                }
                if (null == strPassword || strPassword.length() == 0)
                {
                    // showToast("Enter Your Password");
                    isPasswordValidated = false;
                    userPassword.setError( "password is required!" );
                }
            }
        });


    }
}
