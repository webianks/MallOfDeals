package com.webianks.mallofdeals.ParseWorks;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.webianks.mallofdeals.R;
import com.webianks.mallofdeals.Shoppers.ShopperCoupons;
import com.webianks.mallofdeals.Shoppers.ShopperEvents;
import com.webianks.mallofdeals.Shoppers.ShopperSales;
import com.webianks.mallofdeals.Shoppers.ShoppersCouponsSetterGetter;
import com.webianks.mallofdeals.Shoppers.ShoppersEventsSetterGetter;
import com.webianks.mallofdeals.Shoppers.ShoppersSalesSetterGetter;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class ParseFeedingWorksForShoppers {


	public static void retrievePostFromParse(final boolean is_refreshing) {

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
		 query.findInBackground(new FindCallback<ParseObject>() {

			public void done(List<ParseObject> posts, ParseException e) {

				final List<ShoppersEventsSetterGetter> SetterGetterClassList = new ArrayList<ShoppersEventsSetterGetter>();
                ShoppersEventsSetterGetter setterGetter = null;

				if (e == null && posts.size() != 0) {


                    ParseObject.pinAllInBackground(posts);

					for (int i = posts.size()-1; i >= 0; i--) {




						setterGetter = new ShoppersEventsSetterGetter();
						ParseFile postImage = (ParseFile) posts.get(i).get(
								"ImageFile");
						String ImageUrl = postImage.getUrl().toString();

						String eventTitle = posts.get(i).getString("eventName");

                          Log.d("Webi",ImageUrl+eventTitle);


			     		setterGetter.setEventName(eventTitle);
						setterGetter.setEventImageURl(ImageUrl);


						SetterGetterClassList.add(setterGetter);
                        setterGetter = null;

					}
					if (!is_refreshing){


                        ShopperEvents.parseRetreivingCallback(
                                SetterGetterClassList, is_refreshing, " ");
					}
					
					else{

						ShopperEvents.parseRetreivingCallback(
                                SetterGetterClassList, is_refreshing," ");
					}

				} else {
                    ShopperEvents.parseRetreivingCallback(null, is_refreshing," ");
				}
			}
		});

	}





    public static void retrieveShopperCouponsFromParse(final boolean is_refreshing) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Coupons");

        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> posts, ParseException e) {

                final List<ShoppersCouponsSetterGetter> SetterGetterClassList = new ArrayList<ShoppersCouponsSetterGetter>();
                ShoppersCouponsSetterGetter setterGetter = null;

                if (e == null && posts.size() != 0) {

                    ParseObject.pinAllInBackground(posts);

                    for (int i = posts.size()-1; i >= 0; i--) {




                        setterGetter = new ShoppersCouponsSetterGetter();
                        ParseFile postImage = (ParseFile) posts.get(i).get(
                                "ImageFile");
                        String ImageUrl = postImage.getUrl().toString();

                        String eventTitle = posts.get(i).getString("couponName");

                        Log.d("Webi",ImageUrl+eventTitle);


                        setterGetter.setEventName(eventTitle);
                        setterGetter.setEventImageURl(ImageUrl);


                        SetterGetterClassList.add(setterGetter);
                        setterGetter = null;

                    }
                    if (!is_refreshing){


                        ShopperCoupons.parseRetreivingCallback(
                                SetterGetterClassList, is_refreshing, " ");
                    }

                    else{

                        ShopperCoupons.parseRetreivingCallback(
                                SetterGetterClassList, is_refreshing," ");
                    }

                } else {
                        ShopperCoupons.parseRetreivingCallback(null, is_refreshing," ");
                }
            }
        });

    }





    public static void retrieveShopperSalesFromParse(final boolean is_refreshing) {


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Sales");

        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> posts, ParseException e) {



                final List<ShoppersSalesSetterGetter> SetterGetterClassList = new ArrayList<ShoppersSalesSetterGetter>();
                ShoppersSalesSetterGetter setterGetter = null;

                if (e == null && posts.size() != 0) {


                    ParseObject.pinAllInBackground(posts);

                    for (int i = posts.size()-1; i >= 0; i--) {




                        setterGetter = new ShoppersSalesSetterGetter();
                        ParseFile postImage = (ParseFile) posts.get(i).get(
                                "ImageFile");
                        String ImageUrl = postImage.getUrl().toString();

                        String eventTitle = posts.get(i).getString("salesName");

                        Log.d("Webi",ImageUrl+eventTitle);


                        setterGetter.setEventName(eventTitle);
                        setterGetter.setEventImageURl(ImageUrl);


                        SetterGetterClassList.add(setterGetter);
                        setterGetter = null;

                    }
                    if (!is_refreshing){


                        ShopperSales.parseRetreivingCallback(
                                SetterGetterClassList, is_refreshing, " ");
                    }

                    else{

                        ShopperSales.parseRetreivingCallback(
                                SetterGetterClassList, is_refreshing," ");
                    }

                } else {

                    ShopperSales.parseRetreivingCallback(null, is_refreshing, " ");
                }
            }
        });
    }




    public static void retrieveShopperSalesLocally(final boolean is_refreshing) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Sales");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> posts, ParseException e) {

                final List<ShoppersSalesSetterGetter> SetterGetterClassList = new ArrayList<ShoppersSalesSetterGetter>();
                ShoppersSalesSetterGetter setterGetter = null;

                if (e == null && posts.size() != 0) {



                    for (int i = posts.size()-1; i >= 0; i--) {
                        setterGetter = new ShoppersSalesSetterGetter();
                        ParseFile postImage = (ParseFile) posts.get(i).get(
                                "ImageFile");
                        String ImageUrl = postImage.getUrl().toString();

                        String eventTitle = posts.get(i).getString("salesName");

                        Log.d("Webi",ImageUrl+eventTitle);
                        setterGetter.setEventName(eventTitle);
                        setterGetter.setEventImageURl(ImageUrl);
                        SetterGetterClassList.add(setterGetter);
                        setterGetter = null;

                    }
                    if (!is_refreshing){


                        ShopperSales.parseRetreivingCallback(
                                SetterGetterClassList, is_refreshing, " ");
                    }

                    else{

                        ShopperSales.parseRetreivingCallback(
                                SetterGetterClassList, is_refreshing," ");
                    }

                } else {

                    ShopperSales.parseRetreivingCallback(null, is_refreshing, " ");
                }
            }
        });
    }




    public static void retrieveShopperEventsLocally(final boolean is_refreshing) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> posts, ParseException e) {

                final List<ShoppersEventsSetterGetter> SetterGetterClassList = new ArrayList<ShoppersEventsSetterGetter>();
                ShoppersEventsSetterGetter setterGetter = null;

                if (e == null && posts.size() != 0) {


                    ParseObject.pinAllInBackground(posts);

                    for (int i = posts.size()-1; i >= 0; i--) {




                        setterGetter = new ShoppersEventsSetterGetter();
                        ParseFile postImage = (ParseFile) posts.get(i).get(
                                "ImageFile");
                        String ImageUrl = postImage.getUrl().toString();

                        String eventTitle = posts.get(i).getString("eventName");

                        Log.d("Webi",ImageUrl+eventTitle);


                        setterGetter.setEventName(eventTitle);
                        setterGetter.setEventImageURl(ImageUrl);


                        SetterGetterClassList.add(setterGetter);
                        setterGetter = null;

                    }
                    if (!is_refreshing){


                        ShopperEvents.parseRetreivingCallback(
                                SetterGetterClassList, is_refreshing, " ");
                    }

                    else{

                        ShopperEvents.parseRetreivingCallback(
                                SetterGetterClassList, is_refreshing," ");
                    }

                } else {
                    ShopperEvents.parseRetreivingCallback(null, is_refreshing," ");
                }
            }
        });
    }

    public static void retrieveShopperCouponsLocally(final boolean is_refreshing) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Coupons");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> posts, ParseException e) {

                final List<ShoppersCouponsSetterGetter> SetterGetterClassList = new ArrayList<ShoppersCouponsSetterGetter>();
                ShoppersCouponsSetterGetter setterGetter = null;

                if (e == null && posts.size() != 0) {

                    ParseObject.pinAllInBackground(posts);

                    for (int i = posts.size()-1; i >= 0; i--) {




                        setterGetter = new ShoppersCouponsSetterGetter();
                        ParseFile postImage = (ParseFile) posts.get(i).get(
                                "ImageFile");
                        String ImageUrl = postImage.getUrl().toString();

                        String eventTitle = posts.get(i).getString("couponName");

                        Log.d("Webi",ImageUrl+eventTitle);


                        setterGetter.setEventName(eventTitle);
                        setterGetter.setEventImageURl(ImageUrl);


                        SetterGetterClassList.add(setterGetter);
                        setterGetter = null;

                    }
                    if (!is_refreshing){


                        ShopperCoupons.parseRetreivingCallback(
                                SetterGetterClassList, is_refreshing, " ");
                    }

                    else{

                        ShopperCoupons.parseRetreivingCallback(
                                SetterGetterClassList, is_refreshing," ");
                    }

                } else {
                    ShopperCoupons.parseRetreivingCallback(null, is_refreshing," ");
                }
            }
        });

    }
}
