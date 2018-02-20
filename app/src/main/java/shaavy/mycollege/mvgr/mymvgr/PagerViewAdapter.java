package shaavy.mycollege.mvgr.mymvgr;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by AkshayeJH on 01/01/18.
 */

class PagerViewAdapter extends FragmentPagerAdapter{

    public PagerViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                UsersFragment usersFragment = new UsersFragment();
                return usersFragment;


            case 1:
                NotificationFragment notificationFragment = new NotificationFragment();
                return  notificationFragment;


            case 2:
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;

            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return 3;
    }

}
