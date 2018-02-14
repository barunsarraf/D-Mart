package deal.mart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    private TextView user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        user = (TextView) findViewById(R.id.welcome_user);
        user.setText(SignInActivity.getUser().name);
        user.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this,ListActivity.class));
                finish();
            }
        },500);
    }
}
