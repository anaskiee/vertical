package anaskiee.vertical;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadTileTask extends AsyncTask<Integer, Integer, String> {
	private static final String TAG = "DownloadTileTask";
	private OnTaskCompleted listener;

	public DownloadTileTask(OnTaskCompleted listener){
		this.listener=listener;
	}

	protected String doInBackground(Integer... i) {
		int z = i[0];
		int x = i[1];
		int y = i[2];
		String shortFilename = String.format("%d_%d_%d", z, x, y);
		String filename = String.format("%d_%d_%d.png", z, x, y);
		String urlAddress = String.format("http://a.tile.openstreetmap.org/%d/%d/%d.png", z, x, y);
		//File file = new File(listener.getContext().getExternalFilesDir("test"), filename);
		File file = new File(listener.getContext().getFilesDir(), filename);
		try (FileOutputStream fos = new FileOutputStream(file)) {
			URL url = new URL(urlAddress);
			try (InputStream is = url.openStream()) {
				int length;
				byte[] buffer = new byte[1024];
				while ((length = is.read(buffer)) > 0)
					fos.write(buffer, 0, length);
			} catch (IOException e) {
				Log.i(TAG, e.getMessage());
			}
		} catch (IOException e) {
			Log.i(TAG, e.getMessage());
		}
		return shortFilename;
	}

	protected void onPostExecute(String filename) {
		listener.onTaskCompleted(filename);
	}
}
