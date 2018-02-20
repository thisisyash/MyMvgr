package shaavy.mycollege.mvgr.mymvgr;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import me.grantland.widget.AutofitHelper;
import shaavy.mycollege.mvgr.mymvgr.configure.Stream;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ViewNotice extends AppCompatActivity {
    private static final String TAG = "ViewNotice";
    private final Context mContext = ViewNotice.this;
    private TextView noticeMessage,username,noticedown;
    private ImageView noticeImage;
    private CircleImageView profile_photo,back_arrow;
    private String user_name="",imgUrl="";

    PhotoViewAttacher pAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notice);

        noticeImage = (ImageView)findViewById(R.id.notice_image);
        noticeMessage = (TextView)findViewById(R.id.notice);
        noticedown = (TextView)findViewById(R.id.noticedown);
        profile_photo = (CircleImageView)findViewById(R.id.profile_photo);
        username = (TextView)findViewById(R.id.username);
        back_arrow = (CircleImageView)findViewById(R.id.back_arrow);
        pAttacher = new PhotoViewAttacher(noticeImage);
        pAttacher.update();

        AutofitHelper.create(noticeMessage);
        AutofitHelper.create(noticedown);
        Intent intent = getIntent();
        String message = intent.getStringExtra("message");
        String imageString = intent.getStringExtra("image");
        String from = intent.getStringExtra("from");


        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,AnnouncementActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(from);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);

                Log.d(TAG, "onDataChange: "+user.getImage());
                Log.d(TAG, "onDataChange: "+user.getName());
                user_name = user.getName();
                imgUrl = user.getImage();
                username.setText(user_name);

                UrlImageViewHelper.setUrlDrawable(profile_photo,imgUrl );

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        if(imageString==null){

            noticeMessage.setText(message);
            noticedown.setVisibility(View.GONE);
            noticeImage.setVisibility(View.GONE);
        }
        else {

            noticeMessage.setVisibility(View.GONE);
            noticedown.setText(message);
            AutofitHelper.create(noticedown);
            UrlImageViewHelper.setUrlDrawable(noticeImage,imageString);

        }
        Log.d(TAG, "onCreate: the data obtained is "+message+imageString

        );


    }
}
