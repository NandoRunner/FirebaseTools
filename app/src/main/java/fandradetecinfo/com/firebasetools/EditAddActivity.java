package fandradetecinfo.com.firebasetools;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;
public class EditAddActivity extends _BaseActivity {

    private DocumentReference docRef;

    EditText ed1;
    EditText ed2;

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        tv = (TextView) findViewById(R.id.txtbusca);

        docRef = FirebaseFirestore.getInstance().document("sampleData/pessoas");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists())
                {
                    String nome = documentSnapshot.getString(NOME_KEY);
                    String idade = documentSnapshot.getString(IDADE_KEY);

                    tv.setText(nome + " tem " + idade + " anos!");
                }
                else if (e != null)
                {
                    Log.w(TAG, "Exception", e);
                }
            }
        });
    }

    public void Salvar(View vw)
    {


        ed1 = (EditText) findViewById(R.id.edtNome2);
        ed2 = (EditText) findViewById(R.id.edt);

        String nome = ed1.getText().toString();
        String idade = ed2.getText().toString();

        if (nome.isEmpty() || idade.isEmpty()) return;

        Map<String, Object> dataToSave = new HashMap<String, Object>();
        dataToSave.put("nome", model.getAltura());
        dataToSave.put("data_nascimento", model.getDataNascimento());
        dataToSave.put("nome", model.getNome());
        dataToSave.put("num_registros", model.getNum_registros());
        dataToSave.put("peso_medio", model.getPeso_medio());
        dataToSave.put("sexo", model.getSexo());


        Map<String, Object> dataToSave = new HashMap<String, Object>();
        dataToSave.put(NOME_KEY, nome);
        dataToSave.put(IDADE_KEY, idade);

        firestore.collection(TAG).add(dataToSave).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "documento salvo");
                ed1.setText("");
                ed2.setText("");
            }
        });

        // Documento específico, serve para configuração
        docRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("coleção - pessoas", "documento salvo");
                ed1.setText("");
                ed2.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Falha no salvamento do documento");
            }
        });


    }
}
