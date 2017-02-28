package me.mrliu.android_bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnOpen;
    private Button btnGetDevices, btnFindDevices;

    private BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
        setListeners();

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    private void setListeners() {
        btnOpen.setOnClickListener(this);
        btnGetDevices.setOnClickListener(this);
        btnFindDevices.setOnClickListener(this);
    }

    private void setViews() {
        btnOpen = (Button) findViewById(R.id.btn_open);
        btnGetDevices = (Button) findViewById(R.id.btn_get_devices);
        btnFindDevices = (Button) findViewById(R.id.btn_find_devices);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_open:
                openBluetooth();
                break;
            case R.id.btn_get_devices:
                getDevices();
                break;
            case R.id.btn_find_devices:
                findDevices();
                break;
        }
    }

    /**
     * 搜索蓝牙设备
     */
    private void findDevices() {
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
        mBluetoothAdapter.startDiscovery();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.d("TAG", device.getName()+", "+device.getAddress());
            }
        }
    };


    /**
     * 获取蓝牙设备
     */
    private void getDevices() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size()>0) {
            for (BluetoothDevice device : pairedDevices) {
                Log.d("TAG", device.getName()+", "+device.getAddress());
            }
        }
    }

    /**
     * 打开/关闭 蓝牙
     */
    private static final int REQUEST_ENABLE_BT = 1;
    private void openBluetooth() {
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "该设备不支持蓝牙", Toast.LENGTH_SHORT).show();
            return;
        }
        /*if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();
        } else {
            mBluetoothAdapter.enable();
        }*/

        if (!mBluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_ENABLE_BT);
        } else {
            mBluetoothAdapter.disable();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                if (requestCode == REQUEST_ENABLE_BT) {
                    Toast.makeText(this, "蓝牙已打开", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
