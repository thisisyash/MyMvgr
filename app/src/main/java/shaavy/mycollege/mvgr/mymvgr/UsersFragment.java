package shaavy.mycollege.mvgr.mymvgr;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;


import java.util.List;

import shaavy.mycollege.mvgr.mymvgr.configure.Stream;
import shaavy.mycollege.mvgr.mymvgr.models.Assignment;
import shaavy.mycollege.mvgr.mymvgr.models.Feedback;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends android.support.v4.app.Fragment {

    private RecyclerView mUsersListView;

    private List<Users> usersList;
    private UsersRecyclerAdapter usersRecyclerAdapter;
    GridLayout mainGrid;
    //private FirebaseFirestore mFirestore;

    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        //mFirestore = FirebaseFirestore.getInstance();

       // mUsersListView = (RecyclerView) view.findViewById(R.id.users_list_view);



        mainGrid = (GridLayout)view.findViewById(R.id.mainGrid);

        //Set Event
        setSingleEvent(mainGrid);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();



//        mFirestore.collection("Users").addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//
//                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
//
//                    if (doc.getType() == DocumentChange.Type.ADDED) {
//
//                        String user_id = doc.getDocument().getId();
//
//                        Users users = doc.getDocument().toObject(Users.class).withId(user_id);
//                        usersList.add(users);
//
//                        usersRecyclerAdapter.notifyDataSetChanged();
//
//                    }
//
//                }
//
//            }
//        });

    }


    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if(finalI==0){

                        Intent intent = new Intent(getContext(),AnnouncementActivity.class);
                        intent.putExtra("outString","a");
                        startActivity(intent);

                    }

                    else if(finalI==1){

                        Intent intent = new Intent(getContext(),NoticeActivity.class);
                        intent.putExtra("outString","n");
                        startActivity(intent);
                    }

                    else if(finalI==2){

                        Intent intent = new Intent(getContext(), FeedbackActivity.class);
                        intent.putExtra("outString","f");
                        startActivity(intent);
                    }

                    else if(finalI==3){

                        Intent intent = new Intent(getContext(), AssignmentActivity.class);
                        intent.putExtra("outString","s");
                        startActivity(intent);
                    }

                    else if(finalI==4){

                        Intent intent = new Intent(getContext(), AttendanceActivity.class);
                        intent.putExtra("outString","d");
                        startActivity(intent);
                    }
                    else if(finalI==5){

                        Intent intent = new Intent(getContext(), ResultActivity.class);
                        intent.putExtra("outString","k");
                        startActivity(intent);
                    }
                    else if(finalI==6){

                        Intent intent = new Intent(getContext(), ErrorActivity.class);
                        intent.putExtra("outString","r");
                        startActivity(intent);
                    }
                    else if(finalI==7){

                        Intent intent = new Intent(getContext(), AboutActivity.class);
                        startActivity(intent);

                    }


                }
            });
        }
    }
}
