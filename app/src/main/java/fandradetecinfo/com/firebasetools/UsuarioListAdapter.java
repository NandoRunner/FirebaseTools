package fandradetecinfo.com.firebasetools;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fandradetecinfo.com.firebasetools.Models.Usuario;

public class UsuarioListAdapter extends RecyclerView.Adapter<UsuarioListAdapter.ViewHolder> {

    public List<Usuario> pessoasList;

    public UsuarioListAdapter(List<Usuario> pessoasList) {
        this.pessoasList = pessoasList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nomeText.setText(pessoasList.get(position).getNome());
        holder.uidText.setText(pessoasList.get(position).getUid());
		holder.docidText.setText(pessoasList.get(position).getDoc_id());
    }

    @Override
    public int getItemCount() {
        return pessoasList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        View mView;

        public TextView nomeText;
        public TextView uidText;
		public TextView docidText;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mView = itemView;

            nomeText = (TextView)mView.findViewById(R.id.txtNome);
            uidText = (TextView)mView.findViewById(R.id.txtIdade);
			docidText = (TextView)mView.findViewById(R.id.txtDocid);
        }

    }
}
