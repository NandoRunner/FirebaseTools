package fandradetecinfo.com.firebasetools;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText nome, email, senha;
    private TextView msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        nome = (EditText) findViewById(R.id.edtNome2);
        email = (EditText) findViewById(R.id.edtEmail2);
        senha = (EditText) findViewById(R.id.edtSenha);

        msg = (TextView) findViewById(R.id.txtMsg);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                {
                    Log.d("LogX", "usuário logado:" + user.getUid());
                    msg.setText("usuário logado:" + user.getUid());

                }
                else
                {
                    Log.d("LogX", "Sem usuário logado");
                    msg.setText("Sem usuário logado");
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null)
        {
            mAuth.removeAuthStateListener(mAuthListener);
            mAuth.signOut();
        }
    }

    public void ClicarLogar(View v)
    {
        mAuth.signInWithEmailAndPassword(email.getText().toString(), senha.getText().toString()).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.d("LogX", "Falha na autenticação");
                            msg.setText(task.getException().getMessage().toString());
                        }
                        else
                        {
                            Log.d("LogX", "Login efetuado!");
                            msg.setText("Login efetuado!");
                        }
                    }
                });
    }

    public void ClicarListar(View v)
    {
        Intent i = new Intent(getApplicationContext(),ListActivity.class);
        startActivity(i);
    }

    public void ClicarRegistrar(View v)
    {
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), senha.getText().toString()).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Log.d("LogX", "Falha na criação da conta");
                    msg.setText(task.getException().getMessage().toString());
                }
                else
                {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    Map<String, Object> user = new HashMap<>();
                    user.put("nome", nome.getText().toString());
                    user.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());

                    db.collection("usuario")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("LogX", "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("LogX", "Error adding document", e);
                                }
                            });

                    Log.d("LogX", "Conta criada");
                    msg.setText("Conta criada!");
                }
            }
        });
    }


}
