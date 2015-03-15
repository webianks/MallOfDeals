package com.webianks.mallofdeals.Shoppers;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.webianks.mallofdeals.ParseWorks.ParseFeedingWorks;
import com.webianks.mallofdeals.R;

import java.util.List;

import tr.xip.errorview.ErrorView;
import tr.xip.errorview.RetryListener;


public class ShopperCoupons extends Fragment {


    private static ListView mListView;
    private static Context con;
    private static Activity activity;
    static List<ShoppersCouponsSetterGetter> SetterGetterClassList;
    static ShopperCouponsAdapter shopperEventsAdapter = null;
    static ProgressBar mProgressBar;
    private static SwipeRefreshLayout swipeLayout;
    private static ErrorView errorView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopper_coupons, container, false);

        mListView = (ListView) view.findViewById(R.id.main_list_view);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        errorView= (ErrorView) view.findViewById(R.id.error_view);
        mProgressBar.setVisibility(View.VISIBLE);



        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                ParseFeedingWorks.retrieveShopperCouponsFromParse(true);
                swipeLayout.setRefreshing(true);
            }

        });
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return view;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        con = getActivity();
        activity= getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        ParseFeedingWorks.retrieveShopperCouponsFromParse(false);
    }




    public static void parseRetreivingCallback(

            List<ShoppersCouponsSetterGetter> sgClassList,
            Boolean refresh_code,
            String message) {



        if (sgClassList != null) {

            SetterGetterClassList = sgClassList;

            shopperEventsAdapter = new ShopperCouponsAdapter(con,activity,
                    SetterGetterClassList);
            mListView.setAdapter(shopperEventsAdapter);
            mProgressBar.setVisibility(View.INVISIBLE);

        }else{
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
                    ParseFeedingWorks.retrieveShopperCouponsFromParse(true);


                }
            });
        }
    }




}
