package shaavy.mycollege.mvgr.mymvgr;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import shaavy.mycollege.mvgr.mymvgr.models.Attendance;
import shaavy.mycollege.mvgr.mymvgr.models.ViewFile;

public class ViewFileActivity extends AppCompatActivity {
    private static final String TAG = "ViewFileActivity";
    private Context mContext = ViewFileActivity.this;
    private String rid,type;

    private TextView result;
    private DatabaseReference firebaseDatabase;
    private FirebaseAuth mAuth;

    private RecyclerView mResultList;
    private Button notice;
    private CircleImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_file);

        result = (TextView)findViewById(R.id.result);
        if(getIntent() != null)
        {
            rid = getIntent().getStringExtra("rid");
            type = getIntent().getStringExtra("type");
            Log.d(TAG, "onCreate: the file to be displayed is "+rid);
            Log.d(TAG, "onCreate: the type is "+type);
        }

        mAuth = FirebaseAuth.getInstance();

        back = (CircleImageView)findViewById(R.id.goback);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });



        String user_id = mAuth.getCurrentUser().getUid();


        //firebaseDatabase = FirebaseDatabase.getInstance().getReference("results");
        //Log.d(TAG, "onCreate: "+firebaseDatabase);

        firebaseSearch(type,rid);




    }


    private void firebaseSearch(String type,String text) {
        Log.d(TAG, "firebaseSearch: the text is "+text);
        Log.d(TAG, "firebaseSearch: the type is "+type);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child(type).child(text);

        Log.d(TAG, "firebaseSearch: the data i got in Attendance is "+firebaseDatabase);
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: the values are "+dataSnapshot);
                Log.d(TAG, "onDataChange: the fff"+dataSnapshot.getValue());
                result.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        FirebaseRecyclerAdapter<ViewFile,ViewFileActivity.UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ViewFile, ViewFileActivity.UsersViewHolder>(
//                ViewFile.class,
//                R.layout.view_result,
//                ViewFileActivity.UsersViewHolder.class,
//                firebaseDatabase
//
//        ) {
//            @Override
//            protected void populateViewHolder(ViewFileActivity.UsersViewHolder viewHolder, final shaavy.mycollege.mvgr.mymvgr.models.ViewFile model, final int position) {
//
//                viewHolder.setDetails(model.getUid(),model.getResult(),mContext);
//
//            }
//        };

    //    mResultList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;
        Context ctx;
        public UsersViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

        }

        public final void setDetails(final String from, String message, final Context ctx){
            this.ctx = ctx;
            final TextView rollno = (TextView)mView.findViewById(R.id.rollno);
            final TextView result = (TextView)mView.findViewById(R.id.result);


            rollno.setText(from);
            result.setText(message);

        }

    }















}
