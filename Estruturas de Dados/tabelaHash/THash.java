/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabelaHash;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;
import listaVetor.ListaVetor;
/**
 *
 * @author giova
 */
public class THash<K, V> {
    private ArrayList<Entrada<K, V>> tabela;
    private int numListas;
    private int tamanho;

    public THash() {
        tabela = new ArrayList<>();
        numListas = 10;
        tamanho = 0;

        for (int i = 0; i < numListas; i++) {
            tabela.add(null);
        }
    }

    
    public V inserir(K chave, V valor) throws InvalidParameterException {
        int indexLista = getListaIndex(chave);
        Entrada<K, V> head = tabela.get(indexLista);

        while (head != null) {
            if (head.chave.equals(chave)) {
                V aux = head.valor;
                head.valor = valor;
                return aux;
            }
            head = head.next;
        }

        tamanho++;
        head = tabela.get(indexLista);
        Entrada<K, V> novaEnt = new Entrada<>(chave, valor);
        novaEnt.next = head;
        tabela.set(indexLista, novaEnt);

        if ((1.0 * tamanho) / numListas >= 0.7) {
            ArrayList<Entrada<K, V>> aux = tabela;
            tabela = new ArrayList<>();
            numListas = 2 * numListas;
            tamanho = 0;
            for (int i = 0; i < numListas; i++) {
                tabela.add(null);
            }
            for (Entrada<K, V> headNode : aux) {
                while (headNode != null) {
                    inserir(headNode.chave, headNode.valor);
                    headNode = headNode.next;
                }
            }

        }
        return null;
    }

    
    public V buscar(K chave, V valor) {
        int indexLista = getListaIndex(chave);
        Entrada<K, V> head = tabela.get(indexLista);

        while (head != null) {
            if (head.chave.equals(chave))
                return head.valor;
            head = head.next;
        }
        return null;
    }

     public V buscar(K chave) {
        int indexLista = getListaIndex(chave);
        Entrada<K, V> head = tabela.get(indexLista);

        while (head != null) {
            if (head.chave.equals(chave))
                return head.valor;
            head = head.next;
        }
        return null;
    }
     
    
    public V remover(K chave) {
        int indexLista = getListaIndex(chave);
        Entrada<K, V> head = tabela.get(indexLista);

        Entrada<K, V> prev = null;
        while (head != null) {
            if (head.chave.equals(chave))
                break;
            prev = head;
            head = head.next;
        }
        if (head == null)
            return null;

        tamanho--;

        if (prev != null)
            prev.next = head.next;
        else
            tabela.set(indexLista, head.next);
        return head.valor;
    }

    
    public int tamanho() {
        return tamanho;
    }

    
    public boolean vazio() {
        return tamanho() == 0;
    }

    
    public void limpar() {
        limpar();
    }

    public ListaVetor<V> getToVetor() {
        ListaVetor<V> vetor = new ListaVetor<>();
        for (Entrada<K, V> x : tabela) {
            while (x != null) {
                vetor.adicionar(x.valor);
                x = x.next;
            }
        }
        return vetor;
    }

    
    public boolean contem(V valor) {
        for (Entrada<K, V> x : tabela) {
            while (x != null) {
                if (x.valor.equals(valor))
                    return true;
                x = x.next;
            }
        }
        return false;
    }

    public boolean contemChave(K chave) {
        for (Entrada<K, V> x : tabela) {
            while (x != null) {
                if (x.chave.equals(chave))
                    return true;
                x = x.next;
            }
        }
        return false;
    }

    private int getListaIndex(K chave) {
        int hashCode = chave.hashCode();
        if (hashCode < 0)
            hashCode = hashCode * (-1);
        return hashCode % numListas;
    }

    
    public String toString() {
        if (tamanho() == 0)
            return "[]";

        StringBuilder builder = new StringBuilder();
        builder.append("[");

        for (Entrada<K, V> x : tabela) {
            while (x != null) {
                builder.append(", " + x.valor.toString());
                x = x.next;
            }
        }
        builder.append("]");
        return builder.toString();
    }

    
    public Iterator<V> iterator() {
        return new IteratorHash();
    }

    private class Entrada<K, V> {
        protected K chave;
        protected V valor;
        protected Entrada<K, V> next;

        public Entrada(K chave, V valor) {
            this.chave = chave;
            this.valor = valor;
        }
    }

    private class IteratorHash implements Iterator<V> {
        ArrayList<Entrada<K, V>> lista = tabela;
        int indice = 0;
        @Override
        public boolean hasNext() {
            return indice < tamanho && lista.get(indice) != null;
        }

        @Override
        public V next() {
            return lista.get(indice++).valor;
        }
    }
}
