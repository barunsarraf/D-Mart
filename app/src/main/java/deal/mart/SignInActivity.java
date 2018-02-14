package deal.mart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    public static String userName;
    private static User userInfo;

    public static User getUser() {
        return userInfo;
    }

    public static void setUser(User userInfo) {
        SignInActivity.userInfo = userInfo;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        SignInActivity.userName = userName;
    }

    private ProgressDialog dialog;
    private Button signin,signup;
    private EditText user,password;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        dialog = new ProgressDialog(this,ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Logging you");
        dialog.setMessage("Processing ...");

        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);

        signin = (Button) findViewById(R.id.sign_in);
        signup = (Button) findViewById(R.id.sign_up);

        signin.setOnClickListener(this);
        signup.setOnClickListener(this);

        preferences = getPreferences(MODE_PRIVATE);
        String pre_user = preferences.getString("user","user");
        String pre_password = preferences.getString("password","password");
        if(!pre_user.equals("user")) {
            user.setText(pre_user);
            password.setText(pre_password);
            //signIn();
        }
    }

    private void signIn(){
        if(!checkConnection()){
            Toast.makeText(SignInActivity.this,"connection error",Toast.LENGTH_LONG).show();
            return;
        }
        if(user.getText().toString().trim().length() == 0 || password.getText().toString().trim().length() == 0 ){
            Toast.makeText(SignInActivity.this,"improper details",Toast.LENGTH_LONG).show();
            return;
        }
        dialog.show();
        FirebaseDatabase.getInstance().getReference().child("user").child(user.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dialog.dismiss();
                if(!dataSnapshot.exists()){
                    Toast.makeText(SignInActivity.this,"user not found",Toast.LENGTH_LONG).show();
                    return;
                }
                User user = dataSnapshot.getValue(User.class);
                if(user.password.equals(password.getText().toString().trim())){
                    setUserName(SignInActivity.this.user.getText().toString().trim());
                    setUser(user);
                    startActivity(new Intent(SignInActivity.this,WelcomeActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(SignInActivity.this,"password error",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dialog.dismiss();
                Toast.makeText(SignInActivity.this,"failed",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void signUp() {
        if(!checkConnection()){
            Toast.makeText(SignInActivity.this,"connection error",Toast.LENGTH_LONG).show();
            return;
        }
        startActivity(new Intent(this,SignUpActivity.class));
    }

    private boolean checkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if(info == null)
            return false;
        return info.isConnected();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in:
                signIn();
                break;
            case R.id.sign_up:
                signUp();
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user",user.getText().toString().trim());
        editor.putString("password",password.getText().toString().trim());
        editor.apply();
        super.onDestroy();
    }
}
