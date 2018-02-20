package shaavy.mycollege.mvgr.mymvgr;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private static final int PICK_IMAGE = 1;
    private CircleImageView mImageBtn;

    private EditText mNameField;

    private Button mRegBtn;


    private Uri imageUri;
    private String inString,cid,type,mobile;

    private StorageReference mStorage;
    private FirebaseAuth mAuth;


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;


    private ProgressBar mRegisterProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imageUri = null;

        mStorage = FirebaseStorage.getInstance().getReference().child("images");
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

        mImageBtn = (CircleImageView) findViewById(R.id.register_image_btn);
        mNameField = (EditText) findViewById(R.id.register_name);
        mRegBtn = (Button) findViewById(R.id.register_btn);

        mRegisterProgressBar = (ProgressBar) findViewById(R.id.registerProgressBar);

        if(getIntent() != null)
        {
            inString = getIntent().getStringExtra("outString");
            cid = getIntent().getStringExtra("cid");
            type = getIntent().getStringExtra("type");
            mobile = getIntent().getStringExtra("mobile");


        }


        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (imageUri != null) {

                    mRegisterProgressBar.setVisibility(View.VISIBLE);

                    final String name = mNameField.getText().toString();


                    if (!TextUtils.isEmpty(name)) {

                        final String user_id = mAuth.getCurrentUser().getUid();

                        StorageReference user_profile = mStorage.child(user_id + ".jpg");

                        user_profile.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> uploadTask) {

                                if (uploadTask.isSuccessful()) {

                                    final String download_url = uploadTask.getResult().getDownloadUrl().toString();

                                    String token_id = FirebaseInstanceId.getInstance().getToken();

                                    Map<String, Object> userMap = new HashMap<>();
                                    userMap.put("uid",user_id);
                                    userMap.put("name", name);
                                    userMap.put("cid", cid);
                                    userMap.put("sclass", inString);
                                    userMap.put("image", download_url);
                                    userMap.put("token_id", token_id);
                                    userMap.put("type",type);
                                    userMap.put("mobile",mobile);
                                    myRef.child("Users").child(user_id).setValue(userMap);
                                    Log.d(TAG, "onComplete: the final hashmap is "+userMap);
                                    Intent intent1 = new Intent(RegisterActivity.this,MainActivity.class);
                                    startActivity(intent1);
                                    finish();


                                } else {

                                    Toast.makeText(RegisterActivity.this, "Error : " + uploadTask.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    mRegisterProgressBar.setVisibility(View.INVISIBLE);

                                }

                            }
                        });


                    } else {

                        Toast.makeText(RegisterActivity.this, "Error : " + "error some where", Toast.LENGTH_SHORT).show();
                        mRegisterProgressBar.setVisibility(View.INVISIBLE);

                    }

                }
                else {
                    Toast.makeText(RegisterActivity.this, "Please choose a profile picture", Toast.LENGTH_SHORT).show();
                }
            }
        });







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

    private void sendToMain() {

        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE){

            imageUri = data.getData();

            Picasso.with(RegisterActivity.this).load(imageUri).fit().centerCrop().into(mImageBtn);

        }

    }
}
