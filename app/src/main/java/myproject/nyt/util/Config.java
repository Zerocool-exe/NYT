package myproject.nyt.util;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Config
{
    Context ctx;
    NotificationManager notificationManager=null;
    public Config(Context context)
    {
        this.ctx=context;
    }

    //public String GET_ARTICLES = "http://115.134.221.250:18080/NYT/GetArticlesList";
    public String GET_ARTICLES = "https://api.nytimes.com/svc/search/v2/articlesearch.json?";
    public String MOST_VIEWED = "https://api.nytimes.com/svc/mostpopular/v2/viewed/30.json?";
    public String MOST_SHARED = "https://api.nytimes.com/svc/mostpopular/v2/shared/30.json?";
    public String MOST_EMAILED = "https://api.nytimes.com/svc/mostpopular/v2/emailed/30.json?";

    public boolean isNetworkAvailable(Context context)
    {
        boolean result = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (cm != null)
            {
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
                if (capabilities != null)
                {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                    {
                        result = true;
                    }
                    else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                    {
                        result = true;
                    }
                }
            }
        }
        else
        {
            if (cm != null)
            {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    // connected to the internet
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        result = true;
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }
    public JSONObject convertStreamToString(InputStream is)
    {
        String json="";
        JSONObject jObj=null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jObj;
    }
    public String imageFileToByte(File file){

        Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }
    public byte[] encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return b;
    }
    public void requestPermissions()
    {
        ActivityCompat.requestPermissions((Activity) ctx, new String[]{Manifest.permission.WAKE_LOCK, Manifest.permission.RECEIVE_BOOT_COMPLETED}, 200);
    }
    public void requestAllPermissions()
    {
        ActivityCompat.requestPermissions((Activity) ctx, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION}, 200);
    }
    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
        String strDate = sdf.format(new Date());
        return strDate;
    }
    public String getTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        String strDate = sdf.format(new Date());
        return strDate;
    }
    public String getYYYYMMDD() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String strDate = sdf.format(new Date());
        return strDate;
    }
    public String convertDOBDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date currentdate = null;
        try {
            currentdate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        return sdf2.format(currentdate);
    }

    public void playSong(Context _ctx)
    {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(_ctx, defaultSoundUri);
        r.play();
    }
    public void sendNotification(String title, String message)
    {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(ctx, defaultSoundUri);
        r.play();
        /*notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        String ADMIN_CHANNEL_ID ="admin_channel";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels();
        }

        Intent notificationIntent = null;
        int notificationId = new Random().nextInt(60000);
        notificationIntent = new Intent(ctx, MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx,notificationId+1, notificationIntent,PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(ctx, ADMIN_CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_logo)
                .setContentTitle(title) // title
                .setContentText(message) // body message
                .setAutoCancel(true)
                .setNumber(1)
                .setShowWhen(true)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setContentIntent(pendingIntent);
        notificationManager.notify(notificationId+1, notificationBuilder.build());*/
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels(){
        final Uri defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        CharSequence adminChannelName = "Global channel";
        String adminChannelDescription = "Notifications sent from the app admin";

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();
        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel("admin_channel", adminChannelName, NotificationManager.IMPORTANCE_HIGH);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.canShowBadge();
        adminChannel.setShowBadge(true);
        adminChannel.setLightColor(Color.GRAY);
        adminChannel.enableVibration(true);
        adminChannel.setSound(defaultRingtoneUri,audioAttributes);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }
}
