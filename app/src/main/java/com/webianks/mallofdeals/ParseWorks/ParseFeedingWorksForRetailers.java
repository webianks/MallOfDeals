package com.webianks.mallofdeals.ParseWorks;


import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.webianks.mallofdeals.R;
import com.webianks.mallofdeals.Retailers.RetailerCouponsSetterGetter;
import com.webianks.mallofdeals.Retailers.RetailerEvents;
import com.webianks.mallofdeals.Retailers.RetailerCoupons;
import com.webianks.mallofdeals.Retailers.RetailerEvents;
import com.webianks.mallofdeals.Retailers.RetailerEventsSetterGetter;
import com.webianks.mallofdeals.Retailers.RetailerSales;
import com.webianks.mallofdeals.Retailers.RetailerSalesSetterGetter;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class ParseFeedingWorksForRetailers {

	public static void uploadPostToParse(final Context con, String user,
			String postQue, Bitmap bitmap,String what) {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
	    byte[] image = stream.toByteArray();

		ParseFile file = new ParseFile("post.png", image);
		file.saveInBackground();


		long now = System.currentTimeMillis();



        String inTable="";
        String titleColoumn="";
        String imageColoumn="";


        if(what.contains("sale")){

            inTable="Sales";
            titleColoumn="salesName";
            imageColoumn="ImageFile";

        }else if(what.contains("event")){

            inTable="Events";
            titleColoumn="eventName";
            imageColoumn="ImageFile";

        }else if(what.contains("coupon")){

            inTable="Coupons";
            titleColoumn="couponName";
            imageColoumn="ImageFile";

        }




		ParseObject webiObject = new ParseObject(inTable);
		webiObject.put(titleColoumn,postQue);
        webiObject.put("user",user);
		webiObject.put(imageColoumn,file);


		Drawable drawable = con.getResources().getDrawable(
				R.mipmap.ic_launcher);
		BitmapDrawable bdrawable = (BitmapDrawable) drawable;
		Bitmap icon = bdrawable.getBitmap();

		final int id = 1;
		final NotificationManager mNotifyManager = (NotificationManager) con
				.getSystemService(Context.NOTIFICATION_SERVICE);
		final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				con);
		mBuilder.setContentTitle("Mall Of Deals")
				.setContentText("Post upload in progress.").setLargeIcon(icon)
				.setSmallIcon(R.drawable.ic_mini);

		mBuilder.setProgress(0, 0, true);
		// Issues the notification
		mNotifyManager.notify(id, mBuilder.build());

		webiObject.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				// TODO Auto-generated method stub
				if (e == null) {

					mBuilder.setContentText("Upload complete")
					// Removes the progress bar
							.setProgress(0, 0, false);
					mNotifyManager.notify(id, mBuilder.build());
					//Post_Activity.parseSavingCallback(true, con);
				} else {

					mBuilder.setContentText("Failed to upload.")
					// Removes the progress bar
							.setProgress(0, 0, false);
					mNotifyManager.notify(id, mBuilder.build());
					//Post_Activity.parseSavingCallback(false, con);
				}
			}

		});

	}



	public static void retrievePostFromParse(final boolean is_refreshing,String user) {

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
        query.whereEqualTo("user",user);
		 query.findInBackground(new FindCallback<ParseObject>() {

			public void done(List<ParseObject> posts, ParseException e) {

				final List<RetailerEventsSetterGetter> SetterGetterClassList = new ArrayList<RetailerEventsSetterGetter>();
                RetailerEventsSetterGetter setterGetter = null;

				if (e == null && posts.size() != 0) {


                    ParseObject.pinAllInBackground(posts);

					for (int i = posts.size()-1; i >= 0; i--) {




						setterGetter = new RetailerEventsSetterGetter();
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


                        RetailerEvents.parseRetreivingCallback(
                                SetterGetterClassList, is_refreshing, " ");
					}

					else{

						RetailerEvents.parseRetreivingCallback(
                                SetterGetterClassList, is_refreshing," ");
					}

				} else {
                    RetailerEvents.parseRetreivingCallback(null, is_refreshing," ");
				}
			}
		});

	}


    public static void retrieveRetailerCouponsFromParse(final boolean is_refreshing, String user) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Coupons");

        query.whereEqualTo("user",user);
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> posts, ParseException e) {

                final List<RetailerCouponsSetterGetter> SetterGetterClassList = new ArrayList<RetailerCouponsSetterGetter>();
                RetailerCouponsSetterGetter setterGetter = null;

                if (e == null && posts.size() != 0) {

                    ParseObject.pinAllInBackground(posts);

                    for (int i = posts.size()-1; i >= 0; i--) {




                        setterGetter = new RetailerCouponsSetterGetter();
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


                        RetailerCoupons.parseRetreivingCallback(
                                SetterGetterClassList, is_refreshing, " ");
                    }

                    else{

                        RetailerCoupons.parseRetreivingCallback(
                                SetterGetterClassList, is_refreshing," ");
                    }

                } else {
                        RetailerCoupons.parseRetreivingCallback(null, is_refreshing," ");
                }
            }
        });

    }





    public static void retrieveRetailerSalesFromParse(final boolean is_refreshing,String user) {


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Sales");

        query.whereEqualTo("user",user);
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> posts, ParseException e) {



                final List<RetailerSalesSetterGetter> SetterGetterClassList = new ArrayList<RetailerSalesSetterGetter>();
                RetailerSalesSetterGetter setterGetter = null;

                if (e == null && posts.size() != 0) {


                    ParseObject.pinAllInBackground(posts);

                    for (int i = posts.size()-1; i >= 0; i--) {




                        setterGetter = new RetailerSalesSetterGetter();
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


                        RetailerSales.parseRetreivingCallback(
                                SetterGetterClassList, is_refreshing, " ");
                    }

                    else{

                        RetailerSales.parseRetreivingCallback(
                                SetterGetterClassList, is_refreshing," ");
                    }

                } else {

                    RetailerSales.parseRetreivingCallback(null, is_refreshing, " ");
                }
            }
        });
    }





    public static void retrieveRetailerSalesLocally(final boolean is_refreshing,String user) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Sales");
        query.whereEqualTo("user",user);
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> posts, ParseException e) {

                final List<RetailerSalesSetterGetter> SetterGetterClassList = new ArrayList<RetailerSalesSetterGetter>();
                RetailerSalesSetterGetter setterGetter = null;

                if (e == null && posts.size() != 0) {



                    for (int i = posts.size()-1; i >= 0; i--) {
                        setterGetter = new RetailerSalesSetterGetter();
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


                        RetailerSales.parseRetreivingCallback(
                                SetterGetterClassList, is_refreshing, " ");
                    }

                    else{

                        RetailerSales.parseRetreivingCallback(
                                SetterGetterClassList, is_refreshing," ");
                    }

                } else {

                    RetailerSales.parseRetreivingCallback(null, is_refreshing, " ");
                }
            }
        });
    }




    public static void retrieveRetailerEventsLocally(final boolean is_refreshing,String user) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
        query.whereEqualTo("user",user);
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> posts, ParseException e) {

                final List<RetailerEventsSetterGetter> SetterGetterClassList = new ArrayList<RetailerEventsSetterGetter>();
                RetailerEventsSetterGetter setterGetter = null;

                if (e == null && posts.size() != 0) {


                    ParseObject.pinAllInBackground(posts);

                    for (int i = posts.size()-1; i >= 0; i--) {




                        setterGetter = new RetailerEventsSetterGetter();
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


                        RetailerEvents.parseRetreivingCallback(
                                SetterGetterClassList, is_refreshing, " ");
                    }

                    else{

                        RetailerEvents.parseRetreivingCallback(
                                SetterGetterClassList, is_refreshing," ");
                    }

                } else {
                    RetailerEvents.parseRetreivingCallback(null, is_refreshing," ");
                }
            }
        });
    }

    public static void retrieveRetailerCouponsLocally(final boolean is_refreshing,String user) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Coupons");
        query.whereEqualTo("user",user);
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> posts, ParseException e) {

                final List<RetailerCouponsSetterGetter> SetterGetterClassList = new ArrayList<RetailerCouponsSetterGetter>();
                RetailerCouponsSetterGetter setterGetter = null;

                if (e == null && posts.size() != 0) {

                    ParseObject.pinAllInBackground(posts);

                    for (int i = posts.size()-1; i >= 0; i--) {




                        setterGetter = new RetailerCouponsSetterGetter();
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


                        RetailerCoupons.parseRetreivingCallback(
                                SetterGetterClassList, is_refreshing, " ");
                    }

                    else{

                        RetailerCoupons.parseRetreivingCallback(
                                SetterGetterClassList, is_refreshing," ");
                    }

                } else {
                    RetailerCoupons.parseRetreivingCallback(null, is_refreshing," ");
                }
            }
        });

    }
}
