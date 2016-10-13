package tjanssen.companionapp;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentTransaction;
import android.widget.Spinner;
import android.widget.TabHost;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TokenFragment.OnFragmentInteractionListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    boolean fragmentInteractionComplete = false;

    TokenFragment tokenFrag;
    ScheduleFragment scheduleFrag;
    GradesFragment gradesFrag;

    String token;

    ArrayList<String> weeks;
    ArrayList<String> types;
    ArrayList<String> classes;
    ArrayList<String> subjects;
    ArrayList<String> teachers;

    ArrayList<ScheduleItem> schedule;
    ArrayList<GradeItem> grades;

    TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scheduleFrag = new ScheduleFragment();
        gradesFrag = new GradesFragment();
        tokenFrag = new TokenFragment();

        weeks = new ArrayList<>();
        classes = new ArrayList<>();
        subjects = new ArrayList<>();
        teachers = new ArrayList<>();

        schedule = new ArrayList<>();
        grades = new ArrayList<>();

        types = new ArrayList<>();
        types.add("Class");
        types.add("Subject");
        types.add("Teacher");

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_main, tokenFrag);
        fragmentTransaction.commit();


    }

    @Override
    public void onFragmentInteraction(String token) {
        this.token = token;

        new JSONinitialTask(this).execute(token, "spinnerWeek");
        new JSONinitialTask(this).execute(token, "spinnerElementClass");
        new JSONinitialTask(this).execute(token, "spinnerElementTeacher");
        new JSONinitialTask(this).execute(token, "spinnerElementSubject");

        new JSONgradeTask(this).execute(token);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ScheduleFragment(), "Schedule");
        adapter.addFragment(new GradesFragment(), "Grades");
        viewPager.setAdapter(adapter);
    }
}
