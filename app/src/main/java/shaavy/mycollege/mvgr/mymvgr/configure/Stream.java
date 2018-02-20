package shaavy.mycollege.mvgr.mymvgr.configure;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import shaavy.mycollege.mvgr.mymvgr.R;

public class Stream extends AppCompatActivity {

    private static final String TAG = "Stream";
    private Context mContext = Stream.this;
    private String inString,cid,mobile;
    private Button btech,mtech,mba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);

        btech = (Button)findViewById(R.id.btech);
        mtech = (Button)findViewById(R.id.mtech);
        mba = (Button)findViewById(R.id.mba);

        if(getIntent() != null)
        {
            inString = getIntent().getStringExtra("outString");
            cid = getIntent().getStringExtra("cid");
            mobile = getIntent().getStringExtra("mobile");

        }

       // Toast.makeText(mContext, "The getted thing is " + inString, Toast.LENGTH_SHORT).show();

        btech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,Departments.class);
                intent.putExtra("outString",inString+"bt");
                intent.putExtra("cid",cid);
                intent.putExtra("mobile",mobile);
                startActivity(intent);
                finish();
            }
        });

        mtech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,Departments.class);
                intent.putExtra("outString",inString+"mt");
                intent.putExtra("mobile",mobile);
                intent.putExtra("cid",cid);
                startActivity(intent);
                finish();
            }
        });

        mba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,Departments.class);
                intent.putExtra("outString",inString+"mb");
                intent.putExtra("mobile",mobile);
                intent.putExtra("cid",cid);
                startActivity(intent);
                finish();
            }
        });


    }
}
