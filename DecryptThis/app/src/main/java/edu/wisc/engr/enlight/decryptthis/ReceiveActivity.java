package edu.wisc.engr.enlight.decryptthis;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

public class ReceiveActivity extends Activity implements FirebaseEventListener {

    String messageStringValue;
    String messageStringKey = "key";
    String imageStringValue;
    String imageStringKey = "image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        Log.e("Create", "OnCreate called");
        Firebase.setAndroidContext(this);
        DataHolder.createDataHolder();
        DataHolder.getInstance().setFirebaseEventListener(this);

    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.e("Resume", "OnResume called");
        if (DataHolder.getInstance() == null){
            DataHolder.createDataHolder();
        }
        DataHolder.getInstance().setFirebaseEventListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_receive, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDatabaseUpdated(DataSnapshot dataSnapshot) {
        Map<String, Object> nvp = (Map<String, Object>) dataSnapshot.getValue();
        messageStringValue = "" + nvp.get(messageStringKey);
        imageStringValue = "" + nvp.get(imageStringKey);

        //once we have these add them to our singleton and start the new activity
        DataHolder.getInstance().message = messageStringValue;
        byte[] decodedString = Base64.decode(imageStringValue, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        DataHolder.getInstance().bitmap = decodedByte;
        Log.e("Data Received", "Starting decrypt activity with message: " + messageStringValue);
        DataHolder.getInstance().setFirebaseEventListener(null);
        /** Called when the user clicks the Send button */

        Intent intent = new Intent(ReceiveActivity.this, DecryptActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
