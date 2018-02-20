package shaavy.mycollege.mvgr.mymvgr;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import shaavy.mycollege.mvgr.mymvgr.configure.Stream;

public class CollegeId extends AppCompatActivity {
    private static final String TAG = "CollegeId";

    private Context mContext = CollegeId.this;

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;


    private EditText college_id;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_id);

        firebaseDatabase = FirebaseDatabase.getInstance();


        mAuth = FirebaseAuth.getInstance();


        college_id = (EditText) findViewById(R.id.college_id);
        next = (Button) findViewById(R.id.next);
        Intent intent = getIntent();

        final String mobile = intent.getStringExtra("mobile");
        final String type = intent.getStringExtra("type");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String cid = college_id.getText().toString();

                final String mUserId = mAuth.getCurrentUser().getUid();

                if (type.equals("student")) {
                    //Key does not exist
                    Log.d(TAG, "onDataChange: Its not a faculty id");
                    Intent intent = new Intent(CollegeId.this, Stream.class);
                    intent.putExtra("cid", cid);
                    intent.putExtra("mobile", mobile);
                    intent.putExtra("outString", "r");
                    startActivity(intent);
                    finish();


                } else {
                    Log.d(TAG, "onDataChange: its a faculty id -----------");
                    //need to insert the data in users table as faculty this is registration

                    //Key exists
                    Intent intent = new Intent(CollegeId.this, RegisterActivity.class);
                    intent.putExtra("cid", cid);
                    intent.putExtra("mobile", mobile);
                    intent.putExtra("outString", "zzzzzzz");
                    intent.putExtra("type", "faculty");
                    startActivity(intent);
                    finish();

                }
            }


        });

    }
}
