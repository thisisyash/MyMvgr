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
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import shaavy.mycollege.mvgr.mymvgr.configure.Stream;
import shaavy.mycollege.mvgr.mymvgr.models.Announcement;


public class AnnouncementActivity extends AppCompatActivity {
    private static final String TAG = "AnnouncementActivity";
    private final Context mContext  = AnnouncementActivity.this;

    private DatabaseReference firebaseDatabase;
    private FirebaseAuth mAuth;

    private RecyclerView mResultList;
    private Button notice;
    private CircleImageView back;
    private TextView nothing;

    private static String sclass="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        mAuth = FirebaseAuth.getInstance();

        notice = (Button)findViewById(R.id.notice);
        back = (CircleImageView)findViewById(R.id.goback);
        nothing = (TextView)findViewById(R.id.nothing);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,Stream.class);
                intent.putExtra("outString","a");
                startActivity(intent);
                finish();
            }
        });

        String user_id = mAuth.getCurrentUser().getUid();

        DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);
                sclass = user.getSclass();
                String type = user.getType();
                if(type.equals("student")){
                    notice.setVisibility(View.GONE);
                }
                firebaseSearch(sclass);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        LinearLayoutManager ly1 = new LinearLayoutManager(this);
        ly1.setReverseLayout(true);
        ly1.setStackFromEnd(true);
        mResultList = (RecyclerView)findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(ly1);
    }

    private void firebaseSearch(String text) {
        Log.d(TAG, "firebaseSearch: the text is "+text);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference(text).child("announcements");

        firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Log.d(TAG, "onDataChange: there is announcements for you");
                    nothing.setVisibility(View.INVISIBLE);

                }else {
                    Log.d(TAG, "onDataChange: no announcements");
                    nothing.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        Log.d(TAG, "firebaseSearch: the data i got in announcement is "+firebaseDatabase);
        FirebaseRecyclerAdapter<Announcement,AnnouncementActivity.UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Announcement, AnnouncementActivity.UsersViewHolder>(
                Announcement.class,
                R.layout.list_layout,
                AnnouncementActivity.UsersViewHolder.class,
                firebaseDatabase

        ) {
            @Override
            protected void populateViewHolder(AnnouncementActivity.UsersViewHolder viewHolder, final shaavy.mycollege.mvgr.mymvgr.models.Announcement model, final int position) {

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

            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(from);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    Users user = dataSnapshot.getValue(Users.class);
                    user_name.setText("Announcement from");
                    SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    sfd.format(new Date(time));
                    sclasst.setText("on"+sfd.format(new Date(time)));
                    user_status.setText(user.getName());
                    UrlImageViewHelper.setUrlDrawable(user_image,user.getImage().toString() );

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }

    }

}
