package deal.mart;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.notification_toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.notification_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("notification").child(SignInActivity.getUserName());
        notificationAdapter = new NotificationAdapter(this,Notification.class,R.layout.notification_row,NotificationHolder.class,ref);
        recyclerView.setAdapter(notificationAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();
        return true;
    }
}
