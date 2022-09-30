package algonquin.cst2335.ouni0002.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import algonquin.cst2335.ouni0002.data.MainViewModel;
import algonquin.cst2335.ouni0002.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding variableBinding;
    private MainViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model= new ViewModelProvider(this).get(MainViewModel.class);
        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        TextView mytext = variableBinding.textview;
        Button mybutton=variableBinding.mybutton;
        EditText myedit = variableBinding.myedittext;
        CheckBox mycheckbox= variableBinding.mycheckbox;
        Switch myswitch = variableBinding.myswitch;
        RadioButton myradiobutton= variableBinding.myradiobutton;
        ImageView myimage=variableBinding.myimage;
        ImageButton myimagebutton=variableBinding.myimagebutton;
        myimagebutton.setOnClickListener(vw->{
            Context context = getApplicationContext();
            CharSequence text = "The width = " + myimagebutton.getWidth() + " and height = " + myimagebutton.getHeight() ;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });
        mycheckbox.setOnCheckedChangeListener((btn,isChecked)->{
            model.isSelected.postValue(isChecked);
        });

        myswitch.setOnCheckedChangeListener((btn,isChecked)->{
            model.isSelected.postValue(isChecked);
        });
        myradiobutton.setOnCheckedChangeListener((btn,isChecked)->{
            model.isSelected.postValue(isChecked);

        });
        model.isSelected.observe(this, s->{
            mycheckbox.setChecked(s);
            myswitch.setChecked(s);
            myradiobutton.setChecked(s);
            Context context = getApplicationContext();
            CharSequence text = "The value is now: " + s;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });
        mybutton.setOnClickListener(vw ->{

            model.editString.postValue(myedit.getText().toString());
            model.editString.observe(this, s->{
                mytext.setText("Your edit text has: " + s);
            });
            //mytext.setText ( "Your edit text has:" + model. editString);
        });
    }
}