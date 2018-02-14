package deal.mart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class ListActivity extends AppCompatActivity implements View.OnClickListener, DrawerLayout.DrawerListener, NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    private PageAdapter pageAdapter;
    private Timer timer;
    private ImageView profile,upload,home;
    private DrawerLayout drawer;
    private NavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Toolbar toolbar = (Toolbar) findViewById(R.id.list_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigation = (NavigationView) findViewById(R.id.navigation_view);

        profile = (ImageView) findViewById(R.id.profile);
        home = (ImageView) findViewById(R.id.home);
        upload = (ImageView) findViewById(R.id.upload);

        viewPager = (ViewPager) findViewById(R.id.page);

        drawer.addDrawerListener(this);

        navigation.setNavigationItemSelectedListener(this);

        profile.setOnClickListener(this);
        home.setOnClickListener(this);
        upload.setOnClickListener(this);

        setupHeader();
        setupViewPager();
        initRecycleView();

    }

    private void setupHeader(){

        View view = navigation.getHeaderView(0);
        ImageView user_icon = (ImageView) view.findViewById(R.id.drawer_profile_icon);
        TextView user_name = (TextView) view.findViewById(R.id.drawer_profile_username);
        TextView user_mail = (TextView) view.findViewById(R.id.drawer_profile_mail);

        User user = SignInActivity.getUser();

        user_name.setText(user.name);
        user_mail.setText(user.mail);
    }

    private void setupViewPager() {

        pageAdapter = new PageAdapter(this);
        viewPager.setAdapter(pageAdapter);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                viewPager.post(new Runnable(){
                    @Override
                    public void run() {
                        viewPager.setCurrentItem((viewPager.getCurrentItem()+1)%6);
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 3000, 3000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START, true);
                break;
            default:
        }
        return true;
    }

    private void initRecycleView(){

        ListManager.initAdapter(this);

        Integer [] recycle_id = {R.id.recycle_book,R.id.recycle_cycle,R.id.recycle_laptop,R.id.recycle_smartphone,R.id.recycle_watch,R.id.recycle_other};
        Integer [] index_id = {ListManager.BOOK,ListManager.CYCLE,ListManager.LAPTOP,ListManager.SMART_PHONE,ListManager.WATCH,ListManager.OTHER};
        Integer index = 0;
        for(Integer id : recycle_id){
            RecyclerView recyclerView = (RecyclerView) findViewById(id);
            recyclerView.setAdapter(ListManager.getAdapter(index_id[index]));
            recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
            index++;
        }
        Integer [] more_id = {R.id.more_book,R.id.more_cycle,R.id.more_laptop,R.id.more_smartphone,R.id.more_watch,R.id.more_other};
        for(Integer id : more_id){
            ImageView imageView = (ImageView) findViewById(id);
            imageView.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home:
                drawer.openDrawer(GravityCompat.START, true);
                return;
                //break;
            case R.id.upload:
                startActivity(new Intent(this,UploadActivity.class));
                return;
                //break;
            case R.id.profile:
                startActivity(new Intent(this,ProfileActivity.class));
                return;
                //break;
            default:
        }
        Intent intent = new Intent(this,TabActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_sale:
                startActivity(new Intent(this,SaleActivity.class));
                break;
            case R.id.nav_sign_in:
                startActivity(new Intent(this,SignInActivity.class));
                finish();
                break;
            case R.id.nav_setting:
                startActivity(new Intent(this,ProfileActivity.class));
                break;
            case R.id.nav_upload:
                startActivity(new Intent(this,UploadActivity.class));
                break;
            case R.id.nav_notification:
                startActivity(new Intent(this,NotificationActivity.class));
                break;
            default:
        }
        drawer.closeDrawer(GravityCompat.START,true);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START,true);
        else
            super.onBackPressed();
    }
}


