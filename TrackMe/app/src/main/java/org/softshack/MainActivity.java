package org.softshack;

import com.example.coursework2.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private final String BAR_COLOUR = "#35A2C4";
	private final String GOOGLE_MAP_URL = "https://www.google.co.uk/maps/place/";
	private final String MESSAGE_LOCATION_UPDATED = "Location Updated";
	private final String MESSAGE_COORD_PROBLEM = "Problem with GPS, please click locate";
	private final String MESSAGE_REQEUST_PERMISSION = "Enable Location Services";

	private Toast toast;
	private TextView latitudeTextView;
	private TextView latitudeValueTextView;
	private TextView longitudeTextView;
	private TextView longitudeValueTextView;
	private TextView accuracyTextView;
	private TextView accuracyValueTextView;
	private TextView lastUpdateTextView;
	private TextView lastUpdateValueTextView;
	private Context context;
	private WebView webView;
	private Calendar calendar;
	private SimpleDateFormat dateFormat;

	/**
	 * Builds the user interface.
	 * @param Bundle savedInstanceState of the user interface
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(BAR_COLOUR)));
		context = getApplicationContext();

		View createButton = findViewById(R.id.locate_button);
		createButton.setOnClickListener(this);
		latitudeTextView = (TextView) findViewById(R.id.latitude_textiew);
		latitudeTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.define_location, 0, 0, 0);
		latitudeValueTextView = (TextView) findViewById(R.id.latitude_value_textiew);
		longitudeTextView = (TextView) findViewById(R.id.longitude_textiew);
		longitudeTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.define_location, 0, 0, 0);
		longitudeValueTextView = (TextView) findViewById(R.id.longitude_value_textiew);
		accuracyTextView = (TextView) findViewById(R.id.accuracy_textview);
		accuracyTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.info, 0, 0, 0);
		accuracyValueTextView = (TextView) findViewById(R.id.accuracy_value_textiew);
		lastUpdateTextView = (TextView) findViewById(R.id.last_update_view);
		lastUpdateValueTextView = (TextView) findViewById(R.id.last_update_date);

		webView = (WebView) findViewById(R.id.map_view);
		webView.setWebViewClient(new WebViewClient());
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setVisibility(0);

		isPermissionGiven();
	}

	public void onStart(Bundle savedInstanceState) {
		isPermissionGiven();
	}

	public void onResume(Bundle savedInstanceState) {
		isPermissionGiven();
	}

	/**
	 * Checks if location services are enabled. Notifies and directs user
	 * according to the status of the location service.
	 * @return boolean status of the location service
	 */
	private boolean isPermissionGiven() {
		String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if (!provider.equals("")) {
			return true;
		} else {
			displayMessage(MESSAGE_REQEUST_PERMISSION);
			Intent locationSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);// create new intent to view location settings
			startActivity(locationSettings);// start the activity for location settings
		}
		return false;
	}

	/**
	 * OnClickListener catches which button is pressed and takes action
	 * accordingly.
	 * 
	 * @param View button clicked
	 */
	@Override
	public void onClick(View button) {
		switch (button.getId()) {
		case R.id.locate_button:

			if (isPermissionGiven()) {
				GPS gps = new GPS(context);
				// convert returned GPS coordinates to strings
				//if (isCoordValid(gps.getLatitude(), gps.getLongitude())) {
					String latitude = String.valueOf(gps.getLatitude());
					String longitude = String.valueOf(gps.getLongitude());
					String accuracy = String.valueOf(gps.getAccuracy());
					// assign string values to UI components
					accuracyValueTextView.setText(accuracy);
					latitudeValueTextView.setText(latitude);
					longitudeValueTextView.setText(longitude);

					// set date and time for now
					calendar = Calendar.getInstance();
					dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					lastUpdateValueTextView.setText(dateFormat.format(calendar.getTime()));

					// check if internet is available and view map accordingly
					if (isInternetAvailable(context)) {
						webView.loadUrl(GOOGLE_MAP_URL + latitude + "," + longitude);
					} else {
						displayMessage("Map not available - No internet connection");
					}

					displayMessage(MESSAGE_LOCATION_UPDATED);
				//}
			}

			break;
		}
	}

	/**
	 * Checks if received coordinates are valid. Notifies user accordingly.
	 * 
	 * @return boolean validation of coords
	 */
	private boolean isCoordValid(double latitude, double longitude) {
		if (latitude == 0.0 || longitude == 0.0) {
			displayMessage(MESSAGE_COORD_PROBLEM);
			return false;
		}
		return true;
	}

	/**
	 * Takes a parameter message and displays to the user interface. Used to
	 * notify, warn user.
	 * 
	 * @param messsage
	 */
	public void displayMessage(String message) {
		toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
		toast.show();
	}
	
    /**
     * @returns boolean status of the Internet connectivity
     * */
    public boolean isInternetAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    /**
     * Calls the finish method and ends the activity.
     * */
	public void onDestroy() {
		super.onDestroy();
		finish();
	}

}