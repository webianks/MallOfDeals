package com.webianks.mallofdeals.Retailers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.webianks.mallofdeals.R;

import java.util.List;


public class RetailerSalesAdapter extends BaseAdapter {

	private Context con;
	ImageLoader imageLoader;
	DisplayImageOptions options;
	private Activity activity;
	List<RetailerSalesSetterGetter> sgcl;
	Typeface tfRegular;
	Typeface tfSlab;

	public RetailerSalesAdapter(Context context, Activity activity,
                                List<RetailerSalesSetterGetter> sgcl
    ) {

		this.con = context;
		this.activity = activity;
		this.sgcl = sgcl;

		
		
		tfRegular= Typeface.createFromAsset(con.getAssets(),
					"fonts/Roboto-Regular.ttf");
		   
		tfSlab = Typeface.createFromAsset(con.getAssets(),
					"fonts/RobotoSlab-Regular.ttf");
	
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				con).build();

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);

		options = new DisplayImageOptions.Builder().cacheInMemory()
				.cacheOnDisc().build();

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return sgcl.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
    

		
		View view = null;
		ViewsHolder holder = null;

		if (convertView == null) {


			LayoutInflater inflater = (LayoutInflater) con
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.single_post_ui, parent, false);
			holder = new ViewsHolder();
			holder.imageView = (ImageView) view.findViewById(R.id.postImage);
			holder.tvQuestion = (TextView) view
					.findViewById(R.id.tvPstQuestion);

			view.setTag(holder);

		} else {
			view = convertView;
			holder = (ViewsHolder) view.getTag();
		}

		ImageLoadingListener listener = new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String arg0, View arg1) {

			}

			@Override
			public void onLoadingCancelled(String arg0, View arg1) {

			}

			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap bitmap) {

			}

			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {

			}

		};


		Uri imageUri = Uri.parse(sgcl.get(position).getEventImageURl());
		imageLoader.displayImage(imageUri.toString(), holder.imageView,
			options, listener);

		holder.tvQuestion.setTypeface(tfRegular);
		holder.tvQuestion.setText(sgcl.get(position).getEventName().toString());

		return view;
	}

	static class ViewsHolder {
		private ImageView imageView;
		private TextView tvQuestion;
	}

}