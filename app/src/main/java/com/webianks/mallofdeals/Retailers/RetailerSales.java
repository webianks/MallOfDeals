package com.webianks.mallofdeals.Retailers;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.ParseUser;
import com.webianks.mallofdeals.Network.IsConnectedToNetwork;
import com.webianks.mallofdeals.ParseWorks.ParseFeedingWorksForRetailers;
import com.webianks.mallofdeals.ParseWorks.ParseFeedingWorksForShoppers;
import com.webianks.mallofdeals.R;

import net.i2p.android.ext.floatingactionbutton.FloatingActionButton;

import java.util.List;

import tr.xip.errorview.ErrorView;
import tr.xip.errorview.RetryListener;


public class RetailerSales extends Fragment {

    private static ListView mListView;
    private static Context con;
    private static Activity activity;
    static List<RetailerSalesSetterGetter> SetterGetterClassList;
    static RetailerSalesAdapter shopperSalesAdapter = null;
    static ProgressBar mProgressBar;
    private static RelativeLayout mainContent;
    private static SwipeRefreshLayout swipeLayout;
    private static ErrorView errorView;
    private SharedPreferences prefs = null;

    FloatingActionButton newSale;
    FloatingActionButton newEvent;
    FloatingActionButton newCoupon;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_retailer_sales, container, false);

        mListView = (ListView) view.findViewById(R.id.main_list_view);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        errorView= (ErrorView) view.findViewById(R.id.error_view_sales);

       newSale = (FloatingActionButton) view.findViewById(R.id.new_sale_button);
       newEvent = (FloatingActionButton) view.findViewById(R.id.new_event_button);
       newCoupon = (FloatingActionButton) view.findViewById(R.id.new_coupon_button);

        mProgressBar.setVisibility(View.VISIBLE);

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                ParseFeedingWorksForRetailers.retrieveRetailerSalesFromParse(true);
                swipeLayout.setRefreshing(true);
            }

        });
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        newSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(),PostActivity.class);
                intent.putExtra("username",ParseUser.getCurrentUser().toString());
                intent.putExtra("inTable","sale");
                startActivity(intent);

            }
        });


        newEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(),PostActivity.class);

                intent.putExtra("username",ParseUser.getCurrentUser().toString());
                intent.putExtra("inTable","event");
                startActivity(intent);
            }
        });

        newCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(),PostActivity.class);
                intent.putExtra("username",ParseUser.getCurrentUser().toString());
                intent.putExtra("inTable","coupon");
                startActivity(intent);
            }
        });

        return view;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        con = getActivity();
        activity= getActivity();

        prefs = getActivity().getSharedPreferences("com.webianks.mallofdeals", Context.MODE_PRIVATE);


        if (prefs.getBoolean("firstrun", true) && savedInstanceState==null) {
            // Do first run stuff here then set 'firstrun' as false

            if(IsConnectedToNetwork.isConnectedNet(getActivity())) {
                ParseFeedingWorksForRetailers.retrievePostFromParse(false);
                prefs.edit().putBoolean("firstrun", false).commit();
            }

        }else{
            //subsequent run of app.

            ParseFeedingWorksForRetailers.retrieveRetailerSalesLocally(false);
            //now check for the new content
            if(IsConnectedToNetwork.isConnectedNet(getActivity())) {
                ParseFeedingWorksForRetailers.retrievePostFromParse(true);
            }
        }



    }





    public static void parseRetreivingCallback(

            List<RetailerSalesSetterGetter> sgClassList,
            Boolean refresh_code,
            String message) {


        if (!refresh_code) {
            if (sgClassList != null) {

                SetterGetterClassList = sgClassList;

                shopperSalesAdapter = new RetailerSalesAdapter(con, activity,
                        SetterGetterClassList);
                mListView.setAdapter(shopperSalesAdapter);
                mProgressBar.setVisibility(View.INVISIBLE);

            } else {
                mProgressBar.setVisibility(View.INVISIBLE);
                errorView.setVisibility(View.VISIBLE);
                errorView.setError(504);
                errorView.setOnRetryListener(new RetryListener() {
                    @Override
                    public void onRetry() {

                        retry();
                    }

                    private void retry() {
                        mProgressBar.setVisibility(View.VISIBLE);
                        errorView.setVisibility(View.INVISIBLE);
                        ParseFeedingWorksForShoppers.retrieveShopperSalesFromParse(true);
                    }
                });
            }
        } else {
            //List now need to be refreshed.

            if (sgClassList != null) {

                if (shopperSalesAdapter != null) {

                    if(SetterGetterClassList!=null){
                        SetterGetterClassList.clear();
                        SetterGetterClassList.addAll(sgClassList);
                    }

                    shopperSalesAdapter.notifyDataSetChanged();

                } else {
                    mListView.setAdapter(shopperSalesAdapter);
                    mainContent.setVisibility(View.VISIBLE);
                }

                swipeLayout.setRefreshing(false);


            } else {
                swipeLayout.setRefreshing(false);
                Toast.makeText(con, "Failed to update! Check network connection.", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }




}
