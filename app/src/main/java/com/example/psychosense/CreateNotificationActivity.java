package com.example.psychosense;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.Manifest;
import java.util.Calendar;

public class CreateNotificationActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "Idan";
    private static final int ALARM_PENDING_INTENT_REQUEST_CODE = 1;
    private static final int NOTIFICATION_ID = 1;

    private TextView timeTV;
    private Button createBtn, cancelBtn;
    private Intent intent;
    private Boolean hasSelectedTime;
    private int hour = 0, minute = 0;
    
    //perrmission launcher
    // Register the permissions callback, which handles the user's response to the
    // system permissions dialog. Save the return value, an instance of
    // ActivityResultLauncher, as an instance variable.
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                    Log.d(TAG, "permission granted: ");
                } else {
                    Log.d(TAG, "in launcer of the ask for rational: in launcher");
                    // Explain to the user that the feature is unavailable because the
                    // feature requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notification);

        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) &&
        (ContextCompat.checkSelfPermission(this, Manifest.permission.SCHEDULE_EXACT_ALARM) == PackageManager.PERMISSION_GRANTED)
        ){
            // You can use the API that requires the permission.
            //performAction(...);
            Log.d(TAG, "onCreate: granted 50");
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.POST_NOTIFICATIONS)) {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected, and what
            // features are disabled if it's declined. In this UI, include a
            // "cancel" or "no thanks" button that lets the user continue
            // using your app without granting the permission.
            //showInContextUI(...);
            Log.d(TAG, "onCreate: show rational 1");
        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS);
        }

        createBtn = findViewById(R.id.createBtn);
        createBtn.setOnClickListener(this);

        cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(this);

        timeTV = findViewById(R.id.timeTV);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick");
        if(v.getId() == R.id.createBtn) {
            popTimeMaker();
        }
        else if(v.getId() == R.id.cancelBtn) {
            Intent intent2 = new Intent(getApplicationContext(), AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent2, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
            timeTV.setText("No alarm has been set");
        }
    }

    //
    public void popTimeMaker() {
        Log.d(TAG, "popTimeMaker");
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hasSelectedTime = true;
                hour = selectedHour;
                minute = selectedMinute;
                Log.d(TAG, "1");
                if (minute < 10) {
                    timeTV.setText("an alarm has been set for " + hour + ":0" + minute);
                }
                else {
                    timeTV.setText("an alarm has been set for " + hour + ":" + minute);
                }
                Log.d(TAG, "onTimeSet: alarm created: " + hour + ":" + minute);

                createAlarm(CreateNotificationActivity.this);
            }
        };

        final int SPINNER_MODE = 2;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, SPINNER_MODE, onTimeSetListener, hour, minute, true);
        timePickerDialog.show();
    }


        public void createAlarm(Context context) {
            Log.d(TAG, "createAlarm");
            //create new alarm
            try {
                AlarmManager newAlarm;
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, hour);
                c.set(Calendar.MINUTE, minute);
                c.set(Calendar.SECOND, 0);
                Log.d(TAG, "2");
                Intent intent1 = new Intent(getApplicationContext(), AlertReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 1, intent1, PendingIntent.FLAG_IMMUTABLE);

                newAlarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
                Log.d(TAG, "3");
                Log.d(TAG, "4");
                newAlarm.set(AlarmManager.RTC_WAKEUP,  c.getTimeInMillis() , pendingIntent);
                //newAlarm.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
                Log.d(TAG, "5");
            } catch (Exception e) {
                Log.d(TAG, "createAlarm: fail");
                throw new RuntimeException(e);
            }
        }
    }


