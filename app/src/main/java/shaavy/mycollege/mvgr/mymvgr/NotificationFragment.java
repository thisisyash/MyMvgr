package shaavy.mycollege.mvgr.mymvgr;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import shaavy.mycollege.mvgr.mymvgr.models.Blog;
import shaavy.mycollege.mvgr.mymvgr.models.Notice;
//time imports



/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends android.support.v4.app.Fragment {
    private static final String TAG = "NotificationFragment";
    private DatabaseReference firebaseDatabase;
    private FirebaseAuth mAuth;
    private Button notice;

    private TextView comeback;
    private RecyclerView mResultList;

    //time
    private String time;
    private Calendar calander;
    private SimpleDateFormat simpleDateFormat;
    private TimePicker alarmTimePicker;

    private Intent intent;
    private TaskStackBuilder stackBuilder;
    private PendingIntent pendingIntent;
    private NotificationManager notificationManager;

    private CircleImageView pikaachu;
    private TextView comback1;


    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notification, container, false);

        notice = (Button)v.findViewById(R.id.notice);
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SendActivity.class);
                intent.putExtra("outString","b");
                intent.putExtra("task","blog");
                startActivity(intent);
            }
        });
        comeback = (TextView)v.findViewById(R.id.comeback);
        pikaachu = (CircleImageView)v.findViewById(R.id.pikaachu);
        comback1 = (TextView)v.findViewById(R.id.comeback1);

        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("blog");
        Log.d(TAG, "onCreate: "+firebaseDatabase);

        LinearLayoutManager ly1 = new LinearLayoutManager(getActivity());
        ly1.setReverseLayout(true);
        ly1.setStackFromEnd(true);
        mResultList = (RecyclerView)v.findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(ly1);
        calander = Calendar.getInstance();


        simpleDateFormat = new SimpleDateFormat("HH:mm");
        time = simpleDateFormat.format(calander.getTime());
        Log.d(TAG, "onCreateView: the current time is "+time);

        try {
            Date startTime = simpleDateFormat.parse("16:00");
            Date currentTime = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Log.d(TAG, "onCreateView: "+startTime);
            Log.d(TAG, "onCreateView: "+currentTime);
            if(currentTime.after(startTime)){
                Log.d(TAG, "onCreateView: inside the loop");
                comeback.setVisibility(View.GONE);
                pikaachu.setVisibility(View.GONE);
                comback1.setVisibility(View.GONE);
                //Log.d(TAG, "Alert: Something is going to Happen");
                mResultList.setVisibility(View.VISIBLE);
                firebaseSearch("sdf");
            }
            else {
                Log.d(TAG, "onCreateView: outside the loop");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }



        return v;
    }

    private void firebaseSearch(String text) {

        Log.d(TAG, "firebaseSearch: " + "--- firebase search started");
        FirebaseRecyclerAdapter<Blog,NotificationFragment.UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, NotificationFragment.UsersViewHolder>(
                Blog.class,
                R.layout.layout_view_post,
                NotificationFragment.UsersViewHolder.class,
                firebaseDatabase

        ) {
            @Override
            protected void populateViewHolder(NotificationFragment.UsersViewHolder viewHolder, final shaavy.mycollege.mvgr.mymvgr.models.Blog model, final int position) {

                viewHolder.setDetails(model.getFrom(),model.getMessage(),model.getTime(),model.getImage(),getActivity());
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getActivity(),ViewNotice.class);
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

        public final void setDetails(final String from, String message,final Long time,final String image, final Context ctx){
            this.ctx = ctx;

            final TextView user_name = (TextView)mView.findViewById(R.id.username);
            final TextView user_status = (TextView)mView.findViewById(R.id.postmessage);
            final TextView user_status1 = (TextView)mView.findViewById(R.id.postmessagecenter);
            //final TextView sclasst = (TextView)mView.findViewById(R.id.sclass);
            final ImageView user_image = (ImageView) mView.findViewById(R.id.post_image);
            final ImageView userPhoto = (ImageView)mView.findViewById(R.id.ivEllipses);

            if(image==null){
                user_status1.setText(message);
                user_status.setVisibility(View.GONE);
            }
            else {
                user_status.setText(message);
                user_status1.setVisibility(View.GONE);
            }

            //user_name.setText(from);

            Glide.with(ctx).load(image).into(user_image);
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(from);
            Log.d(TAG, "setDetails: "+myRef);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Users user = dataSnapshot.getValue(Users.class);
                    Log.d(TAG, "onDataChange: the data snapshot is --------"+dataSnapshot);
                   Log.d(TAG, "onDataChange: "+user.getImage());
                    Log.d(TAG, "onDataChange: "+user.getName());

                    user_name.setText(user.getName());
                    SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    sfd.format(new Date(time));
                    Log.d(TAG, "onDataChange: sdf"+sfd);

                    UrlImageViewHelper.setUrlDrawable(userPhoto,user.getImage().toString() );


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });

        }

    }

}
