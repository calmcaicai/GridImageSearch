package com.example.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {

	EditText etQuery;
	GridView gvImages;
	TextView tv;

	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;

	AsyncHttpClient client = new AsyncHttpClient();

	static int totalLoad = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvImages = (GridView) findViewById(R.id.gvImages);
		tv = (TextView) findViewById(R.id.tv);
		
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvImages.setAdapter(imageAdapter);
		gvImages.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View parent,
					int position, long rowId) {
				Intent i = new Intent(getApplicationContext(),
						ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
			}
		});

		gvImages.setOnScrollListener(new EndlessScrollListener(8, totalLoad) {
			@Override
			public void loadMore(int page, int totalItemsCount) {

				String query = etQuery.getText().toString();
				Log.v("Calm:::", "totalItemsCount = "+totalItemsCount +"page = " +page);
				loadImages(query);
			}
		});
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.set_options:
			advancedSearchOptions();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onImageSearch(View v) {
		String query = etQuery.getText().toString();
		Toast.makeText(this, "Searching for " + query, Toast.LENGTH_SHORT)
				.show();
		imageResults.clear();
		loadImages(query);
	}

	String BASE_URL = "https://ajax.googleapis.com/ajax/services/search/images?rsz=8";
	private String size=null, color=null, type=null, site=null;

	private String URLBuilder(String query) {
		StringBuilder urlBuilder = new StringBuilder(BASE_URL);
		urlBuilder.append("&start=");
		urlBuilder.append(totalLoad);
		urlBuilder.append("&v=1.0&q=");
		urlBuilder.append(Uri.encode(query));
		if (size != null) {
			urlBuilder.append("&imgsz=");
			urlBuilder.append(size);
		}
		if (color != null) {
			urlBuilder.append("&imgcolor=");
			urlBuilder.append(color);
		}
		if (type != null) {
			urlBuilder.append("&imgtype=");
			urlBuilder.append(type);
		}
		if (site != null) {
			urlBuilder.append("&as_sitesearch=");
			urlBuilder.append(site);
		}
		// imgsz
		// imgcolor
		// imgtype
		// as_sitesearch
		return urlBuilder.toString();
	}

	private void loadImages(String query) {
		String url = URLBuilder(query);

		client.get(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				JSONArray imageJsonResults = null;
				try {
					imageJsonResults = response.getJSONObject("responseData")
							.getJSONArray("results");

					imageAdapter.addAll(ImageResult
							.fromJSONArray(imageJsonResults));

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		});
		totalLoad += 8;

		url = URLBuilder(query);

		client.get(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				JSONArray imageJsonResults = null;
				try {
					imageJsonResults = response.getJSONObject("responseData")
							.getJSONArray("results");

					imageAdapter.addAll(ImageResult
							.fromJSONArray(imageJsonResults));
					// Log.d("DEBUG", imageResults.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		});
		totalLoad += 8;
	}

	static final int REQUEST_CODE = 0;

	public void advancedSearchOptions() {
		Intent i = new Intent(this, AdvancedSearchOptionActivity.class);
		startActivityForResult(i, REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (data.getExtras().containsKey("size"))
			size = data.getStringExtra("size");
		if (data.getExtras().containsKey("color"))
			color = data.getStringExtra("color");
		if (data.getExtras().containsKey("type"))
			type = data.getStringExtra("type");
		if (data.getExtras().containsKey("site"))
			site = data.getStringExtra("site");
	}

}
