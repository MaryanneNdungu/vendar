package io.vaxly.venda.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import io.vaxly.venda.fragments.Browse;
import io.vaxly.venda.fragments.Chat;
import io.vaxly.venda.fragments.Search;

/**
 * Created by vaxly on 9/8/16.
 */
public class MyPager extends FragmentStatePagerAdapter {
    public MyPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new Browse();
        }else if (position == 1){
            return new Search();
        }else if (position == 2){
            return new Chat();
        }else if (position == 3){
            return new Chat();
        }else {
            return new Browse();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
