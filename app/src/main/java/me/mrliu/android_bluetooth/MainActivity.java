package me.mrliu.android_bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnOpen;

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
    }

    private void setViews() {
        btnOpen = (Button) findViewById(R.id.btn_open);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_open:
                openBluetooth();
                break;
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
