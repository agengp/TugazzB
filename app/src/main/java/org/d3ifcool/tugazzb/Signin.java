package org.d3ifcool.tugazzb;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * Created by prase on 28/10/2017.
 */

public class Signin extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private LinearLayout Section;
    private Button SignOut;
    private SignInButton SignIn;
    private TextView Name,Email;
    private ImageView Pic;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        Section = (LinearLayout)findViewById(R.id.section);
        SignOut = (Button) findViewById(R.id.logout);
        SignIn = (SignInButton)findViewById(R.id.login);
        Name = (TextView)findViewById(R.id.name);
        Email = (TextView)findViewById(R.id.email);
        Pic = (ImageView)findViewById(R.id.pic);
        SignIn.setOnClickListener(this);
        SignOut.setOnClickListener(this);
        Section.setVisibility(View.GONE);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,null)
                .addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions)
                .build();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                signIn();
                break;
            case R.id.logout:
                signOut();
                break;

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn(){
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);

    }

    private void signOut(){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });

    }
    private void handleResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            String img_url = account.getPhotoUrl().toString();
            Toast.makeText(this, account.getDisplayName(), Toast.LENGTH_SHORT).show();
            Name.setText(name);
            Email.setText(email);
            Glide.with(this).load(img_url).into(Pic);
            updateUI(true);
        }else{
            updateUI(false);
        }
    }
    private void updateUI(boolean isLogin){
        if(isLogin){
            Section.setVisibility(View.VISIBLE);
            SignIn.setVisibility(View.GONE);
        }else {
            Section.setVisibility(View.GONE);
            SignIn.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
            startActivity(new Intent(this, AlarmActivity.class));
        }
    }
}
