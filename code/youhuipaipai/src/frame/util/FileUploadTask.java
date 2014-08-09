package frame.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class FileUploadTask extends AsyncTask<Object, Integer, String> {

	private ProgressDialog dialog = null;
	HttpURLConnection connection = null;
	DataOutputStream outputStream = null;
	DataInputStream inputStream = null;
	String end = "\r\n";// 回车换行符
	String twoHyphens = "--";// 分隔符前后缀
	String boundary = "*****";// 分隔符
	String filePath;
	Context context;
	String actionUrl;

	public FileUploadTask(String filePath, Context context, String actionUrl) {
		this.filePath = filePath;
		this.context = context;
		this.actionUrl = actionUrl;
	}

	File uploadFile = new File(filePath);
	long totalSize = uploadFile.length(); // Get size of file, bytes

	@Override
	protected void onPreExecute() {
		dialog = new ProgressDialog(context);
		dialog.setTitle("正在上传...");
		dialog.setMessage("0k/" + totalSize / 1000 + "k");
		dialog.setIndeterminate(false);
		// dialog.setCancelable(false);别加这个有时候文件大一点，网速慢点，会卡死到那。
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setProgress(0);
		dialog.show();
	}

	@Override
	protected String doInBackground(Object... arg0) {
		String result = "0";
		long length = 0;
		int progress;
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 10 * 1024;// 10KB
		try {
			try {
				URL url = new URL(actionUrl);
				connection = (HttpURLConnection) url.openConnection();
				connection.setChunkedStreamingMode(128 * 1024);// 128KB
				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.setUseCaches(false);
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Connection", "Keep-Alive");
				connection.setRequestProperty("Charset", "UTF-8");
				connection.setRequestProperty("Content-Type",
						"multipart/form-data;boundary=" + boundary);
				outputStream = new DataOutputStream(
						connection.getOutputStream());
				/*
				 * // 写入普通属性 if (params != null) { Set<String> keys =
				 * params.keySet(); for (Iterator<String> it = keys.iterator();
				 * it.hasNext();) { String key = it.next(); String value =
				 * params.get(key); outputStream.writeBytes(twoHyphens +
				 * boundary + end); outputStream
				 * .writeBytes("Content-Disposition: form-data; " + "name=\"" +
				 * key + "\"" + end); outputStream.writeBytes(end);
				 * outputStream.writeBytes(value); outputStream.writeBytes(end);
				 * } }
				 */
				// 文件写入
				outputStream.writeBytes(twoHyphens + boundary + end);
				outputStream.writeBytes("Content-Disposition: form-data; "
						+ "name=\"" + "picFile" + "\";filename=\""
						+ new File(filePath).getName() + "\"" + end);
				outputStream.writeBytes(end);
				FileInputStream fileInputStream = new FileInputStream(new File(
						filePath));
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);// 设置每次写入的大小
				buffer = new byte[bufferSize];
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				while (bytesRead > 0) {
					outputStream.write(buffer, 0, bufferSize);
					length += bufferSize;
					Thread.sleep(500);
					progress = (int) ((length * 100) / totalSize);
					publishProgress(progress, (int) length);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				}
				outputStream.writeBytes(end);
				outputStream.writeBytes(twoHyphens + boundary + twoHyphens
						+ end);
				publishProgress(100, (int) length);
				InputStream is = connection.getInputStream();
				int ch;
				StringBuffer b = new StringBuffer();
				while ((ch = is.read()) != -1) {
					b.append((char) ch);
				}
				result = b.toString().trim();
				Log.d("UI", "result______________________" + result);
				org.json.JSONObject obj = new org.json.JSONObject(result);
				boolean r = obj.optBoolean("issuccess");
				if (r) {
					result = "1";
				} else {
					result = "2";
				}
				is.close();
				fileInputStream.close();
				outputStream.flush();
				outputStream.close();
			} catch (Exception ex) {

				dismissDialog();
				ex.printStackTrace();
			}
		} catch (Error e) {
			dismissDialog();
			Writer writer = new StringWriter();
			PrintWriter pw = new PrintWriter(writer);
			e.printStackTrace(pw);
			pw.close();
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		dialog.setProgress(progress[0]);
		dialog.setMessage(progress[1] / 1000 + "k/" + totalSize / 1000 + "k");
	}
	@Override
	protected void onPostExecute(String result) {
		try {
			if ("1".equals(result)) {
				// handler.sendEmptyMessage(1);
				Toast.makeText(context, "上传成功!", Toast.LENGTH_LONG).show();
			} else {
				dialog.dismiss();
				Toast.makeText(context, "上传失败!", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {

		} finally {
			dismissDialog();
		}
	}

	private void dismissDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}

	}

}