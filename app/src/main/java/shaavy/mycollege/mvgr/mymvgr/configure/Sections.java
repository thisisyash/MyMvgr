package shaavy.mycollege.mvgr.mymvgr.configure;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;

import shaavy.mycollege.mvgr.mymvgr.R;
import shaavy.mycollege.mvgr.mymvgr.RegisterActivity;
import shaavy.mycollege.mvgr.mymvgr.SendActivity;

public class Sections extends AppCompatActivity {
    private static final String TAG = "Sections";
    private Context mContext=Sections.this;
    private String inString,cid,mobile;


    private Button seca,secb,secc,secd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sections);


        if(getIntent() != null)
        {
            inString = getIntent().getStringExtra("outString");
            Log.d(TAG, "onCreate: the instring is"+inString);
            cid = getIntent().getStringExtra("cid");
            mobile = getIntent().getStringExtra("mobile");

        }

        //Toast.makeText(mContext, "The getted thing is " + inString, Toast.LENGTH_SHORT).show();

        seca = (Button)findViewById(R.id.seca);
        secb = (Button)findViewById(R.id.secb);
        secc = (Button)findViewById(R.id.secc);
        secd = (Button)findViewById(R.id.secd);


        seca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inString.charAt(0)=='a'){
                    inString = inString.substring(1);
                    Intent intent = new Intent(mContext,SendActivity.class);
                    intent.putExtra("task","announcement");
                    intent.putExtra("outString",inString+"a");
                    startActivity(intent);
                    finish();
                }
                else if(inString.charAt(0)=='s'){
                    inString = inString.substring(1);
                    Intent intent = new Intent(mContext,SendActivity.class);
                    intent.putExtra("task","assignment");
                    intent.putExtra("outString",inString+"a");
                    startActivity(intent);
                    finish();
                }
                else if(inString.charAt(0)=='r'){
                    inString = inString.substring(1);
                    Intent intent = new Intent(mContext,RegisterActivity.class);
                    intent.putExtra("outString",inString+"a");
                    intent.putExtra("cid",cid);
                    intent.putExtra("mobile",mobile);
                    intent.putExtra("type","student");
                    startActivity(intent);
                    finish();
                }


            }
        });


        secb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inString.charAt(0)=='a'){
                    inString = inString.substring(1);
                    Intent intent = new Intent(mContext,SendActivity.class);
                    intent.putExtra("task","announcement");
                    intent.putExtra("outString",inString+"b");
                    startActivity(intent);
                    finish();
                }
                else if(inString.charAt(0)=='s'){
                    inString = inString.substring(1);
                    Intent intent = new Intent(mContext,SendActivity.class);
                    intent.putExtra("task","assignment");
                    intent.putExtra("outString",inString+"b");
                    startActivity(intent);
                    finish();
                }
                else if(inString.charAt(0)=='r'){
                    inString = inString.substring(1);
                    Intent intent = new Intent(mContext,RegisterActivity.class);
                    intent.putExtra("outString",inString+"b");
                    intent.putExtra("cid",cid);
                    intent.putExtra("mobile",mobile);
                    intent.putExtra("type","student");
                    startActivity(intent);
                    finish();
                }


            }
        });

        secc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inString.charAt(0)=='a'){
                    inString = inString.substring(1);
                    Intent intent = new Intent(mContext,SendActivity.class);
                    intent.putExtra("task","announcement");
                    intent.putExtra("outString",inString+"c");
                    startActivity(intent);
                    finish();
                }
                else if(inString.charAt(0)=='s'){
                    inString = inString.substring(1);
                    Intent intent = new Intent(mContext,SendActivity.class);
                    intent.putExtra("task","assignment");
                    intent.putExtra("outString",inString+"c");
                    startActivity(intent);
                    finish();
                }
                else if(inString.charAt(0)=='r'){
                    inString = inString.substring(1);
                    Intent intent = new Intent(mContext,RegisterActivity.class);
                    intent.putExtra("outString",inString+"c");
                    intent.putExtra("cid",cid);
                    intent.putExtra("mobile",mobile);
                    intent.putExtra("type","student");
                    startActivity(intent);
                    finish();
                }


            }
        });

        secd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inString.charAt(0)=='a'){
                    inString = inString.substring(1);
                    Intent intent = new Intent(mContext,SendActivity.class);
                    intent.putExtra("task","announcement");
                    intent.putExtra("outString",inString+"d");
                    startActivity(intent);
                }
                else if(inString.charAt(0)=='s'){
                    inString = inString.substring(1);
                    Intent intent = new Intent(mContext,SendActivity.class);
                    intent.putExtra("task","assignment");
                    intent.putExtra("outString",inString+"d");
                    startActivity(intent);
                }
                else if(inString.charAt(0)=='r'){
                    inString = inString.substring(1);
                    Intent intent = new Intent(mContext,RegisterActivity.class);
                    intent.putExtra("outString",inString+"d");
                    intent.putExtra("cid",cid);
                    intent.putExtra("mobile",mobile);
                    intent.putExtra("type","student");
                    startActivity(intent);
                }


            }
        });




    }
}
