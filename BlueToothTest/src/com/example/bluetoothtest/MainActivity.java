package com.example.bluetoothtest;

import java.util.ArrayList;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button On, Off, Visible, list, find;
    private BluetoothAdapter ba;
    private Set<BluetoothDevice> pairedDevices;
    private ListView lv;
    private ArrayAdapter<String> BTArrayAdapter;
    private ListView myListView;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(Constants.TAG, "onCreate");

        ba = BluetoothAdapter.getDefaultAdapter();

        On = (Button) findViewById(R.id.button1);
        Off = (Button) findViewById(R.id.button2);
        Visible = (Button) findViewById(R.id.button3);
        list = (Button) findViewById(R.id.button4);
        find = (Button) findViewById(R.id.buttonfind);
//        et = (EditText) findViewById(R.id.et1);

        // create the arrayAdapter that contains the BTDevices, and set it to
        // the ListView
        myListView = (ListView) findViewById(R.id.listView1);
        BTArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        myListView.setAdapter(BTArrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void on(View view) {
//        if (!ba.isEnabled()) {
//            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(turnOn, 0);
//            Toast.makeText(getApplicationContext(), "Turned on", Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_LONG).show();
//        }
        Datastore.getAllConnections();
    }

    public void list(View view) {
        pairedDevices = ba.getBondedDevices();

        ArrayList list = new ArrayList();
        for (BluetoothDevice bt : pairedDevices)
            list.add(bt.getName());

        Toast.makeText(getApplicationContext(), "Showing Paired Devices", Toast.LENGTH_SHORT).show();
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);

    }

    public void off(View view) {
        ba.disable();
        Toast.makeText(getApplicationContext(), "Turned off", Toast.LENGTH_LONG).show();
    }

    public void visible(View view) {
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(getVisible, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void find(View view) {
        if (ba.isDiscovering()) {
            Log.i(Constants.TAG, "is discovering");
            // the button is pressed when it discovers, so cancel the discovery
            ba.cancelDiscovery();
        } else {
            Log.i(Constants.TAG, "Clear the adapter and restart");
            BTArrayAdapter.clear();
            ba.startDiscovery();

            registerReceiver(bReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        }
    }

    final BroadcastReceiver bReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                Log.i(Constants.TAG, "Found a device");
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                
                //Add device to the list
                Datastore.addConnection(device.getName(), device.getAddress());
                // add the name and the MAC address of the object to the
                // arrayAdapter
                BTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                Log.i(Constants.TAG, "NotifyChanged");
                BTArrayAdapter.notifyDataSetChanged();
            }
        }
    };

}
