package com.codesroots.mac.cards.DataLayer.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class PreferenceHelper {

	private static SharedPreferences app_prefs;
	private static String app_ref = "userdetails";
	private static String photo = "photo";
	private static String Token = "auth";
	private static String UserId = "userid";
	private static String Username = "username";
	private static String orderid = "orderid";
	private static String type = "type";
	private static String AuthId = "auth";
	private static String UserCity = "userCity";

	private static String UserGroupId = "UserGroupId";
	private static String ROOMID = "ROOMID";
	private static String INCHAT = "INCHAT";
	private final String CURRENTLAT = "latitude";
	private final String CURRENTLONG = "longtude";
	private final String CURRENTCATEGRY = "CURRENTCATEGRY";
	private Context context;

	public PreferenceHelper(Context context) {
		this.context = context;
		try {
			app_prefs = context.getSharedPreferences(app_ref,
					Context.MODE_PRIVATE);
		}
		catch (NullPointerException e)
		{

		}
	}
	public static String getAuthId() {

		return app_prefs.getString(AuthId,"0");
	}

	public static void setAuthId(String API_TOKEN, Context context) {
		app_prefs = context.getSharedPreferences(app_ref,
				Context.MODE_PRIVATE);
		Editor edit = app_prefs.edit();
		edit.putString(AuthId, API_TOKEN);
		edit.apply();
	}


	public static String getToken() {

		return app_prefs.getString(Token,"");
	}

	public static void setToken(String API_TOKEN, Context context) {
		app_prefs = context.getSharedPreferences(app_ref,
				Context.MODE_PRIVATE);
		Editor edit = app_prefs.edit();
		edit.putString(Token, API_TOKEN);
		edit.apply();
	}
	public static String getUsername() {
		return app_prefs.getString(Username,"");
	}
	public static void setUsername(String username) {
		Editor edit = app_prefs.edit();
		edit.putString(Username, username);
		edit.apply();
	}
	public static String getOrderid() {
		return app_prefs.getString(orderid,"");
	}
	public static void setOrderid(String orderids) {
		Editor edit = app_prefs.edit();
		edit.putString(orderid, orderids);
		edit.apply();
	}
	public static int getUserId() {
		return app_prefs.getInt(UserId,0);
	}
	public static int getUserGroupId() {
		return app_prefs.getInt(UserGroupId,0);
	}
	public static void setROOMID(String roomid) {
		Editor edit = app_prefs.edit();
		edit.putString(ROOMID, roomid);
		edit.apply();
	}



	public static void setUserId(int user_id) {
		Editor edit = app_prefs.edit();
		edit.putInt(UserId, user_id);
		edit.apply();
	}
	public static void setUserGroupId(int userGroupId) {
		Editor edit = app_prefs.edit();
		edit.putInt(UserGroupId, userGroupId);
		edit.apply();
	}



	public static String gettype() {
		return app_prefs.getString(type,"");
	}
	public static void settype(String orderids) {
		Editor edit = app_prefs.edit();
		edit.putString(type, orderids);
		edit.apply();
	}


	public static String getUserCity() {
		return UserCity;
	}

	public static void setUserCity(String userCity) {
		Editor edit = app_prefs.edit();
		edit.putString(UserCity, userCity);
		edit.apply();
	}
}
