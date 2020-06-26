package com.bignerdranch.android.shopping;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ShopPickerFragment extends DialogFragment {
    final String[] mShops = {"Netto", "Fakta", "Bakery", "Meny", "Fotex", "Irma", "7-Eleven",
            "Kvickly", "Aldi", "Bilka", "Lidl", "SuperBrugsen", "Rema 1000", "SPAR"};

    public static final String EXTRA_SHOP =
            "com.bignerdranch.android.shopping.shop";

    private EditText mNewWhere;

    public ShopPickerFragment (EditText where) {
        mNewWhere = where;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.shop_picker_title)
                .setItems(mShops, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mNewWhere.setText(mShops[which]);
                    }
                }).create();
    }
}
