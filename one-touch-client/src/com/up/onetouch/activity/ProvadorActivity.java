package com.up.onetouch.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Fullscreen;
import com.googlecode.androidannotations.annotations.NoTitle;
import com.googlecode.androidannotations.annotations.ViewById;

import com.up.onetouch.R;
import com.up.onetouch.utils.CropOption;
import com.up.onetouch.utils.CropOptionAdapter;
import com.up.onetouch.utils.MultiTouch;

import eu.janmuller.android.simplecropimage.CropImage;

@EActivity(R.layout.provador)
@NoTitle
@Fullscreen
public class ProvadorActivity extends Activity  {

	private static final int PICK_FROM_CAMERA = 1;
	private static final int REQUEST_CODE_CROP_IMAGE = 2;
	private static final int PICK_FROM_FILE = 3;

	private Uri mImageCaptureUri;

	@ViewById(R.id.iv_photo)
	ImageView imgSelected;

	@ViewById(R.id.editor)
	FrameLayout layoutEditor;

	@ViewById(R.id.catalogo)
	LinearLayout catalogo;

	AlertDialog dialog;
	
	@AfterViews
	void afterViews() {
		
		final String[] items = new String[] { "Camera", "Galeria de Imagens" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, items);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Selecionar imagem");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {

				if (item == 0) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

					mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));

					intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,mImageCaptureUri);

					try {
						intent.putExtra("return-data", true);

						startActivityForResult(intent, PICK_FROM_CAMERA);
					} catch (ActivityNotFoundException e) {
						e.printStackTrace();
					}
				} else {
					Intent intent = new Intent();

					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);

					startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
				}
			}
		});

		if(dialog == null){
		
			for (int x = 1; x < 50; x++) {
				int resourceId = 0;
				String imgNum = String.format("%02d", x);
				resourceId = getResources().getIdentifier("img" + imgNum, "drawable", "com.up.onetouch");
				if (resourceId != 0) {
					catalogo.addView(insertImage(resourceId));
				}
			}
			
		}
		
		dialog = builder.create();
		
		

		layoutEditor.setOnDragListener(new OnDragListener() {

			@Override
			public boolean onDrag(View v, DragEvent event) {
				
				switch (event.getAction()) {	
					case DragEvent.ACTION_DROP:
						
						ImageView imgView = (ImageView) event.getLocalState();						
						ViewGroup owner = (ViewGroup) imgView.getParent();
						owner.removeView(imgView);
						ViewGroup owner2 = (ViewGroup) owner.getParent();
						owner2.removeView(owner);
						
						layoutEditor.addView(imgView);
						
						imgView.setX(0);
						imgView.setY(0);
						
//						float x = event.getX() - (imgView.getWidth() / 2);
//						float y = event.getY() - (imgView.getHeight() / 2);
			        	       
						Matrix matrix = imgView.getMatrix();
				        RectF drawableRect = new RectF(0, 0, imgView.getDrawable().getIntrinsicWidth(), imgView.getDrawable().getIntrinsicHeight());
						RectF viewRect = new RectF(0, 0, 160, 190);
						matrix.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER);
												
						imgView.setOnTouchListener(new MultiTouch(matrix));
						imgView.setVisibility(View.VISIBLE);
																		
						break;
					default:
						break;
				}
				return true;
			}
		});
	}

	View insertImage(int img) {

		int w = 160;
		int h = 190;

		BitmapDrawable bmp = (BitmapDrawable) getResources().getDrawable(img);
		LinearLayout layout = new LinearLayout(getApplicationContext());
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 250));
		layout.setGravity(Gravity.CENTER);

		ImageView imageView = new ImageView(getApplicationContext());
		imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		imageView.setImageBitmap(bmp.getBitmap());

		imageView.setScaleType(ImageView.ScaleType.MATRIX);

		Matrix m = imageView.getImageMatrix();

		RectF drawableRect = new RectF(0, 0, imageView.getDrawable()
				.getIntrinsicWidth(), imageView.getDrawable()
				.getIntrinsicHeight());
		RectF viewRect = new RectF(0, 0, w, h);
		m.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER);
		imageView.setImageMatrix(m);

		imageView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					ClipData data = ClipData.newPlainText("", "");
					DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
					v.startDrag(data, shadowBuilder, v, 0);
					v.setVisibility(View.INVISIBLE);
					return true;
				} else {
					return false;
				}
			}
		});

		layout.addView(imageView);
		return layout;
	}

	@Click(R.id.iv_photo)
	void buttonCropClick() {
		dialog.show();
	}
	
	private void doCrop() {
		final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();

		Intent intent = new Intent(this, CropImage.class);
		
		intent.putExtra(CropImage.IMAGE_PATH, mImageCaptureUri);

	    // allow CropImage activity to rescale image
	    intent.putExtra(CropImage.SCALE, true);

	    // if the aspect ratio is fixed to ratio 3/2
	    intent.putExtra(CropImage.ASPECT_X, 3);
	    intent.putExtra(CropImage.ASPECT_Y, 2);
	    
	    startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK)
			return;

		switch (requestCode) {
		case PICK_FROM_CAMERA:
			doCrop();

			break;

		case PICK_FROM_FILE:
			mImageCaptureUri = data.getData();

			doCrop();

			break;

		case REQUEST_CODE_CROP_IMAGE:
/*			Bundle extras = data.getExtras();

			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");

				imgSelected.setImageBitmap(photo);

				imgSelected.setPadding(0, 0, 0, 0);
			}

			File f = new File(mImageCaptureUri.getPath());

			if (f.exists())
				f.delete();
*/
			
			String path = data.getStringExtra(CropImage.IMAGE_PATH);

            // if nothing received
            if (path == null) {

                return;
            }

            // cropped bitmap
            Bitmap bitmap = BitmapFactory.decodeFile(mImageCaptureUri.getPath());
			
			break;
		}
	}
}