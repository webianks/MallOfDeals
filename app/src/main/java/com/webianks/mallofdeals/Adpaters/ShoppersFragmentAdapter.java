package com.webianks.mallofdeals.Adpaters;



import com.webianks.mallofdeals.Shoppers.ShopperCoupons;
import com.webianks.mallofdeals.Shoppers.ShopperEvents;
import com.webianks.mallofdeals.Shoppers.ShopperSales;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ShoppersFragmentAdapter extends FragmentStatePagerAdapter{

    public ShoppersFragmentAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

     


      @Override
    public Fragment getItem(int position) 
    {
        // TODO Auto-generated method stub
        Fragment fragment = new ShopperCoupons();

        switch(position){

        case 0:
            fragment = new ShopperSales();

            break;
        case 1:
            fragment = new ShopperEvents();
            break;
        case 2:
            fragment = new ShopperCoupons();
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
