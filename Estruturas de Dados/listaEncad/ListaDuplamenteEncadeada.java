/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listaEncad;

/**
 *
 * @author giova
 */
import java.security.InvalidParameterException;
import java.util.Iterator;
import listaVetor.Lista;

public class ListaDuplamenteEncadeada<TipoGenerico> implements Lista<TipoGenerico> {

    private No<TipoGenerico> inicio;
    private No<TipoGenerico> fim;
    private int contador = 0;
    private No<TipoGenerico> ultimoNoVisitado;
    
    public ListaDuplamenteEncadeada() {
        this.inicio = null;
        this.fim = null;
    }

    @Override
    public void adicionar(TipoGenerico elemento) {
        if (contador == 0) {
            this.adicionarNoInicio(elemento);
        } else {
            No<TipoGenerico> novo = new No<>(this.fim, elemento, null);
            this.fim.setProximo(novo);
            this.fim = novo;
            this.contador++;
    }
    }

    @Override
    public void adicionar(TipoGenerico elemento, int indice) throws InvalidParameterException {
        if (indice == 0) {
            this.adicionarNoInicio(elemento);
        } else if (indice == this.contador) {
            this.adicionar(elemento);
        } else {
            No<TipoGenerico> novo = new No<>(elemento);            
            novo.setProximo(this.getNo(indice - 1).getProximo());
            novo.setAnterior(this.getNo(indice -1));
            this.getNo(indice - 1).setProximo(novo);
            this.contador++;
        }
    }

    @Override
    public void adicionarNoInicio(TipoGenerico elemento) {
        if (this.inicio == null) {
            No<TipoGenerico> novo = new No(elemento);
            this.inicio = this.fim = novo;
            this.contador++;
        }
        else {
        No<TipoGenerico> novo = new No(this.inicio, elemento);
        this.inicio.setAnterior(novo);
        this.inicio = novo;
        if (this.contador == 0) {
            this.fim = novo;
        }      
        this.contador++;
        }
}

    public void adicionarNoFim(TipoGenerico elemento) {
        this.adicionar(elemento);
    }

    public void adicionarTodos(TipoGenerico... elemento) {
        for (TipoGenerico aux : elemento) {
            this.adicionar(aux);
        }
    }

    public void AdicionarTodos(Iterable<TipoGenerico> lista) {
        for (TipoGenerico aux : lista) {
            this.adicionar(aux);
        }
    }

    @Override
    public TipoGenerico buscarDoInicio() {
        return this.getNo(0).getElemento();
    }
    
    @Override
    public TipoGenerico buscar(int indice) throws InvalidParameterException {
        return this.getNo(indice).getElemento();
    }

    @Override
    public TipoGenerico buscarDoFim() {
        return this.fim.getElemento();
    }


    
    @Override
    public boolean buscar(TipoGenerico elemento) {
        No<TipoGenerico> aux = this.inicio;
        for (int i = 0; i <= contador - 1; i++) {
            if (aux.getElemento().equals(elemento)) {
                return true;
            }
            aux = aux.getProximo();
        }
        throw new InvalidParameterException("Valor não existe");
    }

    @Override
    public TipoGenerico remover(int indice) throws InvalidParameterException {
        if (!valido(indice)) throw new InvalidParameterException("Index invalidado");
        if (indice == 0) {
            this.removerDoInicio();
        } else if (indice == this.contador - 1) {
            this.removerDoFim();
        } else {
            No<TipoGenerico> anterior = this.getNo(indice - 1);
            No<TipoGenerico> atual = anterior.getProximo();
            TipoGenerico elemento = atual.getElemento();
            No<TipoGenerico> proximo = atual.getProximo();

            anterior.setProximo(proximo);
            this.contador--;
            return elemento;
        }
        return null;
    }

    @Override
    public boolean remover(TipoGenerico elemento) {
        if (this.contem(elemento) >= 0) {
            this.remover(this.contem(elemento));
        return true;
        }
        return false;
        }

    @Override
    public TipoGenerico removerDoFim() throws InvalidParameterException {
        if (!this.valido(this.contador - 1)) throw new InvalidParameterException("Posicao nao existe");
        if (this.contador == 1) {
            this.removerDoInicio();
        } else {
            No<TipoGenerico> anterior = this.getNo(this.contador - 2);
            TipoGenerico elemento = (TipoGenerico) anterior.getProximo().getElemento();
            anterior.setProximo(null);
            this.fim = anterior;
            this.contador--;
            return elemento;
        }
        return null;
    }

    @Override
    public TipoGenerico removerDoInicio() throws InvalidParameterException {
        if (!this.valido(0)) throw new InvalidParameterException("Lista vazia");
        
        TipoGenerico elemento = this.inicio.getElemento();
        this.inicio = this.inicio.getProximo();
        this.inicio.getAnterior().setProximo(null);
        this.inicio.setAnterior(null);
        this.contador--;
        if (this.contador == 0) this.fim = null;
        return elemento;
    }

    public int contem(TipoGenerico element) {
        No<TipoGenerico> atual = this.inicio;
        int i = 0;
        while (atual != null) {
            if (atual.getElemento().equals(element))
                return (int)i;
            atual = atual.getProximo();
            i++;
        }
        return -1;
    }

    @Override
    public boolean vazio() {
        return this.inicio == null && this.fim == null && this.tamanho() == 0;
    }

    @Override
    public void limpar() {
        this.inicio = null;
        this.fim = this.inicio;
        this.contador = 0;
    }

    @Override
    public int tamanho() {
        return this.contador;
    }

    public String escrito() {
        if (this.contador == 0)
            return "[]";

        StringBuilder builder = new StringBuilder("[");
        No<TipoGenerico> atual = this.inicio;
        for (int i = 0; i < this.contador - 1; i++) {
            builder.append(atual.getElemento());
            builder.append(", ");
            atual = atual.getProximo();
        }
        builder.append(atual.getElemento());
        builder.append("]");

        return builder.toString();
    }

    @Override
    public Iterator<TipoGenerico> iterator() {
        return new IteratorEncad();
    }

    private boolean valido(int index) {
        return index >= 0 && index < this.contador;
    }

    private No<TipoGenerico> getNo(int indice) {
      //  if (!this.valido(indice))
       //     throw new IllegalArgumentException("Index nao existe");
      
        No<TipoGenerico> atual = this.inicio;
        for (int i = 0; i < indice; i++) {
            atual = atual.getProximo();
        }
        return atual;
    }

    @Override
    public TipoGenerico alterar(TipoGenerico elemento, int indice) {
        if (indice >= 0 && indice < this.tamanho()){
        getNo(indice).setElemento(elemento);
        return elemento;
    }    
        return null;
    }
    
    @Override
    public boolean alterar(TipoGenerico elementoNovo, TipoGenerico elementoAntigo) {
        int i = contem(elementoAntigo);
        if (i >= 0) {
        this.alterar(elementoNovo, i);
        return true;
        }
        return false;
    }

 

    private class IteratorEncad implements Iterator<TipoGenerico> {

        No<TipoGenerico> current = inicio;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public TipoGenerico next() {
            if (hasNext()) {
                TipoGenerico elemento = current.getElemento();
                current = current.getProximo();
                return elemento;
            }
            return null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class No<TipoGenerico> {
        private No proximo;
        private TipoGenerico elemento;
        private No anterior;
        private int posicao;

        public No(No proximo) {
            this.proximo = proximo;
        }

        public No(TipoGenerico elemento) {
            this.elemento = elemento;
        }

        public No(No proximo, TipoGenerico elemento) {
            this.proximo = proximo;
            this.elemento = elemento;
        }

        public No(No anterior, TipoGenerico elemento, No proximo) {
            this.anterior = anterior;
            this.elemento = elemento;
            this.proximo = proximo;
        }
        
        public TipoGenerico getElemento() {
            return elemento;
        }

        public void setElemento(TipoGenerico elemento) {
            this.elemento = elemento;
        }

        public No getProximo() {
            return proximo;
        }

        public void setProximo(No proximo) {
            this.proximo = proximo;
        }
        
        public No getAnterior() {
            return anterior;
        }
        
        public void setAnterior(No anterior) {
            this.anterior = anterior;
        }
    }
}