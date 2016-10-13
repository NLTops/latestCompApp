package tjanssen.companionapp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class EntryActivity extends AppCompatActivity implements TokenFragment.OnFragmentInteractionListener{

    TokenFragment tokenFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        tokenFrag = new TokenFragment();
    }

    public void onButtonClick(View v){
        Button btn = (Button) v.findViewById(R.id.button);
        btn.setEnabled(false);
        btn.setVisibility(View.GONE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_entry, tokenFrag);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(String token) {
        Intent intent = new Intent(this, MainActivity.class);
        Bundle b = new Bundle();
        b.putString("Token", token);
        intent.putExtras(b);
        startActivity(intent);
    }
}
