package shaavy.mycollege.mvgr.mymvgr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import me.grantland.widget.AutofitHelper;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    private Context mContext = SearchActivity.this;




    private DatabaseReference firebaseDatabase;


    private EditText mSearchField;
    private Button mSearchButton;
    public static String temp1,temp2;
    private RecyclerView mResultList;
    private TextView register_heading;

    private DatabaseReference mUserDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        firebaseDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mSearchButton = (Button)findViewById(R.id.search_btn);
        mSearchField = (EditText)findViewById(R.id.search_field);
        mResultList = (RecyclerView)findViewById(R.id.result_list);
        register_heading = (TextView)findViewById(R.id.register_heading);
        AutofitHelper.create(register_heading);
        Intent int1 = getIntent();
        String inString = int1.getStringExtra("outString");
        Log.d(TAG, "onCreate: the string from getted is --"+inString);

        if(inString.equals("f")){
            Log.d(TAG, "onCreate: the string is inside the f"+inString);
           temp1 = "feedback";
        }
        else{
            Log.d(TAG, "onCreate: the string is outsied "+inString);
           temp1 = "notice";
        }
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = mSearchField.getText().toString();
                if(searchText.equals("pikaachu")){
                    Intent intent = new Intent(mContext,TeamMvgr.class);
                    startActivity(intent);
                    finish();
                }
                firebaseSearch(searchText);

            }
        });
    }

    private void firebaseSearch(String text) {
        Query fireSearch = firebaseDatabase.orderByChild("name").startAt(text).endAt(text + "\uf8ff");
        FirebaseRecyclerAdapter<Users,UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(
                Users.class,
                R.layout.list_layout,
                UsersViewHolder.class,
                fireSearch

        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, final Users model, final int position) {

                viewHolder.setDetails(model.getName(),model.getCid(),model.getSclass(),model.getImage(),mContext);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "onClick: you clicke this"+position+"-"+model.getName());

                        Intent intent = new Intent(SearchActivity.this,SendActivity.class);
                        intent.putExtra("task",temp1);
                        intent.putExtra("user_name",model.getName());
                        intent.putExtra("status",model.getCid());
                        intent.putExtra("token_id",model.getToken_id());
                        intent.putExtra("user_id",model.getUid());
                        intent.putExtra("outString",temp1);

                        startActivity(intent);
                        finish();
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

        public void setDetails(String name,String cid,String sclass,String userImage,Context ctx){
            this.ctx = ctx;
            TextView user_name = (TextView)mView.findViewById(R.id.name_text);
            TextView user_status = (TextView)mView.findViewById(R.id.status_text);
            TextView sclasst = (TextView)mView.findViewById(R.id.sclass);
            ImageView user_image = (ImageView) mView.findViewById(R.id.profile_image);

            user_name.setText(name);
            user_status.setText(cid);
            sclass = getStream(sclass)+getDepartment(sclass)+getYear(sclass)+getClassd(sclass);
            Log.d(TAG, "setDetails: "+sclass);
            sclasst.setText(sclass);
            Glide.with(ctx).load(userImage).into(user_image);


        }

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

            temp1 = "- C.S.E ";
        }

        else if(temp.charAt(2)=='e'&&temp.charAt(3)=='c'){
            temp1 = "- E.C.E ";
        }

        else if(temp.charAt(2)=='e'&&temp.charAt(3)=='e'){
            temp1 = "- E.E.E ";
        }

        else if(temp.charAt(2)=='m'&&temp.charAt(3)=='e'){
            temp1 = "- M.E.C.H ";
        }

        else if(temp.charAt(2)=='c'&&temp.charAt(3)=='i'){
            temp1 = "- CIVIL ";
        }

        else if(temp.charAt(2)=='i'&&temp.charAt(3)=='t'){
            temp1 = "- I.T ";
        }

        else if(temp.charAt(2)=='c'&&temp.charAt(3)=='h'){
            temp1 = "- CHEM ";
        }

        Log.d(TAG, "getDepartment: "+temp1);
        return temp1;
    }

    public static String getYear(String temp){
        String temp1="";
        if(temp.charAt(4)=='1'){
            temp1 = "- I ";
        }

        else if(temp.charAt(4)=='2'){
            temp1 = "- II ";
        }

        else if(temp.charAt(4)=='3'){
            temp1 = "- III ";
        }

        else if(temp.charAt(4)=='4'){
            temp1 = "- IV ";
        }

        Log.d(TAG, "getYear: "+temp1);
        return temp1;
    }


    public static String getClassd(String temp){
        String temp1="";
        if(temp.charAt(5)=='a'){
            temp1 = "- A";
        }

        else if(temp.charAt(5)=='b'){
            temp1 = "- B";
        }

        else if(temp.charAt(5)=='c'){
            temp1 = "- C";
        }

        else if(temp.charAt(5)=='d'){
            temp1 = "- D";
        }

        Log.d(TAG, "getClassd: "+temp1);
        return temp1;
    }


}
