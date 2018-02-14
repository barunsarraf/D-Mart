package deal.mart;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText profileName,profilePassword,profileMail,profileMobile,profileAddress;
    private ImageView profileUpdate,profileEdit;
    private ProgressDialog dialog;
    private TextView profileUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dialog = new ProgressDialog(this,ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Edit Profile");
        dialog.setMessage("Processing ...");

        profileUser = (TextView) findViewById(R.id.profile_user);

        profileUpdate = (ImageView) findViewById(R.id.profile_update);
        profileEdit = (ImageView) findViewById(R.id.profile_edit);


        profileUpdate.setOnClickListener(this);
        profileEdit.setOnClickListener(this);

        profileName = (EditText) findViewById(R.id.profile_name);
        profilePassword = (EditText) findViewById(R.id.profile_password);
        profileMail = (EditText) findViewById(R.id.profile_mail);
        profileMobile = (EditText) findViewById(R.id.profile_mobile);
        profileAddress = (EditText) findViewById(R.id.profile_address);


        User user = SignInActivity.getUser();

        profileUser.setText("Profile User : "+SignInActivity.getUserName());

        profileName.setText(user.name);
        profilePassword.setText(user.password);
        profileMail.setText(user.mail);
        profileMobile.setText(user.mobile.replace("m",""));
        profileAddress.setText(user.address);

        disableEdit();
    }

    private void disableEdit(){

        profileName.setEnabled(false);
        profilePassword.setEnabled(false);
        profileMail.setEnabled(false);
        profileMobile.setEnabled(false);
        profileAddress.setEnabled(false);

        profileName.setTextColor(Color.WHITE);
        profilePassword.setTextColor(Color.WHITE);
        profileMail.setTextColor(Color.WHITE);
        profileMobile.setTextColor(Color.WHITE);
        profileAddress.setTextColor(Color.WHITE);

    }
    private void enableEdit(){


        profileName.setEnabled(true);
        profilePassword.setEnabled(true);
        profileMail.setEnabled(true);
        profileMobile.setEnabled(true);
        profileAddress.setEnabled(true);

        profileName.setTextColor(Color.BLACK);
        profilePassword.setTextColor(Color.BLACK);
        profileMail.setTextColor(Color.BLACK);
        profileMobile.setTextColor(Color.BLACK);
        profileAddress.setTextColor(Color.BLACK);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.profile_edit){
            enableEdit();
            Toast.makeText(this,"edit enabled",Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this,"edit disabled",Toast.LENGTH_LONG).show();
        disableEdit();
        if(profileName.getText().length()==0 || profilePassword.getText().length()==0 ||profileMail.getText().length()==0 || profileMobile.getText().length()==0 || profileAddress.getText().length()==0) {
            Toast.makeText(this,"improper details",Toast.LENGTH_LONG).show();
            return;
        }
        if(profilePassword.getText().length() <4){
            Toast.makeText(this,"bad password",Toast.LENGTH_LONG).show();
            return;
        }
        dialog.show();
        User user = new User(profileName.getText().toString().trim(),"image",profilePassword.getText().toString().trim(),profileMail.getText().toString().trim(),profileMobile.getText().toString().trim(),profileAddress.getText().toString().trim());
        SignInActivity.setUser(user);
        FirebaseDatabase.getInstance().getReference().child("user").child(SignInActivity.getUserName()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                if(task.isSuccessful())
                    Toast.makeText(ProfileActivity.this,"successfully edited",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
