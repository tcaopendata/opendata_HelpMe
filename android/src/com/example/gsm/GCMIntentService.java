/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.gsm;

import static com.example.gsm.CommonUtilities.SENDER_ID;
import static com.example.gsm.CommonUtilities.displayMessage;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;

/**
 * IntentService responsible for handling GCM messages.
 */
public class GCMIntentService extends GCMBaseIntentService {

    @SuppressWarnings("hiding")
    private static final String TAG = "GCMIntentService";

    public GCMIntentService() {    	
        super(SENDER_ID);
        Log.i("�q�L","�w�w");
    }

    @Override
    protected void onRegistered(Context context, String registrationId) {
    	
        Log.i(TAG, "Device registered: regId = " + registrationId);
        displayMessage(context, getString(R.string.gcm_registered));
        ServerUtilities.register(context, registrationId);
    }

    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        displayMessage(context, getString(R.string.gcm_unregistered));
        if (GCMRegistrar.isRegisteredOnServer(context)) {
            ServerUtilities.unregister(context, registrationId);
        } else {
            // This callback results from the call to unregister made on
            // ServerUtilities when the registration to the server failed.
            Log.i(TAG, "Ignoring unregister callback");
        }
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
    	// ���� GCM server �ǨӪ��T��
    	Bundle bData = intent.getExtras();
    	// �q�� user
    	generateNotification(context,bData);
    	}
    	private static void generateNotification(Context context, Bundle data)
    	{
    	int icon = R.drawable.ic_launcher;

    	long when = System.currentTimeMillis();
    	String aa = "";
    	int a =0;
    	for (String key : data.keySet()) {
    		a++;
    		if(a >2){
    			aa+=key+" ";
    		}    		
     }
    	NotificationManager nm =
    	(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

    	Intent ni = new Intent(context, MainActivity.class);

    	ni.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_SINGLE_TOP);

    	PendingIntent intent = PendingIntent.getActivity(context, 0, ni, 0);
    	//.setContentText(data.getString("Notice"))//�]�w����q���ɪ����e
    	Notification noti = new NotificationCompat.Builder(context)
    	.setContentTitle(data.getString("title"))//�]�w����q���ɪ�Title  
    	.setContentText(data.getString("message"))
    	.setContentIntent(intent)//�I���o�ӳq���ɭn���ƻ�ʧ@
    	.setDefaults(Notification.DEFAULT_ALL)//�]�m�����ɥH�w�]�Ҧ�(�t�έ�+�_��)
    	.setSmallIcon(icon)//�]�w����q���ɭn��ܪ�icon�ϥ�
    	.setWhen(when).build(); //�]�w�o�ͮɶ�

    	nm.notify(0, noti);
    }

    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message = getString(R.string.gcm_deleted, total);
        displayMessage(context, message);
        // notifies user
        generateNotification(context, message);
    }

    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
        displayMessage(context, getString(R.string.gcm_error, errorId));
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        displayMessage(context, getString(R.string.gcm_recoverable_error,
                errorId));
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    private static void generateNotification(Context context, String message) {
        int icon = R.drawable.ic_stat_gcm;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);
        String title = context.getString(R.string.app_name);
        Intent notificationIntent = new Intent(context, MainActivity.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);
    }

}
