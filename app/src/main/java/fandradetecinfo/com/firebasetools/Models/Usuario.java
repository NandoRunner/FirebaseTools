package fandradetecinfo.com.firebasetools.Models;

public class Usuario {

    private String Nome;
    private String Uid;

    private String Doc_id;
    private String Email;
    private String DataNascimento;


    public Usuario() {
    }

    public Usuario(String nome, String uid)
    {
        Nome = nome;
        Uid = uid;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getDoc_id() {
        return Doc_id;
    }

    public void setDoc_id(String doc_id) {
        Doc_id = doc_id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDataNascimento() {
        return DataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        DataNascimento = dataNascimento;
    }
}
