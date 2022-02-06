package br.ufc.quixada.dadm.entreg02;

public class Livro {

    private static int i = 0;

    private int id;
    private String nome;
    private String genero;

    public Livro(String nome, String genero){
        this.nome = nome;
        this.genero = genero;
        this.id = i++;
    }

    public Livro(){

    }

    public void atualizarContador(){
        i++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return " Id: " + id + " Nome: " + nome + " Gênero: " + genero;
    }
}
