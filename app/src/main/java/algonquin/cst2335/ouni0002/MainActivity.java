package algonquin.cst2335.ouni0002;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This page takes a password and validates if it has the requirements
 * @author ahmed
 * @version 1.0
 *
 */
public class MainActivity extends AppCompatActivity {

    /** This holds the text at the center of the screen*/
    TextView tv = null;

    /** This holds the password field below the text*/
    EditText et = null;

    /** This holds the button at the bottom of the screen*/
    Button btn = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);
        et = findViewById(R.id.editText);
        btn = findViewById(R.id.button);



        btn.setOnClickListener( clk -> {
            String password = et.getText().toString();
            checkPasswordComplexity( password );
            if(checkPasswordComplexity( password )){
                tv.setText("Your password meets the requirements");
            }else{
                tv.setText("You shall not pass!");
            }
        });

    }

    /** This function is going to check whether the password has a special character
     *
     * @param c
     * @retrun returns true if the character is one of the cases
     */
    boolean isSpecialCharacter(char c){
        switch (c){
            case '#':
            case '?':
            case '*':
            case '$':
            case '%':
            case '&':
            case '^':
            case '!':
            case '@':
                return true;
            default:
                return false;
        }
    }

    /** This function is going to check whether the password meets the requirements
     *
     * @param pw
     * @retrun returns true if the password meets requirements
     */
    boolean checkPasswordComplexity(String pw) {

        boolean foundUpperCase = false;
        boolean foundLowerCase = false;
        boolean foundNumber = false;
        boolean foundSpecial = false;



        for (int i = 0; i < pw.length(); i++) {

            if(isSpecialCharacter(pw.charAt(i))){
                foundSpecial = true;
            }


            if (Character.isDigit(pw.charAt(i))) {
                foundNumber = true;
            }

            if (Character.isUpperCase(pw.charAt(i))) {
                foundUpperCase = true;
            }
            if (Character.isLowerCase(pw.charAt(i))) {
                foundLowerCase = true;
            }


        }

        if (!foundUpperCase) {
            Toast.makeText(getApplicationContext(), "The password does not have an Upper Case letter", Toast.LENGTH_SHORT).show();
            return false;

        } else if (!foundLowerCase) {
            Toast.makeText(getApplicationContext(), "The password does not have a Lower Case letter", Toast.LENGTH_SHORT).show();
            return false;

        } else if (!foundNumber) {
            Toast.makeText(getApplicationContext(), "The password does not have a Number", Toast.LENGTH_SHORT).show(); // Say that they are missing a lower case letter;
            return false;

        } else if (!foundSpecial) {
            Toast.makeText(getApplicationContext(), "The password does not have a Special character", Toast.LENGTH_SHORT).show(); // Say that they are missing a lower case letter;
            return false;
        } else {

            return true;
        }

    }
}