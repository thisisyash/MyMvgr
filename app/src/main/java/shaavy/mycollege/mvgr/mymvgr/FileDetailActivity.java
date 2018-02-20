package shaavy.mycollege.mvgr.mymvgr;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FileDetailActivity extends AppCompatActivity {

    private static final String TAG = "FileDetailActivity";
    private Context mContext = FileDetailActivity.this;
    private String  inString;
    private Button submit;
    private EditText details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_detail);
        submit = (Button)findViewById(R.id.register_btn);
        details = (EditText)findViewById(R.id.message_view);
        if(getIntent() != null)
        {
            inString = getIntent().getStringExtra("outString");

        }

        if(inString.equals("att")){

         submit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(mContext,FileActivity.class);
                 intent.putExtra("type","att");
                 intent.putExtra("data",details.getText().toString());
                 startActivity(intent);
                 finish();
             }
         });

        }

        else if(inString.equals("res")){
            Intent intent = new Intent(mContext,FileActivity.class);
            intent.putExtra("type","res");
            intent.putExtra("data",details.getText().toString());
            startActivity(intent);
            finish();

        }

    }
}
