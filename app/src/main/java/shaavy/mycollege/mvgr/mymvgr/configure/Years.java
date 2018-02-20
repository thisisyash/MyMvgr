package shaavy.mycollege.mvgr.mymvgr.configure;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.time.Year;

import shaavy.mycollege.mvgr.mymvgr.R;

public class Years extends AppCompatActivity {
    private static final String TAG = "Years";
    private Context mContext = Years.this;
    private String inString,cid,mobile;


    private Button firstYear,secondYear,thirdYear,fourthYear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_years);

        firstYear = (Button)findViewById(R.id.firstyear);
        secondYear = (Button)findViewById(R.id.secondyear);
        thirdYear = (Button)findViewById(R.id.thirdyear);
        fourthYear = (Button)findViewById(R.id.fouthyear);

        if(getIntent() != null)
        {
            inString = getIntent().getStringExtra("outString");
            cid = getIntent().getStringExtra("cid");
            mobile = getIntent().getStringExtra("mobile");

        }

       // Toast.makeText(mContext, "The getted thing is " + inString, Toast.LENGTH_SHORT).show();

        firstYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,Sections.class);
                intent.putExtra("outString",inString+"1");
                intent.putExtra("cid",cid);
                intent.putExtra("mobile",mobile);
                startActivity(intent);
                finish();
            }
        });

        secondYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,Sections.class);
                intent.putExtra("outString",inString+"2");
                intent.putExtra("cid",cid);
                intent.putExtra("mobile",mobile);
                startActivity(intent);
                finish();
            }
        });

        thirdYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,Sections.class);
                intent.putExtra("outString",inString+"3");
                intent.putExtra("cid",cid);
                intent.putExtra("mobile",mobile);
                startActivity(intent);
                finish();
            }
        });


        fourthYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,Sections.class);
                intent.putExtra("outString",inString+"4");
                intent.putExtra("cid",cid);
                intent.putExtra("mobile",mobile);
                startActivity(intent);
                finish();
            }
        });



    }
}
