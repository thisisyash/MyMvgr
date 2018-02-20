package shaavy.mycollege.mvgr.mymvgr;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;


import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends android.support.v4.app.Fragment {
    private static final String TAG = "ProfileFragment";
    private Button mLogoutBtn;

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;

    private CircleImageView mProfileImage;
    private TextView mProfileName,mStream,mDeparment,mSection,mYear,mMobile;

    //private FirebaseFirestore mFirestore;
    private String mUserId;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        mAuth = FirebaseAuth.getInstance();
        mUserId = mAuth.getCurrentUser().getUid();

        Log.d(TAG, "onCreateView: the user id is"+mUserId);

        mLogoutBtn = (Button) view.findViewById(R.id.logout_btn);
        mProfileImage = (CircleImageView) view.findViewById(R.id.profile_image);
        mStream = (TextView) view.findViewById(R.id.stream);
        mDeparment = (TextView) view.findViewById(R.id.department);
        mSection = (TextView) view.findViewById(R.id.section);
        mYear = (TextView) view.findViewById(R.id.year);
        mMobile = (TextView) view.findViewById(R.id.mobile);
        mProfileName = (TextView)view.findViewById(R.id.profile_name);

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().child("Users").child(mUserId);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);

                Log.d(TAG, "onDataChange: "+user.getImage());
                Log.d(TAG, "onDataChange: "+user.getName());


                String type = user.getType().toString();
                if(type.equals("student")){

                    String some = user.getSclass().toString();
                    mProfileName.setText("Name : "+user.getName().toString());
                    mStream.setText("Roll no : "+user.getCid());
                    mDeparment.setText("Department : "+getDepartment(some));
                    mSection.setText("Section : "+getClassd(some));
                    mYear.setText("Year : "+getYear(some));
                    mMobile.setText("Mobile : "+user.getMobile());

                    UrlImageViewHelper.setUrlDrawable(mProfileImage,user.getImage().toString() );


                }
                else if(type.equals("faculty")){
                    mProfileName.setText("Name : "+user.getName().toString());
                    mStream.setText("Roll no : "+user.getCid());
                    mMobile.setText("Mobile : "+user.getMobile());
                    mDeparment.setVisibility(View.GONE);
                    mSection.setVisibility(View.GONE);
                    mYear.setVisibility(View.GONE);
                    UrlImageViewHelper.setUrlDrawable(mProfileImage,user.getImage().toString() );


                }






            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });








        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> tokenMapRemove = new HashMap<>();
                //tokenMapRemove.put("token_id", FieldValue.delete());
                mAuth.signOut();
                Intent loginIntent = new Intent(container.getContext(), HomeActivity.class);
                startActivity(loginIntent);

            }
        });

        return view;

    }

    public static String getStream(String temp){
        String temp1="";
        if(temp.charAt(0)=='b' && temp.charAt(1)=='t'){
            temp1 = "B.tech ";

        }
        else if(temp.charAt(0)=='m' && temp.charAt(1)=='t'){
            temp1 = "M.tech ";

        }

        else if(temp.charAt(0)=='m' && temp.charAt(1)=='b'){
            temp1 = "M.B.A ";

        }
        Log.d(TAG, "getStream: "+temp1);
        return temp1;
    }

    public static String getDepartment(String temp){
        String temp1="";
        if(temp.charAt(2)=='c'&&temp.charAt(3)=='s'){

            temp1 = " C.S.E ";
        }

        else if(temp.charAt(2)=='e'&&temp.charAt(3)=='c'){
            temp1 = " E.C.E ";
        }

        else if(temp.charAt(2)=='e'&&temp.charAt(3)=='e'){
            temp1 = " E.E.E ";
        }

        else if(temp.charAt(2)=='m'&&temp.charAt(3)=='e'){
            temp1 = " M.E.C.H ";
        }

        else if(temp.charAt(2)=='c'&&temp.charAt(3)=='i'){
            temp1 = " CIVIL ";
        }

        else if(temp.charAt(2)=='i'&&temp.charAt(3)=='t'){
            temp1 = " I.T ";
        }

        else if(temp.charAt(2)=='c'&&temp.charAt(3)=='h'){
            temp1 = " CHEM ";
        }

        Log.d(TAG, "getDepartment: "+temp1);
        return temp1;
    }

    public static String getYear(String temp){
        String temp1="";
        if(temp.charAt(4)=='1'){
            temp1 = " I ";
        }

        else if(temp.charAt(4)=='2'){
            temp1 = " II ";
        }

        else if(temp.charAt(4)=='3'){
            temp1 = " III ";
        }

        else if(temp.charAt(4)=='4'){
            temp1 = " IV ";
        }

        Log.d(TAG, "getYear: "+temp1);
        return temp1;
    }


    public static String getClassd(String temp){
        String temp1="";
        if(temp.charAt(5)=='a'){
            temp1 = " A";
        }

        else if(temp.charAt(5)=='b'){
            temp1 = " B";
        }

        else if(temp.charAt(5)=='c'){
            temp1 = " C";
        }

        else if(temp.charAt(5)=='d'){
            temp1 = " D";
        }

        Log.d(TAG, "getClassd: "+temp1);
        return temp1;
    }


}
