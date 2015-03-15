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
import com.webianks.mallofdeals.Shoppers.ShopperEvents;
import com.webianks.mallofdeals.Shoppers.ShoppersEventsSetterGetter;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class ParseFeedingWorks {

	public static void uploadPostToParse(final Context con, String user,
			String postQue, Bitmap bitmap) {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
	    byte[] image = stream.toByteArray();

		ParseFile file = new ParseFile("post.png", image);
		file.saveInBackground();

		
		long now = System.currentTimeMillis();
		
		ParseObject webiObject = new ParseObject("Posts");
		webiObject.put("User", user);
		webiObject.put("PostTime",now);
		webiObject.put("ImageName", "post_image_name");
		webiObject.put("ImageFile", file);
		webiObject.put("TotalHit",0);

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
				.setSmallIcon(R.mipmap.ic_launcher);

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







	public static void retrievePostFromParse(final boolean is_refreshing) {

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");

		 query.findInBackground(new FindCallback<ParseObject>() {

			public void done(List<ParseObject> posts, ParseException e) {

				final List<ShoppersEventsSetterGetter> SetterGetterClassList = new ArrayList<ShoppersEventsSetterGetter>();
                ShoppersEventsSetterGetter setterGetter = null;

				if (e == null && posts.size() != 0) {



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
	


}
