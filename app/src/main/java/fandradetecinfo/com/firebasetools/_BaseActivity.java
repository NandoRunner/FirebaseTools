package fandradetecinfo.com.firebasetools;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by Fernando on 04/06/2018.
 */

public class _BaseActivity extends AppCompatActivity {

    protected FirebaseFirestore firestore;

    public static final String TAG = "usuario";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firestore = FirebaseFirestore.getInstance();
    }
}
