package me.mrliu.android_bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SearchBTActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, IBTReceiver {
    private LinearLayout llOpenBT;
    private SwitchCompat switchCompat;
    private RecyclerView rvBTDevices;
    private BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothReceiver receiver;
    private List<BlueToothDeviceSection> sections = new ArrayList<>();
    private BlueToothDeviceSectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bt);
        setViews();
        setListeners();
        registerReceiver();
        getBTState();
    }

    private void getBTState() {
        switch (btAdapter.getState()) {
            case BluetoothAdapter.STATE_ON:
                switchCompat.setChecked(true);
                btAdapter.startDiscovery();
                getBTDevices();
                break;
            case BluetoothAdapter.STATE_OFF:
                switchCompat.setChecked(false);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        btAdapter.cancelDiscovery();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    private void registerReceiver() {
        receiver = new BluetoothReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);
    }

    private void setListeners() {
        llOpenBT.setOnClickListener(this);
        switchCompat.setOnCheckedChangeListener(this);
    }

    private void setViews() {
        llOpenBT = (LinearLayout) findViewById(R.id.ll_open_bluetooth);
        switchCompat = (SwitchCompat) findViewById(R.id.sc_bluetooth_state);
        rvBTDevices = (RecyclerView) findViewById(R.id.rv_bluetooth_devices);
    }

    private void getBTDevices() {
        if (sections.size()>0) {
            sections.clear();
        }
        sections.add(new BlueToothDeviceSection(true, "已配对的设备"));
        Set<BluetoothDevice> devices = btAdapter.getBondedDevices();
        if (devices.size()>0) {
            for (BluetoothDevice device : devices) {
                sections.add(new BlueToothDeviceSection(new BlueToothDeviceSection.BlueToothDevice(device.getType(), device.getName(), device.getAddress())));
            }
        }
        sections.add(new BlueToothDeviceSection(true, "可用设备"));
        rvBTDevices.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BlueToothDeviceSectionAdapter(R.layout.item_section_content, R.layout.item_section_head, sections);
        rvBTDevices.setAdapter(adapter);
    }

    private void clearBtDevices() {
        sections.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_open_bluetooth:
                if (switchCompat.isChecked()) {
                    switchCompat.setChecked(false);
                } else {
                    switchCompat.setChecked(true);
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            //打开蓝牙
            if (btAdapter != null && !btAdapter.isEnabled()) {
                btAdapter.enable();
                Toast.makeText(this, "打开", Toast.LENGTH_SHORT).show();
            }
        } else {
            //关闭蓝牙
            if (btAdapter!= null && btAdapter.isEnabled()) {
                btAdapter.disable();
                Toast.makeText(this, "关闭", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void btOn() {
        Toast.makeText(this, "BT-ON", Toast.LENGTH_SHORT).show();
        switchCompat.setChecked(true);
        btAdapter.startDiscovery();
        getBTDevices();
    }

    @Override
    public void btOff() {
        Toast.makeText(this, "BT-OFF", Toast.LENGTH_SHORT).show();
        switchCompat.setChecked(false);
        btAdapter.cancelDiscovery();
        clearBtDevices();
    }

    @Override
    public void btFound(BluetoothDevice device) {
        sections.add(new BlueToothDeviceSection(new BlueToothDeviceSection.BlueToothDevice(device.getType(), device.getName(), device.getAddress())));
        adapter.notifyDataSetChanged();
    }
}
