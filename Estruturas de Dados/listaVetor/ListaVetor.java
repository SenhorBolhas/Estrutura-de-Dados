/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listaVetor;

import java.security.InvalidParameterException;
import java.util.Iterator;

/**
 *
 * @author 11645
 */
public class ListaVetor<TipoGenerico> implements Lista<TipoGenerico> {

    TipoGenerico[] vetor;
    private int tamanho;
    private int quantidade = 0;

    public ListaVetor() {
        this(5);
    }

    public ListaVetor(int tamanho) {
        if (tamanho < 0) {
            throw new IllegalArgumentException("Argumento invalido: " + tamanho + "Somente numeros maiores que 0");
        }

        this.tamanho = tamanho;
        this.vetor = (TipoGenerico[]) new Object[this.tamanho];
    }

    @Override
    public void adicionarNoInicio(TipoGenerico elemento) {
        aumentar();
        System.arraycopy(this.vetor, 0, this.vetor, 1, this.quantidade);
        this.vetor[0] = elemento;
        this.quantidade++;
    }

    @Override
    public void adicionar(TipoGenerico elemento) {
        aumentar();
        this.vetor[this.quantidade] = elemento;
        this.quantidade++;
    }

    @Override
    public void adicionar(TipoGenerico elemento, int indice) {
        aumentar();
        if (!IndiceValido(indice)) {
            throw new InvalidParameterException("Posicao invalida");
        }

        System.arraycopy(this.vetor, indice, this.vetor, indice + 1, this.quantidade - indice);

        this.vetor[indice] = elemento;
        this.quantidade++;
    }

    @Override
    public TipoGenerico removerDoFim() {
        TipoGenerico aux = this.vetor[this.quantidade - 1];
        this.vetor[this.quantidade - 1] = null;
        this.quantidade--;
        return aux;
    }

    @Override
    public TipoGenerico removerDoInicio() {
        TipoGenerico aux = this.vetor[0];
        System.arraycopy(this.vetor, 1, this.vetor, 0, this.quantidade - 1);
        this.quantidade--;
        return aux;
    }

    @Override
    public TipoGenerico remover(int indice) throws InvalidParameterException {
        TipoGenerico aux = this.vetor[indice];
        System.arraycopy(this.vetor, indice + 1, this.vetor, indice, this.quantidade - 1 - indice);
        this.quantidade--;
        return aux;
    }

    @Override
    public boolean remover(TipoGenerico elemento) throws InvalidParameterException {
        if (this.contem(elemento)) {
            for (int i = 0; i < this.tamanho(); i++) {
                if (elemento.equals(this.vetor[i])) {
                    this.remover(i);
                    return true;
                }
            }
        }
        throw new InvalidParameterException("Objeto nao encontrado");
    }

    @Override
    public boolean buscar(TipoGenerico elemento) throws InvalidParameterException {
        for (int i = 0; i < quantidade; i++) {
            if (this.vetor[i].equals(elemento)) {
                return true;
            }
        }
        return false;
    }

    public int buscarIndice(TipoGenerico elemento) throws InvalidParameterException {
        for (int i = 0; i < quantidade; i++) {
            if (this.vetor[i].equals(elemento)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public TipoGenerico buscar(int indice) {
        if (!ocupado(indice)) {
            throw new InvalidParameterException("Posicao invalida");
        } else {
            return this.vetor[indice];
        }
    }

    @Override
    public TipoGenerico buscarDoInicio() {
        return this.buscar(0);
    }

    @Override
    public TipoGenerico buscarDoFim() {
        return this.buscar(quantidade - 1);
    }

    @Override
    public TipoGenerico alterar(TipoGenerico elemento, int indice) {
        if (indice < this.tamanho()) {
            this.vetor[indice] = elemento;
            return elemento;
        }
        return null;
    }

    @Override
    public boolean alterar(TipoGenerico elementoNovo, TipoGenerico elementoAntigo) {
        int indice = this.buscarIndice(elementoAntigo);
        if (indice >= 0) {
            this.vetor[indice] = elementoNovo;
            return true;
        }
        return false;
    }

    public boolean contem(TipoGenerico elemento) {
        for (int i = 0; i < this.quantidade; i++) {
            if (elemento == this.vetor[i]) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean vazio() {
        return (this.quantidade == 0);
    }

    @Override
    public int tamanho() {
        return this.quantidade;
    }

    @Override
    public void limpar() {
        this.vetor = null;
        this.tamanho = 1;
        this.quantidade = 0;
    }

    private void aumentar() {
        if (this.quantidade == this.tamanho) {
            TipoGenerico[] aux = (TipoGenerico[]) new Object[this.tamanho * 2];
            System.arraycopy(this.vetor, 0, aux, 0, this.tamanho);
            this.tamanho = this.tamanho * 2;
            this.vetor = aux;
        }
    }

    private boolean ocupado(int indice) {
        return indice >= 0 && indice < this.tamanho();
    }

    private boolean IndiceValido(int indice) {
        return indice >= 0 && indice <= this.quantidade;
    }

    public String escrito() {
        if (this.quantidade == 0) {
            return "[]";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("[");

        for (int i = 0; i < this.quantidade - 1; i++) {
            builder.append(this.vetor[i]);
            builder.append(", ");
        }
        builder.append(this.vetor[this.quantidade - 1]);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public Iterator<TipoGenerico> iterator() {
        return new IteradorVetor();
    }

    private class IteradorVetor implements Iterator<TipoGenerico> {

        private int posicaoAtual = 0;

        @Override
        public boolean hasNext() {
            return posicaoAtual < vetor.length && vetor[posicaoAtual] != null;
        }

        @Override
        public TipoGenerico next() {
            return vetor[posicaoAtual++];
        }

    }

}
