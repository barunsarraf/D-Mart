package deal.mart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.io.ByteArrayOutputStream;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, OnSuccessListener<Void>, OnCompleteListener<Void>, OnFailureListener {


    private ProgressDialog dialog;
    private ImageView signup;
    private EditText email,password,user,userName,mobile,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dialog = new ProgressDialog(this,ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Registering User");
        dialog.setMessage("Processing ...");


        user = (EditText) findViewById(R.id._user);

        userName = (EditText) findViewById(R.id.user_name);
        email = (EditText) findViewById(R.id.user_mail);
        password = (EditText) findViewById(R.id.user_password);
        mobile = (EditText) findViewById(R.id.user_mobile);
        address = (EditText) findViewById(R.id.user_address);

        signup = (ImageView) findViewById(R.id.user_signup);
        signup.setOnClickListener(this);
    }

    private void signUp(){
        if(email.getText().length()==0 || userName.getText().length()==0 ||user.getText().length()==0 || password.getText().length()==0 || mobile.getText().length()==0 ||address.getText().length()==0 ) {
            Toast.makeText(this,"improper details",Toast.LENGTH_LONG).show();
            return;
        }
        if(password.getText().length() <4){
            Toast.makeText(this,"insecure password",Toast.LENGTH_LONG).show();
            return;
        }
        //byte [] bytes = decodeBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.other));
        FirebaseDatabase.getInstance().getReference().child("user").child(user.getText().toString().trim()).setValue(new User(userName.getText().toString().trim(),"image@"+user.getText().toString().trim(),password.getText().toString().trim(),email.getText().toString().trim(),"m"+mobile.getText().toString().trim(),address.getText().toString().trim())).addOnCompleteListener(this).addOnSuccessListener(this).addOnFailureListener(this);
        //FirebaseStorage.getInstance().getReference().child("image").child("image@"+user.getText().toString().trim()).putBytes(bytes);
        dialog.show();
    }
    /*
    private byte [] decodeBitmap(Bitmap bitmap){
        float scale = 256.0f/bitmap.getHeight();
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth()*scale),256,false);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
    */
    @Override
    public void onClick(View v) {
        signUp();
    }


    @Override
    public void onSuccess(Void aVoid) {
        Toast.makeText(this,"successfully registered",Toast.LENGTH_LONG).show();
        SignInActivity.setUserName(userName.getText().toString().trim());
        startActivity(new Intent(this,SignInActivity.class));
        dialog.dismiss();
        finish();
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        dialog.dismiss();
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(this,"login failed",Toast.LENGTH_LONG).show();
        dialog.dismiss();
    }
}
