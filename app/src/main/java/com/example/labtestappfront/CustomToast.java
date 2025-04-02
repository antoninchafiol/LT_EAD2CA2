package com.example.labtestappfront;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast {

    public static void showToast(Context context, String message, ToastType type) {
        // Create a LinearLayout to be the root view of the Toast
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        layout.setPadding(16, 16, 16, 16);

        // Create an ImageView (icon)
        ImageView imageView = new ImageView(context);
        layout.addView(imageView);

        // Create a TextView to display the message
        TextView textView = new TextView(context);
        textView.setText(message);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(16);
        layout.addView(textView);

        // Set custom background depending on the type
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(16);
        switch (type) {
            case SUCCESS:
                drawable.setColor(Color.parseColor("#4CAF50")); // Green
                imageView.setImageResource(android.R.drawable.checkbox_on_background); // Checkmark
                break;
            case ERROR:
                drawable.setColor(Color.parseColor("#D32F2F")); // Red
                imageView.setImageResource(android.R.drawable.ic_delete); // Red Cross
                break;
            case WARNING:
                drawable.setColor(Color.parseColor("#FFA500")); // Orange/Yellow
                imageView.setImageResource(android.R.drawable.ic_dialog_alert); // Warning Icon
                break;
        }

        layout.setBackground(drawable);

        // Create and show the Toast
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 100); // Toast position
        toast.setView(layout);
        toast.show();
    }

    // Enum for different Toast types
    public enum ToastType {
        SUCCESS,
        ERROR,
        WARNING
    }
}
