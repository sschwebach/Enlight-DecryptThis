package edu.wisc.engr.enlight.decryptthis;

import android.graphics.Bitmap;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by Sam on 8/31/2015.
 */
public class DataHolder {
    private static DataHolder ourInstance;

    public static DataHolder getInstance() {
        return ourInstance;
    }

    public static void createDataHolder(){
        ourInstance = new DataHolder();
    }

    public String message;
    public Bitmap bitmap;
    private Firebase mFirebase;
    String dataBaseStringKey;
    private FirebaseEventListener mListener;

    private DataHolder() {
        mFirebase = new Firebase("https://decryptthis.firebaseio.com");
        mFirebase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mListener != null) {
                    mListener.onDatabaseUpdated(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void setFirebaseEventListener(FirebaseEventListener l){
        this.mListener = l;
    }



}
