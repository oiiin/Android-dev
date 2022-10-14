package algonquin.cst2335.ouni0002;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        TextView textView = (TextView) findViewById(R.id.textView2);

        textView.setText("Welcome back " + emailAddress);
        Button button1 = (Button) findViewById(R.id.button3);


        button1.setOnClickListener(clk -> {
            Intent call = new Intent(Intent.ACTION_DIAL);
            EditText editText2 = (EditText) findViewById(R.id.editText2);
            String number = editText2.getText().toString();
            call.setData(Uri.parse("tel:" + number));
            startActivity(call);

        });




        Button button2 = (Button) findViewById(R.id.button4);
        ImageView profileImage = (ImageView) findViewById(R.id.imageView);

        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult() ,
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == Activity.RESULT_OK) {

                            Intent data = result.getData();
                            Bitmap thumbnail = data.getParcelableExtra("data");
                            profileImage.setImageBitmap(thumbnail);

                            FileOutputStream fOut = null;

                            try { fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);

                                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);

                                fOut.flush();

                                fOut.close();

                            }

                            catch (IOException e)

                            { e.printStackTrace();

                            }
                        }

                        File file = new File( getFilesDir(), "Picturen.png");

                        if(file.exists())

                        {
                            Bitmap theImage = BitmapFactory.decodeFile("Picturen.png");
                            profileImage.setImageBitmap(theImage);

                        }
                    }
                });

        button2.setOnClickListener(clk -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            cameraResult.launch(cameraIntent);


        });


    }
}