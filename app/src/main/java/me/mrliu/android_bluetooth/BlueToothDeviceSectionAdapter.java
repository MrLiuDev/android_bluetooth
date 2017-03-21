package me.mrliu.android_bluetooth;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by LiuKang on 2017/3/21.
 */

public class BlueToothDeviceSectionAdapter extends BaseSectionQuickAdapter<BlueToothDeviceSection, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public BlueToothDeviceSectionAdapter(int layoutResId, int sectionHeadResId, List<BlueToothDeviceSection> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, BlueToothDeviceSection item) {
        helper.setText(R.id.tv_section_head, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, BlueToothDeviceSection item) {
        BlueToothDeviceSection.BlueToothDevice device = item.t;
        helper.setText(R.id.tv_section_content, device.getName());
    }
}
