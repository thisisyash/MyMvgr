package shaavy.mycollege.mvgr.mymvgr;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SendActivity extends AppCompatActivity {
    private static final String TAG = "SendActivity";
    private Context mContext = SendActivity.this;
    private TextView user_id_view;
    private static final int PICK_IMAGE = 1;

    private String mUserId;
    private String mUserName;
    private String mCurrentId;

    private EditText mMessageView;
    private Button mSendBtn;
    private ProgressBar mMessageProgress;

    private ProgressBar statusbar;
    private TextView loading;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private StorageReference mStorage;
    private FirebaseAuth mAuth;
    //private FirebaseFirestore mFirestore;
    private static Uri imageUri;
    private String download_url,token_id,user_name_clicked;
    private CircleImageView mImageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        mAuth = FirebaseAuth.getInstance();
        imageUri = null;
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        mStorage = FirebaseStorage.getInstance().getReference().child("otherimages");
        mImageBtn = (CircleImageView) findViewById(R.id.register_image_btn);
        user_id_view = (TextView) findViewById(R.id.user_name_view);
        mMessageView = (EditText) findViewById(R.id.message_view);
        mSendBtn = (Button) findViewById(R.id.send_btn);
        mMessageProgress = (ProgressBar) findViewById(R.id.messageProgress);

        loading = (TextView)findViewById(R.id.loading);
        statusbar = (ProgressBar)findViewById(R.id.statusbar);

        mCurrentId = mAuth.getCurrentUser().getUid();
        Log.d(TAG, "onCreate: current user"+mCurrentId);

        //getting the details of previous activity

        String task = getIntent().getStringExtra("task");

        Log.d(TAG, "onCreate: the task obtained is"+task);

        token_id = getIntent().getStringExtra("token_id");
        user_name_clicked = getIntent().getStringExtra("user_name");
        mUserId = getIntent().getStringExtra("user_id");
        mUserName = getIntent().getStringExtra("user_name");




        final String inString = getIntent().getStringExtra("outString");
        Log.d(TAG, "onCreate: the obtained string is ----"+inString);
        
        
        if(task.equals("announcement")){
            Log.d(TAG, "onCreate: ---------i am sending announcement");
            mSendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loading.setVisibility(View.VISIBLE);
                    statusbar.setVisibility(View.VISIBLE);

                    Log.d(TAG, "onClick: the button clicked --- in announcement send");
                    String message = mMessageView.getText().toString();

                    Log.d(TAG, "onClick: the message i god is "+message);
                    if(!TextUtils.isEmpty(message)){

                        if (imageUri != null) {
                            String key1 = myRef.child(inString).child("notices").push().getKey();
                            StorageReference user_profile = mStorage.child(key1);
                            Log.d(TAG, "onClick: image id key "+key1);

                            user_profile.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> uploadTask) {

                                    if (uploadTask.isSuccessful()) {
                                        String message = mMessageView.getText().toString();
                                        download_url = uploadTask.getResult().getDownloadUrl().toString();
                                        Log.d(TAG, "onComplete: image upload succesfull"+download_url);
                                        mMessageProgress.setVisibility(View.VISIBLE);
                                        Log.d(TAG, "onClick: download url from outside ------"+download_url);
                                        Map<String, Object> notice = new HashMap<>();
                                        notice.put("message", message);
                                        notice.put("from", mCurrentId);
                                        notice.put("time", ServerValue.TIMESTAMP);
                                        notice.put("image",download_url);
                                        notice.put("sclass",inString);
                                        myRef.child(inString).child("announcements").push().setValue(notice);


                                        FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("sclass").equalTo(inString)
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                            Log.d(TAG, "onDataChange: inside the for loop-------");
                                                            Users user = snapshot.getValue(Users.class);
                                                            System.out.println(user);
                                                            Map<String, Object> notif = new HashMap<>();
                                                            notif.put("title1", "A new Announcement");
                                                            notif.put("body1", "From : MyMvgr");
                                                            notif.put("token_id", user.getToken_id());
                                                            myRef.child("Users").child(user.getUid()).child("Notifications").push().setValue(notif);
                                                            Log.d(TAG, "onClick: send the notid");

                                                        }
                                                    }
                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                    }
                                                });




                                        Toast.makeText(mContext, "Sent Succesfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(mContext,MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();

                                    } else {

                                        Toast.makeText(mContext, "Error : " + uploadTask.getException().getMessage(), Toast.LENGTH_LONG).show();

                                    }

                                }
                            });
                        }
                        else {

                            mMessageProgress.setVisibility(View.VISIBLE);
                            Log.d(TAG, "onClick: download url from outside ------"+download_url);
                            Map<String, Object> notice = new HashMap<>();
                            notice.put("message", message);
                            notice.put("from", mCurrentId);
                            notice.put("time", ServerValue.TIMESTAMP);
                            notice.put("sclass",inString);
                            myRef.child(inString).child("announcements").push().setValue(notice);

                            FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("sclass").equalTo(inString)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                Log.d(TAG, "onDataChange: inside the for loop-------");
                                                Users user = snapshot.getValue(Users.class);
                                                System.out.println(user);
                                                Map<String, Object> notif = new HashMap<>();
                                                notif.put("title1", "A new Announcement");
                                                notif.put("body1", "From : MyMvgr");
                                                notif.put("token_id", user.getToken_id());
                                                myRef.child("Users").child(user.getUid()).child("Notifications").push().setValue(notif);
                                                Log.d(TAG, "onClick: send the notid");

                                            }
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });



                            Toast.makeText(mContext, "Sent Succesfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }

                    }

                    else {

                        Toast.makeText(mContext, "Please fill the message", Toast.LENGTH_SHORT).show();
                    }
                    
                    
                }
            });
            
        }

        else if(task.equals("blog")){
            Log.d(TAG, "onCreate: ---------i am sending to blog ");
            mSendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loading.setVisibility(View.VISIBLE);
                    statusbar.setVisibility(View.VISIBLE);
                    Log.d(TAG, "onClick: the button clicked --- in blog send");
                    String message = mMessageView.getText().toString();

                    Log.d(TAG, "onClick: the message i god is "+message);
                    if(!TextUtils.isEmpty(message)){

                        if (imageUri != null) {
                            String key1 = myRef.child(inString).child("notices").push().getKey();
                            StorageReference user_profile = mStorage.child(key1);
                            Log.d(TAG, "onClick: image id key "+key1);

                            user_profile.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> uploadTask) {

                                    if (uploadTask.isSuccessful()) {
                                        String message = mMessageView.getText().toString();
                                        download_url = uploadTask.getResult().getDownloadUrl().toString();
                                        Log.d(TAG, "onComplete: image upload succesfull"+download_url);
                                        mMessageProgress.setVisibility(View.VISIBLE);
                                        Log.d(TAG, "onClick: download url from outside ------"+download_url);
                                        Map<String, Object> notice = new HashMap<>();
                                        notice.put("message", message);
                                        notice.put("from", mCurrentId);
                                        notice.put("time", ServerValue.TIMESTAMP);
                                        notice.put("image",download_url);
                                        notice.put("sclass",inString);
                                        myRef.child("blog").push().setValue(notice);
                                        Toast.makeText(mContext, "Sent Succesfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(mContext,MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();

                                    } else {

                                        Toast.makeText(mContext, "Error : " + uploadTask.getException().getMessage(), Toast.LENGTH_LONG).show();

                                    }

                                }
                            });
                        }
                        else {

                            mMessageProgress.setVisibility(View.VISIBLE);
                            Log.d(TAG, "onClick: download url from outside ------"+download_url);
                            Map<String, Object> notice = new HashMap<>();
                            notice.put("message", message);
                            notice.put("from", mCurrentId);
                            notice.put("time", ServerValue.TIMESTAMP);
                            notice.put("sclass",inString);
                            myRef.child("blog").push().setValue(notice);
                            Toast.makeText(mContext, "Sent Succesfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }

                    }

                    else {

                        Toast.makeText(mContext, "Please fill the message", Toast.LENGTH_SHORT).show();
                    }


                }
            });

        }


        else if(task.equals("assignment")){

            Log.d(TAG, "onCreate: ---------i am assignemnt");
            mSendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d(TAG, "onClick: the button clicked --- in assignment send");
                    String message = mMessageView.getText().toString();

                    Log.d(TAG, "onClick: the message i god is "+message);
                    if(!TextUtils.isEmpty(message)){

                        if (imageUri != null) {
                            loading.setVisibility(View.VISIBLE);
                            statusbar.setVisibility(View.VISIBLE);
                            String key1 = myRef.child(inString).child("assignments").push().getKey();
                            StorageReference user_profile = mStorage.child(key1);
                            Log.d(TAG, "onClick: image id key "+key1);

                            user_profile.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> uploadTask) {

                                    if (uploadTask.isSuccessful()) {
                                        String message = mMessageView.getText().toString();
                                        download_url = uploadTask.getResult().getDownloadUrl().toString();
                                        Log.d(TAG, "onComplete: image upload succesfull"+download_url);
                                        mMessageProgress.setVisibility(View.VISIBLE);
                                        Log.d(TAG, "onClick: download url from outside ------"+download_url);
                                        Map<String, Object> notice = new HashMap<>();
                                        notice.put("message", message);
                                        notice.put("from", mCurrentId);
                                        notice.put("time", ServerValue.TIMESTAMP);
                                        notice.put("image",download_url);
                                        notice.put("sclass",inString);
                                        myRef.child(inString).child("assignments").push().setValue(notice);

                                        FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("sclass").equalTo(inString)
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                            Log.d(TAG, "onDataChange: inside the for loop-------");
                                                            Users user = snapshot.getValue(Users.class);
                                                            System.out.println(user);
                                                            Map<String, Object> notif = new HashMap<>();
                                                            notif.put("title1", "A new Assignment ");
                                                            notif.put("body1", "From : MyMvgr");
                                                            notif.put("token_id", user.getToken_id());
                                                            myRef.child("Users").child(user.getUid()).child("Notifications").push().setValue(notif);
                                                            Log.d(TAG, "onClick: send the notid");

                                                        }
                                                    }
                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                    }
                                                });


                                        Toast.makeText(mContext, "Sent Succesfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(mContext,MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();

                                    } else {

                                        Toast.makeText(mContext, "Error : " + uploadTask.getException().getMessage(), Toast.LENGTH_LONG).show();

                                    }

                                }
                            });
                        }
                        else {

                            Toast.makeText(mContext, "Please upload the image", Toast.LENGTH_SHORT).show();
                        }

                    }

                    else {

                        Toast.makeText(mContext, "Please fill the message", Toast.LENGTH_SHORT).show();
                    }


                }
            });

        }

       
        else if(task.equals("notice")){

            Log.d(TAG, "onCreate: ------------"+"i am sending notice");
            mUserId = getIntent().getStringExtra("user_id");
            mUserName = getIntent().getStringExtra("user_name");

            user_id_view.setText("Send to " + mUserName);

            mSendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loading.setVisibility(View.VISIBLE);
                    statusbar.setVisibility(View.VISIBLE);

                    String message = mMessageView.getText().toString();
                    
                    if(!TextUtils.isEmpty(message)){

                        if (imageUri != null) {
                            String key1 = myRef.child("Users").child(mAuth.getCurrentUser().getUid()).push().getKey();
                            StorageReference user_profile = mStorage.child(key1);
                            Log.d(TAG, "onClick: image id key "+key1);

                            user_profile.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> uploadTask) {

                                    if (uploadTask.isSuccessful()) {
                                        String message = mMessageView.getText().toString();
                                        download_url = uploadTask.getResult().getDownloadUrl().toString();
                                        Log.d(TAG, "onComplete: image upload succesfull"+download_url);
                                        mMessageProgress.setVisibility(View.VISIBLE);
                                        Log.d(TAG, "onClick: download url from outside ------"+download_url);
                                        Map<String, Object> notice = new HashMap<>();
                                        notice.put("message", message);
                                        notice.put("from", mCurrentId);
                                        notice.put("time", ServerValue.TIMESTAMP);
                                        notice.put("image",download_url);
                                        myRef.child("Users").child(mUserId).child("notices").push().setValue(notice);


                                        //setting up the notification part
                                        //setting up the notification part
                                        Log.d(TAG, "onClick: the token id i got is ---------"+token_id);
                                        Map<String, Object> notif = new HashMap<>();
                                        notif.put("title1", "You have a new Notice");
                                        notif.put("body1", "From : MyMvgr");
                                        notif.put("token_id", token_id);
                                        myRef.child("Users").child(mUserId).child("Notifications").push().setValue(notif);
                                        Log.d(TAG, "onClick: send the notid");


                                        //sending back to main activity
                                        Toast.makeText(mContext, "Notice Sent Succesfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(mContext,MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();

                                    } else {

                                        Toast.makeText(mContext, "Error : " + uploadTask.getException().getMessage(), Toast.LENGTH_LONG).show();

                                    }

                                }
                            });
                        }
                        else {


                            mMessageProgress.setVisibility(View.VISIBLE);
                            Log.d(TAG, "onClick: download url from outside ------"+download_url);
                            Map<String, Object> notice = new HashMap<>();
                            notice.put("message", message);
                            notice.put("from", mCurrentId);
                            notice.put("time", ServerValue.TIMESTAMP);
                            myRef.child("Users").child(mUserId).child("notices").push().setValue(notice);

                            //setting up the notification part
                            Log.d(TAG, "onClick: the token id i got is ---------"+token_id);
                            Map<String, Object> notif = new HashMap<>();
                            notif.put("title1", "You have a new Notice");
                            notif.put("body1", "From : MyMvgr");
                            notif.put("token_id", token_id);
                            myRef.child("Users").child(mUserId).child("Notifications").push().setValue(notif);
                            Log.d(TAG, "onClick: send the notid");


                            Toast.makeText(mContext, "Sent Succesfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }

                    }

                    else {

                        Toast.makeText(mContext, "Please fill the message", Toast.LENGTH_SHORT).show();
                    }


                }
            });
            
        }
        
        else if(task.equals("feedback")){


            Log.d(TAG, "onCreate: sending the feedback");


            user_id_view.setText("Send to " + mUserName);

            mSendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loading.setVisibility(View.VISIBLE);
                    statusbar.setVisibility(View.VISIBLE);

                    String message = mMessageView.getText().toString();

                    if(!TextUtils.isEmpty(message)){

                        if (imageUri != null) {
                            String key1 = myRef.child("Users").child(mAuth.getCurrentUser().getUid()).push().getKey();
                            StorageReference user_profile = mStorage.child(key1);
                            Log.d(TAG, "onClick: image id key "+key1);

                            user_profile.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> uploadTask) {

                                    if (uploadTask.isSuccessful()) {
                                        String message = mMessageView.getText().toString();
                                        download_url = uploadTask.getResult().getDownloadUrl().toString();
                                        Log.d(TAG, "onComplete: image upload succesfull"+download_url);
                                        mMessageProgress.setVisibility(View.VISIBLE);
                                        Log.d(TAG, "onClick: download url from outside ------"+download_url);
                                        Map<String, Object> notice = new HashMap<>();
                                        notice.put("message", message);
                                        notice.put("from", mCurrentId);
                                        notice.put("time", ServerValue.TIMESTAMP);
                                        notice.put("image",download_url);
                                        myRef.child("Users").child(mUserId).child("feedback").push().setValue(notice);



                                        //----------------------sending the notification part
                                        Log.d(TAG, "onClick: the token id i got is ---------"+token_id);
                                        Map<String, Object> notif = new HashMap<>();
                                        notif.put("title1", "You have a new FeedBack");
                                        notif.put("body1", "MyMvgr");
                                        notif.put("token_id", token_id);
                                        myRef.child("Users").child(mUserId).child("Notifications").push().setValue(notif);
                                        Log.d(TAG, "onClick: send the notid");



                                        Toast.makeText(mContext, "Feedback Sent Succesfully", Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "onComplete: the feedback has been sent -----");
                                        Log.d(TAG, "onComplete: "+message+mCurrentId+download_url);
                                        Intent intent = new Intent(mContext,MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                 //       finish();

                                    } else {

                                        Toast.makeText(mContext, "Error : uploading image" + uploadTask.getException().getMessage(), Toast.LENGTH_LONG).show();

                                    }

                                }
                            });
                        }
                        else {

                            mMessageProgress.setVisibility(View.VISIBLE);
                            Log.d(TAG, "onClick: download url from outside ------"+download_url);
                            Map<String, Object> notice = new HashMap<>();
                            notice.put("message", message);
                            notice.put("from", mCurrentId);
                            notice.put("time", ServerValue.TIMESTAMP);
                            myRef.child("Users").child(mUserId).child("feedback").push().setValue(notice);

                            //----------------------sending the notification part
                            //to determine the token id
                            Log.d(TAG, "onClick: the token id i got is ---------"+token_id);
                            Map<String, Object> notif = new HashMap<>();
                            notif.put("title1", "You have a new FeedBack");
                            notif.put("body1", "MyMvgr");
                            notif.put("token_id", token_id);
                            myRef.child("Users").child(mUserId).child("Notifications").push().setValue(notif);
                            Log.d(TAG, "onClick: send the notid");

                            Toast.makeText(mContext, "Sent Succesfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            //finish();
                        }

                    }

                    else {

                        Toast.makeText(mContext, "Please fill the message", Toast.LENGTH_SHORT).show();
                    }


                }
            });

        }

        mImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

            }
        });

    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE){

            imageUri = data.getData();
            Picasso.with(mContext).load(imageUri).fit().centerCrop().into(mImageBtn);

        }

    }
    
}
