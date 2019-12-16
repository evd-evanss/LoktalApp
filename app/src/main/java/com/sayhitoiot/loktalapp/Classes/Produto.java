package com.sayhitoiot.loktalapp.Classes;

/**
 * Criado por Evandro Costa
 */

public class Produto {

    private String Titulo;
    private String Categoria;
    private String Descritivo;
    private int Imagem;

    public Produto() {
    }

    public Produto(String titulo, String categoria, String descritivo, int imagem) {
        Titulo = titulo;
        Categoria = categoria;
        Descritivo = descritivo;
        Imagem = imagem;
    }


    public String getTitulo() {
        return Titulo;
    }

    public String getCategoria() {
        return Categoria;
    }

    public String getDescritivo() {
        return Descritivo;
    }

    public int getImagem() {
        return Imagem;
    }


    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public void setDescritivo(String descritivo) {
        Descritivo = descritivo;
    }

    public void setImagem(int imagem) {
        Imagem = imagem;
    }
}
