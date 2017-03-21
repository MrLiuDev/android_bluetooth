package me.mrliu.android_bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by LiuKang on 2017/3/21.
 */

public class BluetoothReceiver extends BroadcastReceiver {
    private IBTReceiver ibtReceiver;

    public BluetoothReceiver(IBTReceiver ibtReceiver) {
        this.ibtReceiver = ibtReceiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case BluetoothAdapter.ACTION_STATE_CHANGED:
                switch (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)) {
                    case BluetoothAdapter.STATE_ON:
                        ibtReceiver.btOn();
                        Log.d("TAG", "ON");
                        break;

                    case BluetoothAdapter.STATE_OFF:
                        Log.d("TAG", "OFF");
                        ibtReceiver.btOff();
                        break;
                }
                break;

            case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                Log.d("TAG", "ACTION_DISCOVERY_STARTED");
                break;

            case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                Log.d("TAG", "ACTION_DISCOVERY_FINISHED");
                break;

            case BluetoothDevice.ACTION_FOUND:
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                ibtReceiver.btFound(device);
                Log.d("TAG", "ACTION_FOUND");
                break;
        }
    }
}
