package me.mrliu.android_bluetooth;

import android.bluetooth.BluetoothDevice;

/**
 * Created by LiuKang on 2017/3/21.
 */

public interface IBTReceiver {
    void btOn();
    void btOff();
    void btFound(BluetoothDevice device);
}
