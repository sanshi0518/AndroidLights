package com.github.sanshi0518.androidlights.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.sanshi0518.androidlights.R;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * IOS风格的提示框.
 *
 * @author wanglei
 * @since 15/4/28 下午5:42
 */
public class IOSStyleDialog extends Dialog {

    public IOSStyleDialog(Context context) {
        super(context);
    }

    public IOSStyleDialog(Context context, int theme) {
        super(context, theme);
    }

    protected IOSStyleDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder {
        private Context mContext;
        private String mTitle;
        private String mMessage;
        private String mConfirmText;
        private String mCancelText;

        private boolean mCancelable = false;

        private View mContentView;

        private DialogInterface.OnClickListener mConfirmClickListener;
        private DialogInterface.OnClickListener mCancelClickListener;


        public Builder(Context context) {
            mContext = context;
        }

        public Builder setMessage(String message) {
            mMessage = message;
            return this;
        }

        public Builder setMessage(int message) {
            mMessage = (String) mContext.getText(message);
            return this;
        }

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setTitle(int title) {
            mTitle = (String) mContext.getText(title);
            return this;
        }

        public Builder setContentView(View view) {
            mContentView = view;
            return this;
        }

        /**
         * Sets whether this dialog is cancelable with the BACK key.
         *
         * @param cancelable
         * @return
         */
        public Builder setCancelable(boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }

        public Builder setPositiveButton(int confirmText, DialogInterface.OnClickListener listener) {
            mConfirmText = (String) mContext.getText(confirmText);
            mConfirmClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String confirmText, DialogInterface.OnClickListener listener) {
            mConfirmText = confirmText;
            mConfirmClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int cancelText, DialogInterface.OnClickListener listener) {
            mCancelText = (String) mContext.getText(cancelText);
            mCancelClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String cancelText, DialogInterface.OnClickListener listener) {
            mCancelText = cancelText;
            mCancelClickListener = listener;
            return this;
        }

        public IOSStyleDialog create() {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final IOSStyleDialog dialog = new IOSStyleDialog(mContext, R.style.ios_style_dialog);

            View dialogLayout = inflater.inflate(R.layout.dialog_ios_style, null);

            dialog.addContentView(dialogLayout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            initTitle(dialogLayout);

            initConfirmButton(dialog, dialogLayout);

            initCancelButton(dialog, dialogLayout);

            initMessageContent(dialogLayout);

            dialog.setContentView(dialogLayout);

            dialog.setCancelable(mCancelable);

            return dialog;
        }

        private void initConfirmButton(final IOSStyleDialog dialog, View dialogLayout) {
            if (isBlank(mConfirmText)) {
                dialogLayout.findViewById(R.id.rightButton).setVisibility(View.GONE);
                dialogLayout.findViewById(R.id.dividerLine).setVisibility(View.GONE);
                dialogLayout.findViewById(R.id.leftButton).setBackgroundResource(R.drawable.dialog_iosstyle_left_btn_select);
                return;
            }

            Button confirmButton = (Button) dialogLayout.findViewById(R.id.rightButton);

            confirmButton.setText(mConfirmText);

            if (mConfirmClickListener != null) {
                confirmButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        mConfirmClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                    }
                });
            } else {
                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        }

        private void initCancelButton(final IOSStyleDialog dialog, View dialogLayout) {
            if (isBlank(mCancelText)) {
                dialogLayout.findViewById(R.id.leftButton).setVisibility(View.GONE);
                dialogLayout.findViewById(R.id.dividerLine).setVisibility(View.GONE);
                dialogLayout.findViewById(R.id.rightButton).setBackgroundResource(R.drawable.dialog_iosstyle_single_btn_select);
                return;
            }

            Button cancelButton = (Button) dialogLayout.findViewById(R.id.leftButton);

            cancelButton.setText(mCancelText);

            if (mCancelClickListener != null) {
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        mCancelClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                    }
                });
            } else {
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        }

        private void initMessageContent(View dialogLayout) {
            if (mMessage != null) {
                ((TextView) dialogLayout.findViewById(R.id.message)).setText(mMessage);
                return;
            }

            if (mContentView != null) {
                RelativeLayout messageLayout = (RelativeLayout) dialogLayout.findViewById(R.id.messageLayout);

                messageLayout.removeAllViews();

                messageLayout.addView(mContentView, new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }

        private void initTitle(View dialogLayout) {
            TextView titleView = (TextView) dialogLayout.findViewById(R.id.title);
            titleView.setText(isNotBlank(mTitle) ? mTitle : "");
            titleView.getPaint().setFakeBoldText(true);
        }

    }

}
