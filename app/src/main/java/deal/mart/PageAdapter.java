package deal.mart;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Admin on 9/30/2017.
 */

public class PageAdapter extends PagerAdapter {

    private Context context;
    private int [] resId = {R.drawable.page_book,R.drawable.page_cycle,R.drawable.page_laptop,R.drawable.page_smartphone,R.drawable.page_watch,R.drawable.page_other};

    public PageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return resId.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_page , container,false);
        ImageView pageImage = (ImageView) view.findViewById(R.id.page_image);
        pageImage.setImageResource(resId[position]);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
