package com.example.helpticket.ui.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

abstract class TokenBroadcastReceiver extends BroadcastReceiver {

    private static final String ACTION_TOKEN = "com.google.example.ACTION_TOKEN";
    private static final String EXTRA_KEY_TOKEN = "key_token";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_TOKEN.equals(intent.getAction())) {
            String token = intent.getExtras().getString(EXTRA_KEY_TOKEN);
            onNewToken(token);
        }
    }

    public static IntentFilter getFilter() {
        IntentFilter filter = new IntentFilter(ACTION_TOKEN);

        return filter;
    }

    public abstract void onNewToken(String token);
}
