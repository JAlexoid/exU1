package eu.activelogic.android.exu1.dummy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Arrays;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.util.Log;

import eu.activelogic.android.uone.files.UONode;
import eu.activelogic.android.uone.files.UOVolumes;

public class UbuntuOneAPI {

    public static final String TAG = "UbuntuOneAPI";

    private final String consumerSecret;
    private final String token;
    private final String consumerKey;
    private final String name = "Ubuntu One @ exU1";
    private final String tokenSecret;

    public UbuntuOneAPI(String consumerKey, String consumerSecret, String token, String tokenSecret) {
	super();
	this.token = token;
	this.tokenSecret = tokenSecret;
	this.consumerKey = consumerKey;
	this.consumerSecret = consumerSecret;
    }

    public UOVolumes listVolumes() {

	CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
	consumer.setTokenWithSecret(token, tokenSecret);

	DefaultHttpClient httpclient = new DefaultHttpClient();

	try {
	    URI uri = new URI("https", "one.ubuntu.com", "/api/file_storage/v1", null);
	    HttpGet get = new HttpGet(uri);
	    consumer.sign(get);

	    HttpResponse resp = httpclient.execute(get);
	    Log.d(TAG, "Status line: " + resp.getStatusLine());

	    InputStream is = resp.getEntity().getContent();

	    StringBuilder sb = inputStreamToString(is);

	    Log.d(TAG, "Result: " + sb);

	    JSONObject o = new JSONObject(sb.toString());

	    return new UOVolumes(o);
	} catch (Exception e) {
	    Log.e(TAG, e.getMessage() + e);
	    e.printStackTrace();
	    return null;
	} finally {
	    httpclient.getConnectionManager().shutdown();
	}

    }

    public UONode fetchNode(String path, Boolean includeChildren) {

	CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
	consumer.setTokenWithSecret(token, tokenSecret);

	DefaultHttpClient httpclient = new DefaultHttpClient();

	try {
	    URI uri = new URI("https", "one.ubuntu.com", "/api/file_storage/v1" + path, null);
	    if (includeChildren)
		uri = new URI(uri.normalize().toASCIIString() + "?include_children=true");

	    HttpGet get = new HttpGet(uri);
	    consumer.sign(get);

	    Log.d(TAG, "Fetching: " + get);

	    HttpResponse resp = httpclient.execute(get);
	    Log.d(TAG, "Status line: " + resp.getStatusLine());

	    InputStream is = resp.getEntity().getContent();

	    StringBuilder sb = inputStreamToString(is);

	    Log.d(TAG, "Result: " + sb);

	    JSONObject o = new JSONObject(sb.toString());

	    return new UONode(o);
	} catch (Exception e) {
	    Log.e(TAG, e.getMessage() + e);
	    e.printStackTrace();
	    return null;
	} finally {
	    httpclient.getConnectionManager().shutdown();
	}
    }

    private StringBuilder inputStreamToString(InputStream is) throws IOException {
	String line = "";
	StringBuilder total = new StringBuilder();

	// Wrap a BufferedReader around the InputStream
	BufferedReader rd = new BufferedReader(new InputStreamReader(is));

	// Read response until the end
	while ((line = rd.readLine()) != null) {
	    total.append(line);
	}

	// Return full string
	return total;
    }

}