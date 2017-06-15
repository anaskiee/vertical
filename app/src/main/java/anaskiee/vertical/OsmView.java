package anaskiee.vertical;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.HashMap;

import static android.os.Environment.getExternalStorageDirectory;

public class OsmView extends View implements OnTaskCompleted {

	private static final String TAG = "OsmView";
	private HashMap<String, Boolean> tilesAvailable;
	private HashMap<String, Bitmap> tilesBmp;

	public OsmView(Context context) {
		super(context);
	}

	public OsmView(Context context, AttributeSet attrs) {
		super(context, attrs);
		tilesAvailable =  new HashMap<>();
		tilesBmp =  new HashMap<>();
		tilesAvailable.put("0_0_0", false);
		new DownloadTileTask(this).execute(0, 0, 0);
	}

	public OsmView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void onTaskCompleted(String filename) {
		//String s = getContext().getExternalFilesDir("test").toString();
		String s = getContext().getFilesDir().toString();
		File directory = new File(s);
		File[] files = directory.listFiles();
		for (File file : files) {
			Log.i(TAG, "file: " + file.getName());
			Log.i(TAG, "file: " + file.getAbsolutePath());
			Log.i(TAG, "file: " + file.exists());
			Log.i(TAG, "file: " + file.length());
		}
		//File file = new File(getContext().getExternalFilesDir("test") + "/" + filename + ".png");
		File file = new File(getContext().getFilesDir() + "/" + filename + ".png");
		Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
		tilesBmp.put(filename, bmp);
		tilesAvailable.put(filename, true);
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		//Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		//paint.setARGB(255, 0, 0, 0);
		//canvas.drawCircle(250, 250, 50, paint);

		if (tilesAvailable.get("0_0_0")) {
			canvas.drawBitmap(tilesBmp.get("0_0_0"), 300, 300, null);
		}
	}
}
