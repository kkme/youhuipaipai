package frame.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Utils {
	public static void call(Context context, String number) {
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
				+ number));
		context.startActivity(intent);

	}
}
