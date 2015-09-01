package edu.wisc.engr.enlight.decryptthis;

import com.firebase.client.DataSnapshot;

/**
 * Created by Sam on 8/31/2015.
 */
public interface FirebaseEventListener {
    public void onDatabaseUpdated(DataSnapshot dataSnapshot);
}
