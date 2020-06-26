package com.bignerdranch.android.shopping;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class UIFragment extends Fragment implements Observer{
    private static ItemsDB itemsDB;
    private File mPhotoFile;
    private static final String DIALOG_SHOP = "DialogShop";
    private static final int REQUEST_SHOP = 0;
    private static final int REQUEST_PHOTO= 1;
    private Item mItem = new Item("","");

    // GUI variables
    private Button mListItems;
    private Button mAddItems;
    private EditText mWhatText;
    private EditText mWhereText;
    private ImageButton mPhotoButton;
    private TextView mView;
    private ImageView mPhotoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsDB = ItemsDB.get(getActivity());
        mPhotoFile = ItemsDB.get(getActivity()).getPhotoFile(mItem);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ui, container, false);

        mWhatText = (EditText) view.findViewById(R.id.what_text);
        mWhatText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mWhatText.getText();
            }

            @Override
            public void afterTextChanged(Editable s) {
                hideKeyboardFrom(getContext(), view);
            }
        });

        mWhereText = (EditText) view.findViewById(R.id.where_text);

        //Choose the shop event
        mWhereText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                ShopPickerFragment dialog = new ShopPickerFragment(mWhereText);
                dialog.setTargetFragment(UIFragment.this, REQUEST_SHOP);
                dialog.show(manager, DIALOG_SHOP);
            }
        });
        mWhereText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mWhereText.getText();
            }

            @Override
            public void afterTextChanged(Editable s) {
                hideKeyboardFrom(getContext(), view);
            }
        });

        // Add items event
        mAddItems = (Button) view.findViewById(R.id.add_button);
        mAddItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((mWhatText.getText().length() > 0) &&
                        (mWhereText.getText().length() > 0)) {
                    mItem.setWhat(mWhatText.getText().toString().trim());
                    mItem.setWhere(mWhereText.getText().toString().trim());

                    //Reset fields
                    mWhatText.setText("");
                    mWhereText.setText("");

                    //Hide the keyboard
                    hideKeyboardFrom(getContext(), view);

                    itemsDB.fillItemsDB(mItem);
                } else {
                    Toast.makeText(getActivity(),
                            R.string.no_items_added,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // List all items event
        mListItems = (Button) view.findViewById(R.id.list_button);
        mListItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {;
                //Start ListFragment
                ListFragment listFragment = new ListFragment();
                FragmentManager manager = getFragmentManager();

                if (getResources().getConfiguration().orientation == 2) {
                    manager.beginTransaction().replace(R.id.fragment_list_landscape, listFragment)
                            .addToBackStack(null)
                            .commit();
                } else {
                    manager.beginTransaction().replace(R.id.fragment_container_portrait, listFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        // Take picture event
        PackageManager packageManager = getActivity().getPackageManager();
        mPhotoButton = (ImageButton) view.findViewById(R.id.item_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "com.bignerdranch.android.shopping.fileprovider",
                        mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                List<ResolveInfo> cameraActivities = getActivity()
                        .getPackageManager().queryIntentActivities(captureImage,
                                PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo activity : cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                startActivityForResult(captureImage, REQUEST_PHOTO);
                mPhotoView.setImageDrawable(null);
            }
        });

        mPhotoView = (ImageView) view.findViewById(R.id.item_photo);
        updatePhotoView();

        return view;
    }

    /*Solution for hiding the keyboard found at Stackoverflow.
      Source: https://stackoverflow.com/questions/1109022/close-hide-android-soft-keyboard*/
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager inputMManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void update(Observable o, Object arg) {
        mView.setText(itemsDB.listItems());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_SHOP) {
            String shop = (String) data
                    .getSerializableExtra(ShopPickerFragment.EXTRA_SHOP);
            mItem.setWhere(shop);
            mWhereText.setText(mItem.getWhere());
        } else if (requestCode == REQUEST_PHOTO) {
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.bignerdranch.android.shopping.fileprovider",
                    mPhotoFile);
            getActivity().revokeUriPermission(uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
        }
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
            //mPhotoView.setImageResource(R.mipmap.ic_launcher);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_menu_bar, menu);
    }
}
