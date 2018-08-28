package com.example.e7440.bankingproject.components.message_dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e7440.bankingproject.R;
import com.example.e7440.bankingproject.components.ShowItemAdapter;
import com.example.e7440.bankingproject.module.model.Item;

import java.util.ArrayList;
import java.util.List;

import static com.example.e7440.bankingproject.components.message_dialog.ResultMessageDialog.MESSAGEDIALOG_BUTTON_BACKGROUND;
import static com.example.e7440.bankingproject.components.message_dialog.ResultMessageDialog.MESSAGEDIALOG_BUTTON_NO;
import static com.example.e7440.bankingproject.components.message_dialog.ResultMessageDialog.MESSAGEDIALOG_BUTTON_OK;
import static com.example.e7440.bankingproject.components.message_dialog.ResultMessageDialog.MESSAGEDIALOG_BUTTON_YES;
import static com.example.e7440.bankingproject.components.message_dialog.ResultMessageDialog.MESSAGEDIALOG_ITEM;


/**
 * Created by admin on 4/25/2017.
 */

public class MessageDialogHelper {
    private OnClickDialogListener onClickDialogListener;
    private String[] nameButtonsDialog;
    private Integer dialogId;
    private TypeMessageDialog typeMessageDialog;
    private String titleDialog;
    private String contentDialog;
    private Context context;
    private Dialog mDialog;
    private Object object;
    private ShowItemAdapter mShowItemAdapter;

    //region SET METHOD
    //TODO
    private List<Item> mItemList = new ArrayList<>();

    public List<Item> getmItemList() {
        return mItemList;
    }

    public void setmItemList(List<Item> mItemList) {
        this.mItemList = mItemList;
    }

    public void setDialogId(Integer dialogId) {
        this.dialogId = dialogId;
    }

    public void setNameButtonsDialog(String[] nameButtonsDialog) {
        this.nameButtonsDialog = nameButtonsDialog;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setTypeMessageDialog(TypeMessageDialog typeMessageDialog) {
        this.typeMessageDialog = typeMessageDialog;
    }

    public void setTitleDialog(String titleDialog) {
        this.titleDialog = titleDialog;
    }

    public void setContentDialog(String contentDialog) {
        this.contentDialog = contentDialog;
    }

    public void setOnClickDialogListener(OnClickDialogListener onClickDialogListener) {
        this.onClickDialogListener = onClickDialogListener;
    }
    //endregion

    //region Contrustor
    public MessageDialogHelper(Context context) {
        this.context = context;
    }

    public MessageDialogHelper(OnClickDialogListener onClickDialogListener, TypeMessageDialog typeMessageDialog, String titleDialog, String contentDialog) {
        this.onClickDialogListener = onClickDialogListener;
        this.typeMessageDialog = typeMessageDialog;
        this.titleDialog = titleDialog;
        this.contentDialog = contentDialog;
    }

    public MessageDialogHelper(OnClickDialogListener onClickDialogListener, String[] nameButtonsDialog, TypeMessageDialog typeMessageDialog, String titleDialog, String contentDialog) {
        this.onClickDialogListener = onClickDialogListener;
        this.nameButtonsDialog = nameButtonsDialog;
        this.typeMessageDialog = typeMessageDialog;
        this.titleDialog = titleDialog;
        this.contentDialog = contentDialog;
    }
    //endregion

    public void onCreate() {
        switch (typeMessageDialog) {
            case MESSAGEDIALOG_TYPE_SUCCESS:
                onCreateDialogSuccess();
                break;
            case MESSAGEDIALOG_TYPE_YESNO:
                onCreateDialogYesNo();
                break;
            case MESSAGEDIALOG_TYPE_LOADING:
                onCreateDialogLoading();
                break;
            case MESSAGEDIALOG_TYPE_ERROR:
                onCreateDialogError();
                break;
            case MESSAGEDIALOG_TYPE_WARNING:
                onCreateDialogWarning();
                break;
            case MESSAGEDIALOG_TYPE_TOAST:
                onCreateDialogToast();
                break;
            case MESSAGE_DIALOG_TYPE_CHOOSE_ITEM:
                onCreateDialogChooseItem(titleDialog, mItemList);
                break;
            case MESSAGE_DIALOG_TYPE_ITEM_CHANGE_LANGUAGE:
                break;
            case MESSAGE_DIALOG_TYPE_CHOOSE_ITEM_RIGHT_ARROW:
                break;
            case MESSAGE_DIALOG_TYPE_SHOW_IMAGE:
                onCreateDialogImage();
                break;
        }
    }

    public void onShow() {
        mDialog.show();
    }

    public void onDimiss() {
        mDialog.dismiss();
    }


    private void onCreateDialogChooseItem(String header, List<Item> mItemList) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_show_item, null);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = width;
        params.height = height - getHeightToolbar(context) - getHeightStatusbar(context);

        dialog.setContentView(view);
//        dialog.getWindow().setGravity(Gravity.CENTER);
//        dialog.setCanceledOnTouchOutside(true);

        mDialog = dialog;
        TextView textViewHeader = (TextView) view.findViewById(R.id.textview_header);
        TextView textViewCancel = (TextView) view.findViewById(R.id.textview_cancel);
        RecyclerView mRecycler = (RecyclerView) view.findViewById(R.id.recyclerview_item);
        View mViewNoData = (View) view.findViewById(R.id.include_no_data);
        View viewDialog = (View) view.findViewById(R.id.layout_dialog);

        if (mItemList.size() <= 0) {
            mViewNoData.setVisibility(View.VISIBLE);
            mRecycler.setVisibility(View.GONE);
        } else {
            mViewNoData.setVisibility(View.GONE);
            mRecycler.setVisibility(View.VISIBLE);
        }
        //getView
        //create adapter
        mShowItemAdapter = new ShowItemAdapter(context, mItemList);
        mRecycler.setLayoutManager(new LinearLayoutManager(context));
        mRecycler.setAdapter(mShowItemAdapter);
        mRecycler.setNestedScrollingEnabled(false);


        textViewHeader.setText(header);
        //event click
        viewDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickDialogListener != null) {
                    DialogResultItem dialogResulltItem = new DialogResultItem(dialogId, MESSAGEDIALOG_BUTTON_BACKGROUND, null);
                    onClickDialogListener.onClickDialog(dialogResulltItem);
                }
            }
        });
        //event click item in adapter
        mShowItemAdapter.setOnItemClickListener(new ShowItemAdapter.OnItemClickListener() {
            @Override
            public void onClickListener(Item item, int position) {
                if (onClickDialogListener != null) {
                    DialogResultItem dialogResulltItem = new DialogResultItem(dialogId, MESSAGEDIALOG_ITEM, item);
                    onClickDialogListener.onClickDialog(dialogResulltItem);
                }
            }
        });

        textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

    }

    //region MESSAGEDIALOG_TYPE_YESNO
    public void onCreateDialogWarning() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_type_notification, null);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = width;
        params.height = height - getHeightToolbar(context) - getHeightStatusbar(context) + 200;

        dialog.setContentView(view);
//        dialog.getWindow().setGravity(Gravity.CENTER);
//        dialog.setCanceledOnTouchOutside(true);

        mDialog = dialog;
        //Get view
        View viewDialog = (View) dialog.findViewById(R.id.layout_dialog);
        TextView textViewContent = (TextView) view.findViewById(R.id.textview_content);
        TextView textviewTitle = (TextView) view.findViewById(R.id.textview_title);
        AppCompatImageView imageViewMark = (AppCompatImageView) view.findViewById(R.id.imageview_mark);
        //Set value for view
        imageViewMark.setBackground(context.getResources().getDrawable(R.drawable.ic_dialog_typeinformation));
        textviewTitle.setTextColor(context.getResources().getColor(R.color.yellow_500));

        textViewContent.setText(contentDialog);
        textviewTitle.setText(titleDialog);
        Button buttonAccept = (Button) view.findViewById(R.id.button_accept);
        //Set event for view
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickDialogListener != null) {
                    DialogResultItem item = new DialogResultItem(dialogId, MESSAGEDIALOG_BUTTON_OK, null);
                    onClickDialogListener.onClickDialog(item);
                }
            }
        });

        viewDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickDialogListener != null) {
                    DialogResultItem item = new DialogResultItem(dialogId, MESSAGEDIALOG_BUTTON_BACKGROUND, null);
                    onClickDialogListener.onClickDialog(item);
                }
            }
        });
    }

    public void onCreateDialogError() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_type_notification, null);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = width;
        params.height = height - getHeightToolbar(context) - getHeightStatusbar(context) + 200;

        dialog.setContentView(view);
//        dialog.getWindow().setGravity(Gravity.CENTER);
//        dialog.setCanceledOnTouchOutside(true);

        mDialog = dialog;
        //Get view
        View viewDialog = (View) dialog.findViewById(R.id.layout_dialog);
        TextView textViewContent = (TextView) view.findViewById(R.id.textview_content);
        TextView textviewTitle = (TextView) view.findViewById(R.id.textview_title);
        AppCompatImageView imageViewMark = (AppCompatImageView) view.findViewById(R.id.imageview_mark);
        //Set value for view
        imageViewMark.setBackground(context.getResources().getDrawable(R.drawable.ic_dialog_typeerror));
        textviewTitle.setTextColor(context.getResources().getColor(R.color.red_500));

        textViewContent.setText(contentDialog);
        textviewTitle.setText(titleDialog);
        Button buttonAccept = (Button) view.findViewById(R.id.button_accept);
        //Set event for view
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickDialogListener != null) {
                    DialogResultItem item = new DialogResultItem(dialogId, MESSAGEDIALOG_BUTTON_OK, null);
                    onClickDialogListener.onClickDialog(item);
                }
            }
        });

        viewDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickDialogListener != null) {
                    DialogResultItem item = new DialogResultItem(dialogId, MESSAGEDIALOG_BUTTON_BACKGROUND, null);
                    onClickDialogListener.onClickDialog(item);
                }
            }
        });
    }

    public void onCreateDialogSuccess() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_type_notification, null);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = width;
        params.height = height - getHeightToolbar(context) - getHeightStatusbar(context) + 200;

        dialog.setContentView(view);
//        dialog.getWindow().setGravity(Gravity.CENTER);
//        dialog.setCanceledOnTouchOutside(true);

        mDialog = dialog;
        //Get view
        View viewDialog = (View) dialog.findViewById(R.id.layout_dialog);
        TextView textViewContent = (TextView) view.findViewById(R.id.textview_content);
        TextView textviewTitle = (TextView) view.findViewById(R.id.textview_title);
        AppCompatImageView imageViewMark = (AppCompatImageView) view.findViewById(R.id.imageview_mark);
        //Set value for view
        imageViewMark.setBackground(context.getResources().getDrawable(R.drawable.ic_dialog_typesuccess));
        textviewTitle.setTextColor(context.getResources().getColor(R.color.blue_500));

        textViewContent.setText(contentDialog);
        textviewTitle.setText(titleDialog);
        Button buttonAccept = (Button) view.findViewById(R.id.button_accept);
        //Set event for view
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickDialogListener != null) {
                    DialogResultItem item = new DialogResultItem(dialogId, MESSAGEDIALOG_BUTTON_OK, null);
                    onClickDialogListener.onClickDialog(item);
                }
            }
        });

        viewDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickDialogListener != null) {
                    DialogResultItem item = new DialogResultItem(dialogId, MESSAGEDIALOG_BUTTON_BACKGROUND, null);
                    onClickDialogListener.onClickDialog(item);
                }
            }
        });
        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // Prevent dialog close on back press button
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });
    }

    public void onCreateDialogYesNo() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_type_confirm, null);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        dialog.setContentView(view);

        mDialog = dialog;
        //Get view
        View viewDialog = (View) dialog.findViewById(R.id.layout_dialog);
        TextView textViewContent = (TextView) dialog.findViewById(R.id.textview_content);
        TextView textViewTitle = (TextView) dialog.findViewById(R.id.textView_titles);
        Button buttonAccept = (Button) dialog.findViewById(R.id.button_accept);
        Button buttonDeince = (Button) dialog.findViewById(R.id.button_denice);
        //set value for view
        textViewContent.setText(contentDialog);
        textViewTitle.setText(titleDialog);

        viewDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickDialogListener != null) {
                    DialogResultItem item = new DialogResultItem(dialogId, MESSAGEDIALOG_BUTTON_BACKGROUND, null);
                    onClickDialogListener.onClickDialog(item);
                }
            }
        });

        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickDialogListener != null) {
                    DialogResultItem item = new DialogResultItem(dialogId, MESSAGEDIALOG_BUTTON_YES, null);
                    onClickDialogListener.onClickDialog(item);
                }
            }
        });
        buttonDeince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickDialogListener != null) {
                    DialogResultItem item = new DialogResultItem(dialogId, MESSAGEDIALOG_BUTTON_NO, null);
                    onClickDialogListener.onClickDialog(item);
                }
            }
        });

    }

    public void onCreateDialogLoading() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_type_loading, null);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        dialog.setContentView(view);

        mDialog = dialog;
        //Get view
        TextView textViewContent = (TextView) dialog.findViewById(R.id.textview_content);

        textViewContent.setText(contentDialog);
    }

    public void onCreateDialogImage() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_show_image, null);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        dialog.setContentView(view);

        mDialog = dialog;
        //Get view
        ImageView imageView = (ImageView) dialog.findViewById(R.id.img_dialog);
        Bitmap bitmap = BitmapFactory.decodeFile(contentDialog);
        imageView.setImageBitmap(bitmap);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickDialogListener != null) {
                    DialogResultItem item = new DialogResultItem(dialogId, MESSAGEDIALOG_BUTTON_OK, null);
                    onClickDialogListener.onClickDialog(item);
                }
            }
        });
    }


    public void onCreateDialogToast() {
        Toast.makeText(context, contentDialog, Toast.LENGTH_SHORT).show();
    }


    //region Ultils private
    public static int getHeightToolbar(Context context) {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    public static int getHeightStatusbar(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    //endregion
    //region MessageDialogBuilder
    public static class MessageDialogBuilder {
        private OnClickDialogListener onClickDialogListener;
        private Integer dialogId;
        private TypeMessageDialog typeMessageDialog;
        private String titleDialog;
        private String contentDialog;
        private String[] nameButtonsDialog;
        private Object object;
        private List<Item> mItemList;

        //TODO add list
        public MessageDialogBuilder setItemList(List<Item> mItemList) {
            this.mItemList = mItemList;
            return this;
        }

        public MessageDialogBuilder setDialogId(Integer dialogId) {
            this.dialogId = dialogId;
            return this;
        }

        public MessageDialogBuilder setOnClickDialogListener(OnClickDialogListener onClickDialogListener) {
            this.onClickDialogListener = onClickDialogListener;
            return this;
        }

        public MessageDialogBuilder setTypeMessageDialog(TypeMessageDialog typeMessageDialog) {
            this.typeMessageDialog = typeMessageDialog;
            return this;
        }

        public MessageDialogBuilder setTitleDialog(String titleDialog) {
            this.titleDialog = titleDialog;
            return this;
        }

        public MessageDialogBuilder setContentDialog(String contentDialog) {
            this.contentDialog = contentDialog;
            return this;
        }

        public MessageDialogBuilder setObject(Object object) {
            this.object = object;
            return this;
        }

        public MessageDialogBuilder setNameButtonsDialog(String[] nameButtonsDialog) {
            this.nameButtonsDialog = nameButtonsDialog;
            return this;
        }

        public MessageDialogHelper build(Context context) {
            MessageDialogHelper messageDialogHelper = new MessageDialogHelper(context);
            messageDialogHelper.setContentDialog(contentDialog);
            messageDialogHelper.setTypeMessageDialog(typeMessageDialog);
            messageDialogHelper.setDialogId(dialogId);
            messageDialogHelper.setObject(object);
            messageDialogHelper.setmItemList(mItemList);

            if (titleDialog != null && !TextUtils.isEmpty(titleDialog)) {
                messageDialogHelper.setTitleDialog(titleDialog);
            } else {
                messageDialogHelper.setTitleDialog(context.getResources().getString(R.string.dialog_title));
            }

            if (nameButtonsDialog != null && nameButtonsDialog.length > 0) {
                messageDialogHelper.setNameButtonsDialog(nameButtonsDialog);
            }

            if (onClickDialogListener != null) {
                messageDialogHelper.setOnClickDialogListener(onClickDialogListener);
            }
            messageDialogHelper.onCreate();

            return messageDialogHelper;
        }
    }
    //endregion
}
