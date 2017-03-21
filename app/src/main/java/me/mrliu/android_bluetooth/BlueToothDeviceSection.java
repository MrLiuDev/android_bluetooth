package me.mrliu.android_bluetooth;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by LiuKang on 2017/3/20.
 */

public class BlueToothDeviceSection extends SectionEntity<BlueToothDeviceSection.BlueToothDevice> {


    public BlueToothDeviceSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public BlueToothDeviceSection(BlueToothDevice blueToothDevice) {
        super(blueToothDevice);
    }

    public static class BlueToothDevice {
        private int type;
        private String name;
        private String address;

        public BlueToothDevice() {
        }

        public BlueToothDevice(int type, String name, String address) {
            this.type = type;
            this.name = name;
            this.address = address;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        @Override
        public String toString() {
            return "BlueToothDevice{" +
                    "type=" + type +
                    ", name='" + name + '\'' +
                    ", address='" + address + '\'' +
                    '}';
        }
    }
}
