package shaavy.mycollege.mvgr.mymvgr;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import shaavy.mycollege.mvgr.mymvgr.Utils.SquareImageView;

public class SplashScreen extends AppCompatActivity {
    private static final String TAG = "SplashScreen";
    private Context mContext = SplashScreen.this;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef1;
    private StorageReference mStorage;
    private FirebaseAuth mAuth;
    private Button btn1,btn2;
    private CircleImageView img;
    private TextView tv1,loading;
    private ProgressBar pv1;

    private String tokensrv = "";
    private String uidsrv = "";
    private String imgsrv="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef1 = firebaseDatabase.getReference();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("splash");
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        tv1 = (TextView)findViewById(R.id.disptext);
        loading = (TextView)findViewById(R.id.loading);
        img = (CircleImageView)findViewById(R.id.profile_image);
        pv1 = (ProgressBar)findViewById(R.id.statusbar);

        final Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(10000);
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                catch (InterruptedException e){
                    Log.d(TAG, "run: some thing went wrong");
                }
            }
        };
        myThread.start();



     
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d(TAG, "onDataChange: the data i got is "+dataSnapshot.getValue());
                String type = dataSnapshot.child("type").getValue().toString();

                if(type.equals("20")){
                    Log.d(TAG, "onDataChange: this is for shravya bday ");
                    btn1.setVisibility(View.VISIBLE);
                    btn2.setVisibility(View.VISIBLE);
                    String btntext1 = "Send her Wishes";
                    String btntext2 = "It's OK";

                    tokensrv = dataSnapshot.child("btntext1").getValue().toString();
                    uidsrv = dataSnapshot.child("btntext2").getValue().toString();
                    imgsrv = dataSnapshot.child("imgurl").getValue().toString();

                    String imgurl = dataSnapshot.child("imgurl").getValue().toString();
                    UrlImageViewHelper.setUrlDrawable(img,imgurl);
                    tv1.setText("One of our Mvgrite, SRAVYA is having her birthday today, Let us take a moment to wish our collegemates to be happy.");
                    btn1.setText(btntext1);
                    btn2.setText(btntext2);
                    loading.setVisibility(View.GONE);
                    pv1.setVisibility(View.GONE);


                    btn1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            myThread.interrupt();
                            Log.d(TAG, "onClick: button 1 clicked");

                            //sending the notice part
                            String message = "Many more Happy returns of the day, Shravya. onbehalf of Team MyMvgr.";





                            String mCurrentId = mAuth.getCurrentUser().getUid();

                            Map<String, Object> notice = new HashMap<>();
                            notice.put("message", message);
                            notice.put("from", mCurrentId);
                            notice.put("time", ServerValue.TIMESTAMP);
                            myRef1.child("Users").child(uidsrv).child("notices").push().setValue(notice);


                            //sending the notification part
                            //Log.d(TAG, "onClick: the token id i got is ---------"+token_id);
                            Map<String, Object> notif = new HashMap<>();
                            notif.put("title1", "Happy Birthday Sravya");
                            notif.put("body1", "You have a new Notice");
                            notif.put("token_id", tokensrv);
                            myRef1.child("Users").child(uidsrv).child("Notifications").push().setValue(notif);
                            Log.d(TAG, "onClick: send the notid");


                            Toast.makeText(mContext, "Wishes Sent !!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    });

                    btn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            myThread.interrupt();
                            Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    });


                }

                else if(type.equals("0")){

                    Log.d(TAG, "onDataChange: no action");
                    btn2.setVisibility(View.VISIBLE);
                    String btntext1 = dataSnapshot.child("btntext1").getValue().toString();
                    String imgurl = dataSnapshot.child("imgurl").getValue().toString();
                    UrlImageViewHelper.setUrlDrawable(img,imgurl);


                    String message = dataSnapshot.child("message").getValue().toString();
                    Log.d(TAG, "onDataChange: the -----"+btntext1+"--"+"--"+message);

                    tv1.setText(message);

                    btn2.setText(btntext1);
                    loading.setVisibility(View.GONE);
                    pv1.setVisibility(View.GONE);

                    btn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            myThread.interrupt();
                            Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    });

                }
                else if(type.equals("1")){
                    Log.d(TAG, "onDataChange: move to web");
                    btn1.setVisibility(View.VISIBLE);
                    btn2.setVisibility(View.VISIBLE);
                    String btntext1 = dataSnapshot.child("btntext1").getValue().toString();
                    String btntext2 = dataSnapshot.child("btntext2").getValue().toString();
                    String message = dataSnapshot.child("message").getValue().toString();
                    Log.d(TAG, "onDataChange: the -----"+btntext1+"--"+btntext2+"--"+message);
                    final String btnurl = dataSnapshot.child("btnurl").getValue().toString();
                    String imgurl = dataSnapshot.child("imgurl").getValue().toString();
                    UrlImageViewHelper.setUrlDrawable(img,imgurl);
                    tv1.setText(message);
                    btn1.setText(btntext1);
                    btn2.setText(btntext2);

                    loading.setVisibility(View.GONE);
                    pv1.setVisibility(View.GONE);


                    btn1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            myThread.interrupt();
                            Log.d(TAG, "onClick: button 1 clicked");
                            Intent intent = new Intent(mContext,AboutActivity.class);
                            intent.putExtra("btnurl",btnurl);
                            startActivity(intent);
                            finish();

                        }
                    });

                    btn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            myThread.interrupt();
                            Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        }
                    });



                }

                Log.d(TAG, "onDataChange: the type string is "+type);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
