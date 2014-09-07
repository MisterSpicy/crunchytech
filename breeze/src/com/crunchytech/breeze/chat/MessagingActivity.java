package com.crunchytech.breeze.chat;

import com.sinch.android.rtc.messaging.Message;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.messaging.MessageClientListener;
import com.sinch.android.rtc.messaging.MessageDeliveryInfo;
import com.sinch.android.rtc.messaging.MessageFailureInfo;
import com.sinch.android.rtc.PushPair;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import com.crunchytech.breeze.R;

public class MessagingActivity extends Activity implements ServiceConnection, MessageClientListener {

    private static final String TAG = MessagingActivity.class.getSimpleName();

    private MessageService.MessageServiceInterface mMessageService;

    private MessageAdapter mMessageAdapter;

    private EditText mTxtTextBody;

    private ImageButton mBtnSend;

    private ListView mMessagesList;

    private String mReceipient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messaging);

        doBind();
        
        
        mMessagesList = (ListView) findViewById(R.id.lstMessages);
        mTxtTextBody = (EditText) findViewById(R.id.txtTextBody);
        mBtnSend = (ImageButton) findViewById(R.id.btnSend);

        mMessageAdapter = new MessageAdapter(this);
        mMessagesList.setAdapter(mMessageAdapter);
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        mReceipient = getIntent().getStringExtra(MessageService.INTENT_EXTRA_PEER);
        Log.d(TAG, "Start conversation with " + mReceipient);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
            | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP);
        this.setTitle(mReceipient);
        getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent))); 

    }

    @Override
    public void onDestroy() {
        doUnbind();
        super.onDestroy();
    }

    private void doBind() {
        Intent intent = new Intent(this, MessageService.class);
        bindService(intent, this, BIND_AUTO_CREATE);        
    }

    private void doUnbind() {
        if (mMessageService != null) {
            mMessageService.removeMessageClientListener(this);
        }
        unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        mMessageService = (MessageService.MessageServiceInterface) iBinder;
        mMessageService.addMessageClientListener(this);
        setButtonEnabled(true);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        setButtonEnabled(false);
        mMessageService = null;
    }

    private void sendMessage() {
    	if(mReceipient == null) {
    		Log.e(TAG, "mReceipient is null");
    	}
        String textBody = mTxtTextBody.getText().toString();
        if (mReceipient.isEmpty()) {
            Toast.makeText(this, "No recipient added", Toast.LENGTH_SHORT).show();
            return;
        }
        if (textBody.isEmpty()) {
            Toast.makeText(this, "No text message", Toast.LENGTH_SHORT).show();
            return;
        }

        mMessageService.sendMessage(mReceipient, textBody);
        mTxtTextBody.setText("");
    }

    private void setButtonEnabled(boolean enabled) {
        mBtnSend.setEnabled(enabled);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        // Respond to the action bar's Up/Home button
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onIncomingMessage(MessageClient client, Message message) {
        mMessageAdapter.addMessage(message, MessageAdapter.DIRECTION_INCOMING);
    }

    @Override
    public void onMessageSent(MessageClient client, Message message, String recipientId) {
        mMessageAdapter.addMessage(message, MessageAdapter.DIRECTION_OUTGOING);
    }

    @Override
    public void onShouldSendPushData(MessageClient client, Message message, List<PushPair> pushPairs) {
        //Left intentionally blank
    }

    @Override
    public void onMessageFailed(MessageClient client, Message message,
            MessageFailureInfo failureInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("Failed for user: ")
                .append(failureInfo.getRecipientId())
                .append(" with message: ")
                .append(failureInfo.getSinchError().getMessage());

        Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();
        Log.d(TAG, sb.toString());
        sb.setLength(0);
    }

    @Override
    public void onMessageDelivered(MessageClient client, MessageDeliveryInfo deliveryInfo) {
        Log.d(TAG, "onDelivered");
    }
}
