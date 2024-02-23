package com.poly.moneylover.models;


import com.poly.moneylover.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable {
    private int id;
    private String text;
    private int icon;
    private int color;





    public Item(int id, String text, int icon, int color) {
        this.id = id;
        this.text = text;
        this.icon = icon;
        this.color = color;
    }



    public Item(String text) {
        this.text = text;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public static List<Item> getListItemTienChi() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(1, "Ăn uống", R.drawable.plate_and_utensils, R.color.orange));
        list.add(new Item(2, "Chi tiêu hàng ngày", R.drawable.icon_liquid, R.color.green));
        list.add(new Item(3, "Quần áo", R.drawable.icon_shirt, R.color.blue));
        list.add(new Item(4, "Mỹ phẩm", R.drawable.icon_lipstick, R.color.pink));
        list.add(new Item(5, "Phí giao lưu", R.drawable.icon_coffee, R.color.yellow));
        list.add(new Item(6, "Y tế", R.drawable.icon_medicine, R.color.red));
        list.add(new Item(7, "Giáo dục", R.drawable.icon_education, R.color.green1));
        list.add(new Item(8, "Tiền điện", R.drawable.icon_tap_faucet, R.color.blue_sky));
        list.add(new Item(9, "Phí Đi lại", R.drawable.icon_subway, R.color.brown));
        list.add(new Item(10, "Phí liên lạc", R.drawable.icon_smartphone, R.color.color_text_1));
        list.add(new Item(11, "Tiền nhà", R.drawable.icon_house, R.color.yellow1));
        return list;
    }

    public static  List<Item> getListItemTienThu() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(1, "Tiền lương", R.drawable.icon_wallet, R.color.green));
        list.add(new Item(2, "Tiền phụ cấp", R.drawable.icon_piggy, R.color.orange2));
        list.add(new Item(3, "Tiền thưởng", R.drawable.icon_gift_box, R.color.blue_sky));
        list.add(new Item(4, "Thu nhập phụ", R.drawable.icon_lipstick, R.color.pink));
        list.add(new Item(5, "Đầu tư", R.drawable.icon_coin, R.color.green2));
        list.add(new Item(6, "Thu nhập tạm thời", R.drawable.icon_incomes, R.color.pink));
        return list;
    }

}
