package com.webianks.mallofdeals.ImageUtils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class MatrixMethods {

	
	
	
	public String mainMatrixMethod(Context con,Uri selected_uri){
		
		String[] filePathColumn = { MediaStore.Images.Media.DATA };

		Cursor cursor = con.getContentResolver().query(selected_uri,
				filePathColumn, null, null, null);
		cursor.moveToFirst();
		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String filePath = cursor.getString(columnIndex);
		cursor.close();
		
		return filePath;

	}
	
	
	
}
