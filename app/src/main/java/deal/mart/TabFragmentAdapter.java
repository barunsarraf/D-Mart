package deal.mart;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Admin on 10/9/2017.
 */

public class TabFragmentAdapter extends FragmentStatePagerAdapter {

    private ArrayList<TabFragment> tabFragments;

    public TabFragmentAdapter(FragmentManager fm, TabManager tabManager) {
        super(fm);
        tabFragments = new ArrayList<>();
        for(TabAdapter  tabAdapter : tabManager.getAllAdapter()){
            TabFragment fragment = new TabFragment();
            fragment.setAdapter(tabAdapter);
            fragment.setContext(tabManager.getContext());
            tabFragments.add(fragment);
        }

    }

    @Override
    public Fragment getItem(int position) {
        return tabFragments.get(position);
    }

    @Override
    public int getCount() {
        return tabFragments.size();
    }


}
