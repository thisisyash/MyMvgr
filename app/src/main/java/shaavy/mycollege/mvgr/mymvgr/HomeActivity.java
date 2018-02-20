package shaavy.mycollege.mvgr.mymvgr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    private Button student,faculty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        student = (Button)findViewById(R.id.student);
        faculty = (Button)findViewById(R.id.faculty);
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(HomeActivity.this, PhoneAuthActivity.class);
                loginIntent.putExtra("type","student");
                startActivity(loginIntent);
                finish();

            }
        });

        faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(HomeActivity.this, PhoneAuthActivity.class);
                loginIntent.putExtra("type","faculty");
                startActivity(loginIntent);
                finish();

            }
        });
    }
}
