package deal.mart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class LauncherActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager mpager;
    private int[] layouts={R.layout.first_slide,R.layout.second_slide,R.layout.third_slide,R.layout.fourth_slide,R.layout.fifth_slide};
    private SlideAdapter mpagerAdapter;
    private LinearLayout dotsLayout;
    private ImageView[] dots;
    private Button nextButton;
    int count=0;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        count=preferences.getInt("count",0);
        if(count!=0) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        }
        mpager = (ViewPager) findViewById(R.id.viewPager);
        nextButton=(Button)findViewById(R.id.next);
        nextButton.setOnClickListener(this);
        if(Build.VERSION.SDK_INT>19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        mpagerAdapter = new SlideAdapter(layouts,this);
        mpager.setAdapter(mpagerAdapter);
        dotsLayout = (LinearLayout)findViewById(R.id.dotsLayout);
        createdots(0);
        mpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                createdots(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("count",count+1);
        editor.commit();
    }
    @Override
    public void onClick(View v) {
        int page = mpager.getCurrentItem();
        if(page==4) {
            finish();
            startActivity(new Intent(this, SignInActivity.class));
        }
        else {
            mpager.setCurrentItem(page+1, true);
        }
    }
    private void createdots(int current_position) {
        if (dotsLayout != null)
            dotsLayout.removeAllViews();
        dots = new ImageView[layouts.length];
        for (int i = 0; i < layouts.length; i++) {
            dots[i] = new ImageView(this);
            if (i == current_position) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dots));
            } else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.default_dots));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 0, 4, 0);
            dotsLayout.addView(dots[i], params);
        }
    }
}
