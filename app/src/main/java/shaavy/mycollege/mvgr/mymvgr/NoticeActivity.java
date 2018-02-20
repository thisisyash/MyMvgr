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

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import shaavy.mycollege.mvgr.mymvgr.models.Notice;

public class NoticeActivity extends AppCompatActivity {
    private static final String TAG = "NoticeActivity";
    private final Context mContext = NoticeActivity.this;

    private DatabaseReference firebaseDatabase;
    private FirebaseAuth mAuth;
    private Button notice;
    private CircleImageView back_arrow;
    private RecyclerView mResultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        notice = (Button)findViewById(R.id.notice);

        back_arrow = (CircleImageView)findViewById(R.id.goback);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,SearchActivity.class);
                intent.putExtra("outString","n");
                startActivity(intent);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();

        DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);
                String type = user.getType();
                if(type.equals("student")){
                    notice.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


        firebaseDatabase = FirebaseDatabase.getInstance().getReference("Users").child(user_id).child("notices");
        Log.d(TAG, "onCreate: "+firebaseDatabase);

        LinearLayoutManager ly1 = new LinearLayoutManager(this);
        ly1.setReverseLayout(true);
        ly1.setStackFromEnd(true);
        mResultList = (RecyclerView)findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(ly1);

        firebaseSearch("sdf");
    }


    private void firebaseSearch(String text) {

        Log.d(TAG, "firebaseSearch: " + "--- firebase search started");
        FirebaseRecyclerAdapter<Notice,UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Notice, UsersViewHolder>(
                Notice.class,
                R.layout.list_layout,
                UsersViewHolder.class,
                firebaseDatabase

        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, final shaavy.mycollege.mvgr.mymvgr.models.Notice model, final int position) {

                viewHolder.setDetails(model.getFrom(),model.getMessage(),model.getTime(),mContext);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                       Intent intent = new Intent(mContext,ViewNotice.class);
                       intent.putExtra("from",model.getFrom());
                       intent.putExtra("message",model.getMessage());
                       intent.putExtra("time",model.getTime());
                       intent.putExtra("image",model.getImage());

                        startActivity(intent);
                    }
                });


            }
        };

        mResultList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;
        Context ctx;
        public UsersViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

        }

        public final void setDetails(final String from, String message,final Long time, final Context ctx){
            this.ctx = ctx;
            final TextView user_name = (TextView)mView.findViewById(R.id.name_text);
            final TextView user_status = (TextView)mView.findViewById(R.id.status_text);
            final TextView sclasst = (TextView)mView.findViewById(R.id.sclass);
            final ImageView user_image = (ImageView) mView.findViewById(R.id.profile_image);

            user_name.setText(from);
            user_status.setText(message);

            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(from);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Users user = dataSnapshot.getValue(Users.class);


                    user_name.setText("A new Notice from");
                    SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    sfd.format(new Date(time));
                    Log.d(TAG, "onDataChange: sdf"+sfd);


                    sclasst.setText("on"+sfd.format(new Date(time)));
                    user_status.setText(user.getName());

                    Glide.with(ctx).load(user.getImage()).into(user_image);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });

        }

    }

}
