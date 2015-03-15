package com.webianks.mallofdeals.Adpaters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.webianks.mallofdeals.Retailers.RetailerCoupons;
import com.webianks.mallofdeals.Retailers.RetailerEvents;
import com.webianks.mallofdeals.Retailers.RetailerSales;


public class RetailerFragmentAdapter extends FragmentStatePagerAdapter{

    public RetailerFragmentAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

     


      @Override
    public Fragment getItem(int position) 
    {
        // TODO Auto-generated method stub
        Fragment fragment = new RetailerCoupons();

        switch(position){

        case 0:
            fragment = new RetailerSales();
            break;
        case 1:
            fragment = new RetailerEvents();
            break;
        case 2:
            fragment = new RetailerCoupons();
            break;
        }
        return fragment;
    }

      @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 3;
    }
    
      @Override
    public CharSequence getPageTitle(int position){
        String title = "";
        switch(position){
        case 0:
            title = "Sales";
            break;
        case 1:
            title = "Events";
            break;
        case 2:
            title = "Coupons";
            break;
        }
        return title;
    }

}
