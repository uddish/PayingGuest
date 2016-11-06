package app.paying.guest.release;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.uddishverma.pg_app_beta.R;

public class HelpUsActivity extends AppCompatActivity {


    FloatingActionButton fabOptions, fabCall, fabEmail;
    Animation fabOpen, fabClose, fabClockwise, fabAnticlockwise;
    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_us);

        fabOptions = (FloatingActionButton) findViewById(R.id.fab_menu);
        fabCall = (FloatingActionButton) findViewById(R.id.fab_call);
        fabEmail = (FloatingActionButton) findViewById(R.id.fab_email);

        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fabClockwise = AnimationUtils.loadAnimation(getApplication(), R.anim.rotate_clockwise);
        fabAnticlockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

        fabOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOpen)  {

                    fabCall.startAnimation(fabClose);
                    fabEmail.startAnimation(fabClose);
                    fabOptions.startAnimation(fabAnticlockwise);
                    fabCall.setClickable(false);
                    fabEmail.setClickable(false);
                    isOpen = false;
                }

                else    {

                    fabCall.startAnimation(fabOpen);
                    fabEmail.startAnimation(fabOpen);
                    fabOptions.startAnimation(fabClockwise);
                    fabCall.setClickable(true);
                    fabEmail.setClickable(true);
                    isOpen = true;

                }

            }
        });

        fabCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + String.valueOf(("9582138828"))));
                startActivity(intent);
            }
        });

        fabEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "payingguest16@gmail.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "body");

                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
            }
        });
    }
}
