package fandradetecinfo.com.firebasetools;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fandradetecinfo.com.firebasetools.Models.Usuario;


public class ListActivity extends _BaseActivity {

    private RecyclerView recyclerView;

    private List<Usuario> usuarioList;
    private UsuarioListAdapter usuarioListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                tratarAdicionar();
            }
        });

        usuarioList = new ArrayList<>();
        usuarioListAdapter = new UsuarioListAdapter(usuarioList);

        recyclerView = (RecyclerView) findViewById(R.id.main_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(usuarioListAdapter);

        firestore.collection(TAG)
                .orderBy("Nome")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                try {
                    if (e != null) {
                        Log.d("Firelog", "Exception", e);
                    }

                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            Usuario pessoa = doc.getDocument().toObject(Usuario.class);
                            pessoa.setDoc_id(doc.getDocument().getId());
                            usuarioList.add(pessoa);
                            usuarioListAdapter.notifyDataSetChanged();
                        }
                        else if (doc.getType() == DocumentChange.Type.REMOVED) {

                            Usuario pessoaRemovida = doc.getDocument().toObject(Usuario.class);
                            pessoaRemovida.setDoc_id(doc.getDocument().getId());

                            Iterator<Usuario> itr = usuarioList.iterator();
                            while (itr.hasNext()) {
                                Usuario pessoa = itr.next();
                                if (pessoa.getDoc_id().equals(pessoaRemovida.getDoc_id())) {
                                    itr.remove();
                                }
                            }
                            usuarioListAdapter.notifyDataSetChanged();
                        }
                        else if (doc.getType() == DocumentChange.Type.MODIFIED) {

                            Usuario usuarioModificado = doc.getDocument().toObject(Usuario.class);
                            usuarioModificado.setDoc_id(doc.getDocument().getId());

                            for (int i = 0; i < usuarioList.size(); i++)
                            {
                                if (usuarioList.get(i).getDoc_id().equals(usuarioModificado.getDoc_id()))
                                {
                                    usuarioList.get(i).setNome(usuarioModificado.getNome());
                                    usuarioList.get(i).setDataNascimento(usuarioModificado.getDataNascimento());
                                }
                            }
                            usuarioListAdapter.notifyDataSetChanged();
                        }

                    }
                }catch (Exception ex)
                {
                    Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_LONG);
                }
            }
        });

    }
    private void tratarAdicionar()
    {
        Intent objIntent = new Intent(this, EditAddActivity.class);
        startActivity(objIntent);
    }
}
