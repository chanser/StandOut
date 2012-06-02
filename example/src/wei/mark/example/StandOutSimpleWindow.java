package wei.mark.example;

import wei.mark.standout.StandOutWindow;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class StandOutSimpleWindow extends StandOutWindow {

	@Override
	protected View createAndAttachView(final int id, ViewGroup root) {
		// create a new layout from body.xml
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.simple, root, true);
		return view;
	}

	// the window will be 200x200
	@Override
	protected LayoutParams getParams(int id, View view) {
		return new LayoutParams(200, 200);
	}

	// move the window by dragging the view
	@Override
	protected int getFlags(int id) {
		return super.getFlags(id) | FLAG_BODY_MOVE_ENABLE;
	}

	// close the window on tap
	@Override
	protected boolean onTouchBody(int id, View window, View view,
			MotionEvent event) {
		WindowTouchInfo touchInfo = ((WrappedTag) window.getTag()).touchInfo;

		if (event.getAction() == MotionEvent.ACTION_UP) {
			// tap
			if (touchInfo.deltaX == 0 && touchInfo.deltaY == 0) {
				hide(id);
			}
		}

		return true;
	}

	@Override
	protected Notification getPersistentNotification(int id) {
		// basic notification stuff
		// http://developer.android.com/guide/topics/ui/notifiers/notifications.html
		int icon = android.R.drawable.ic_menu_close_clear_cancel;
		long when = System.currentTimeMillis();
		Context c = getApplicationContext();
		String contentTitle = "Simple Window Example";
		String contentText = "Click to close the StandOutSimpleWindow.";
		String tickerText = String.format("%s: %s", contentTitle, contentText);

		Intent notificationIntent = StandOutWindow.getCloseIntent(this,
				StandOutSimpleWindow.class, id);

		PendingIntent contentIntent = PendingIntent.getService(this, 0,
				notificationIntent,
				// flag updates existing persistent notification
				PendingIntent.FLAG_UPDATE_CURRENT);

		Notification notification = new Notification(icon, tickerText, when);
		notification.setLatestEventInfo(c, contentTitle, contentText,
				contentIntent);
		return notification;
	}

}
