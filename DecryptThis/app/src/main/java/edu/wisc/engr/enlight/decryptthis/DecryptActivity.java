package edu.wisc.engr.enlight.decryptthis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class DecryptActivity extends AppCompatActivity {

    private int selectedEncryptionValue;
    private int currentEncryptionValue;
    private String passedText;
    private String encryptedText;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt);
        selectedEncryptionValue = getRandomValue();
        passedText = DataHolder.getInstance().message.toLowerCase();
        bitmap = DataHolder.getInstance().bitmap;
        encryptedText = getEncryptedText(passedText, (-1 * selectedEncryptionValue));
        setupTextView();
        setupSlider();
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout_ImagePart_Decrypt);
        relativeLayout.setVisibility(View.INVISIBLE);
        ((ImageView) findViewById(R.id.ImageView_Image_Decrypt)).setImageBitmap(bitmap);
    }


    private void setupTextView() {
        TextView textView = (TextView) findViewById(R.id.TextView_EncryptedWord_Decrypt);
        textView.setText(encryptedText);
    }

    private String getEncryptedText(String msg, int offset) {
        String encrypted = "";
        int len = msg.length();
        for (int x = 0; x < len; x++) {
            char c = msg.charAt(x);
            char s = (char) (msg.charAt(x) + offset);
            if (c < 'a' || c > 'z') { //non letters
                encrypted += c;
            } else {
                if (s > 'z') {
                    encrypted += (char)(s - (26));
                } else if (s< 'a') {
                    encrypted += (char)(s +(26));
                }
                else{
                    encrypted +=s;
                }

            }
        }
        return encrypted;
    }

    private int getRandomValue() {
        //gives a value between 1 and 25
        int range = (25 - 1) + 1;
        return (int) (Math.random() * range) + 1;

    }

    private void setupSlider() {
        final TextView textView = (TextView) findViewById((R.id.TextView_EncryptedWord_Decrypt));
        SeekBar sk = (SeekBar) findViewById(R.id.seekBar);
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                currentEncryptionValue = progress;
                textView.setText(getEncryptedText(encryptedText, currentEncryptionValue));

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_decrypt, menu);
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

    public void checkDecryption(View view) {
        CharSequence text;
        if (currentEncryptionValue == selectedEncryptionValue) {
            RelativeLayout image = (RelativeLayout) findViewById(R.id.RelativeLayout_ImagePart_Decrypt);
            image.setVisibility(View.VISIBLE);

            RelativeLayout decrypt = (RelativeLayout) findViewById(R.id.RelativeLayout_DecryptPart_Decrypt);
            decrypt.setVisibility(View.INVISIBLE);


            text = "Correct!";
        } else {
            text = "Incorrect, try again.";
        }
        Context context = getApplicationContext();

        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }
    public void restart(View view){
        Intent myIntent=new Intent(view.getContext(),ReceiveActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
    }
}
