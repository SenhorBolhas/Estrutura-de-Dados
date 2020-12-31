/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filas;

/**
 *
 * @author giova
 */
import listaEncad.ListaDuplamenteEncadeada;

import java.util.Iterator;

public class FilaEncad<TipoGenerico> implements iFila<TipoGenerico> {

    private ListaDuplamenteEncadeada<TipoGenerico> fila;
    private int tamMax;

    public FilaEncad() {
        fila = new ListaDuplamenteEncadeada<>();
        tamMax = Integer.MAX_VALUE;
    }

    /**
     * Adiciona um elemento na fila
     *
     * @param elemento Elemento a ser adicionado
     * @return True se conseguiu adicionar
     */
    @Override
    public void enfileirar(TipoGenerico elemento) {
        if (tamMax == fila.tamanho()) {
            throw new IndexOutOfBoundsException("Fila cheia");
        }
        fila.adicionar(elemento);
    }

    /**
     * Retira e retorna o primeiro elemento da fila
     *
     * @return Proximo da fila
     */
    @Override
    public TipoGenerico desenfileirar() {
        return fila.removerDoInicio();
    }

    @Override
    public TipoGenerico primeiro() {
        return fila.buscarDoInicio();
    }

    public TipoGenerico ultimo() {
        return fila.buscarDoFim();
    }

    @Override
    public int tamanho() {
        return fila.tamanho();
    }

    @Override
    public boolean vazio() {
        return fila.tamanho() == 0;
    }

    public boolean cheio() {
        return tamMax == fila.tamanho();
    }

    public int contem(TipoGenerico elemento) {
        return fila.contem(elemento);
    }

    public void limpar() {
        fila.limpar();
    }

    public void setTamanhoMaximo(int tamanhoMaximo) {
        this.tamMax = tamanhoMaximo;
    }

    public Iterator<TipoGenerico> iterator() {
        return null;
    }

    @Override
    public String toString() {
        return fila.toString();
    }

}
