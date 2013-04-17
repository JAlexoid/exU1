package eu.activelogic.android.uone.files;

import java.text.ParseException;
import java.util.Locale;
import java.util.TimeZone;

import org.json.JSONException;

import android.util.Log;

public class UOBase {

    public static java.text.SimpleDateFormat S_DATE_FORMAT;
    
    static {
	try{
	    S_DATE_FORMAT = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
	    S_DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
	} catch(Exception e){
	    Log.e("UOBase", e.getMessage(), e);
	}
    }
    
    
    String safeString(org.json.JSONObject in, String key) throws JSONException {
	return in.has(key) ? in.getString(key) : null;
    }

    Boolean safeBoolean(org.json.JSONObject in, String key) throws JSONException {
	return in.has(key) ? toBoolean(in.getString(key)) : null;
    }

    Boolean toBoolean(String s) {
	return s.equalsIgnoreCase("TRUE") || s.equals("1") || s.toUpperCase().startsWith("Y");
    }

    java.util.Date safeDate(org.json.JSONObject in, String key) throws JSONException, ParseException {
	return in.has(key) ? toDate(in.getString(key)) : null;
    }

    java.util.Date toDate(String date) throws ParseException {
	return S_DATE_FORMAT.parse(date);
    }

    String formatDate(java.util.Date date) {
	return date != null ? S_DATE_FORMAT.format(date) : null;
    }

    Integer safeInteger(org.json.JSONObject in, String key) throws JSONException {
	return in.has(key) ? Integer.valueOf(in.getString(key)) : null;
    }

    Long safeLong(org.json.JSONObject in, String key) throws JSONException {
	return in.has(key) ? Long.valueOf(in.getString(key)) : null;
    }

    Double safeDouble(org.json.JSONObject in, String key) throws JSONException {
	return in.has(key) ? Double.valueOf(in.getString(key)) : null;
    }

    <T extends Enum<T>> T safeEnum(Class<T> clz, org.json.JSONObject in, String key) throws JSONException {
	return in.has(key) ? Enum.valueOf(clz, in.getString(key).toUpperCase()) : null;
    }

    
}
