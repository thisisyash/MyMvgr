package shaavy.mycollege.mvgr.mymvgr.configure;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import shaavy.mycollege.mvgr.mymvgr.R;

public class Departments extends AppCompatActivity {
    private static final String TAG = "Departments";
    private Context mContext = Departments.this;
    GridLayout mainGrid;
    private String inString,cid,mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments);
        mainGrid = (GridLayout)findViewById(R.id.mainGrid);

        if(getIntent() != null)
        {
            inString = getIntent().getStringExtra("outString");
            cid = getIntent().getStringExtra("cid");
            mobile = getIntent().getStringExtra("mobile");

        }


        //Set Event
        setSingleEvent(mainGrid);
       // Toast.makeText(mContext, "The getted thing is " + inString, Toast.LENGTH_SHORT).show();
    }


    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(finalI==0){

                        Intent intent = new Intent(getBaseContext(),Years.class);
                        intent.putExtra("outString",inString+"cs");
                        intent.putExtra("cid",cid);
                        intent.putExtra("mobile",mobile);
                        startActivity(intent);
                        finish();

                    }

                    else if(finalI==1){

                        Intent intent = new Intent(getBaseContext(),Years.class);
                        intent.putExtra("outString",inString+"ec");
                        intent.putExtra("cid",cid);
                        intent.putExtra("mobile",mobile);
                        startActivity(intent);
                        finish();

                    }

                    else if(finalI==2){

                        Intent intent = new Intent(getBaseContext(),Years.class);
                        intent.putExtra("outString",inString+"ee");
                        intent.putExtra("cid",cid);
                        intent.putExtra("mobile",mobile);
                        startActivity(intent);
                        finish();

                    }

                    else if(finalI==3){

                        Intent intent = new Intent(getBaseContext(),Years.class);
                        intent.putExtra("outString",inString+"me");
                        intent.putExtra("cid",cid);
                        intent.putExtra("mobile",mobile);
                        startActivity(intent);
                        finish();

                    }

                    else if(finalI==4){

                        Intent intent = new Intent(getBaseContext(),Years.class);
                        intent.putExtra("outString",inString+"ci");
                        intent.putExtra("cid",cid);
                        intent.putExtra("mobile",mobile);
                        startActivity(intent);
                        finish();

                    }

                    else if(finalI==5){

                        Intent intent = new Intent(getBaseContext(),Years.class);
                        intent.putExtra("outString",inString+"it");
                        intent.putExtra("cid",cid);
                        intent.putExtra("mobile",mobile);
                        startActivity(intent);
                        finish();

                    }

                    else if(finalI==6){

                        Intent intent = new Intent(getBaseContext(),Years.class);
                        intent.putExtra("outString",inString+"ch");
                        intent.putExtra("cid",cid);
                        intent.putExtra("mobile",mobile);
                        startActivity(intent);
                        finish();

                    }

                    else if(finalI==7){

                        Intent intent = new Intent(getBaseContext(),Years.class);
                        intent.putExtra("outString",inString+"mb");
                        intent.putExtra("cid",cid);
                        intent.putExtra("mobile",mobile);
                        startActivity(intent);
                        finish();

                    }


                }
            });
        }
    }

}
