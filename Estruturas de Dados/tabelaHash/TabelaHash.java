/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabelaHash;

/**
 *
 * @author giova
 */
import java.security.InvalidParameterException;
import java.util.Iterator;
import listaVetor.ListaVetor;
import listaEncad.ListaDuplamenteEncadeada;

public class TabelaHash<K, V> {

    private ListaVetor<ListaDuplamenteEncadeada<Entrada<K, V>>> tabela;
    private int numListas;
    private int tamanho;

    public TabelaHash() {
        numListas = 10;
        tabela = new ListaVetor<ListaDuplamenteEncadeada<Entrada<K, V>>>(numListas);
        tamanho = 0;

        for (int i = 0; i < numListas; i++) {
            tabela.adicionar(null);
        }
    }

    public V inserir(K chave, V valor) throws InvalidParameterException {
        int indexLista = getListaIndex(chave);
        Entrada<K, V> no = tabela.buscar(indexLista).buscarDoFim();

        if ((tamanho * 1.0) / numListas < 0.75) {

            if (no == null) {
                ListaDuplamenteEncadeada<Entrada<K, V>> lista = new ListaDuplamenteEncadeada<Entrada<K, V>>();
                Entrada<K, V> novoNo = new Entrada<>(chave, valor);
                lista.adicionar(novoNo);
                tabela.adicionar(lista, indexLista);
            } else {
                Entrada<K, V> novoNo = new Entrada<>(chave, valor);
                tabela.buscar(indexLista).adicionarNoFim(no);
            }

            tamanho++;
            return valor;
        }

        ListaVetor<ListaDuplamenteEncadeada<Entrada<K, V>>> aux = tabela;
        numListas = 2 * numListas;
        tabela = new ListaVetor<ListaDuplamenteEncadeada<Entrada<K, V>>>(numListas);
        tamanho = 0;
        for (int i = 0; i < numListas; i++) {
            tabela.adicionar(null);
        }
        for (ListaDuplamenteEncadeada<Entrada<K, V>> lista : aux) {
            if (lista.buscarDoInicio() != null) {
                if (lista.tamanho() == 1) {
                    inserir(lista.buscarDoInicio().chave, lista.buscarDoInicio().valor);
                }

                if (lista.tamanho() == 2) {
                    inserir(lista.buscarDoInicio().chave, lista.buscarDoInicio().valor);
                    inserir(lista.buscarDoFim().chave, lista.buscarDoFim().valor);
                }

                if (lista.tamanho() > 2) {
                    for (int i = 0; i < lista.tamanho(); i++) {
                        inserir(lista.buscar(i).chave, lista.buscar(i).valor);
                    }
                }

            }

        }
        Entrada<K, V> novoNo = new Entrada<>(chave, valor);
        tabela.buscar(indexLista).adicionarNoFim(novoNo);
        tamanho++;
        return valor;
    }

    private int getListaIndex(K chave) {
        int hashCode = chave.hashCode();
        return hashCode % numListas;
    }

    public ListaVetor<V> buscarLista(K chave) {
        int indexLista = getListaIndex(chave);
        ListaVetor<V> lista = new ListaVetor<V>(tabela.buscar(indexLista).tamanho());
        for (int i = 0; i < tabela.buscar(indexLista).tamanho(); i++) {
            lista.adicionar(tabela.buscar(indexLista).buscar(i).valor);
        }
        return lista;
    }

    public V buscar(K chave) {
        int indexLista = getListaIndex(chave);
        if (tabela.buscar(indexLista).tamanho() == 0) {
            return null;
        }

        if (tabela.buscar(indexLista).tamanho() == 1) {
            Entrada<K, V> no = tabela.buscar(indexLista).buscarDoInicio();
            return no.valor;
        }

        if (tabela.buscar(indexLista).tamanho() > 1) {
            for (int i = 0; i < tabela.buscar(indexLista).tamanho(); i++ ) {       
                if (tabela.buscar(indexLista).buscar(i).chave.equals(chave)) {
                    return tabela.buscar(indexLista).buscar(i).valor;
                }
            }
        }
        return null;
    }

    public boolean remover(K chave) {
        int indexLista = getListaIndex(chave);
        if (tabela.buscar(indexLista).tamanho() == 0) {
            return false;
        }

        if (tabela.buscar(indexLista).tamanho() == 1) {
            tabela.buscar(indexLista).removerDoInicio();
            tamanho--;
            return true;
        }

        if (tabela.buscar(indexLista).tamanho() > 1) {
            for (int i = 0; i < tabela.buscar(indexLista).tamanho(); i++ ) {       
                if (tabela.buscar(indexLista).buscar(i).chave.equals(chave)) {
                    tabela.buscar(indexLista).remover(i);
                    tamanho--;
                    return true;
                }
            }
        }
        return false;
    }
    
     public boolean remover(K chave, int indice) {
        int indexLista = getListaIndex(chave);
        if (tabela.buscar(indexLista).tamanho() == 0) {
            return false;
        }

        if (tabela.buscar(indexLista).tamanho() == 1) {
            tabela.buscar(indexLista).removerDoInicio();
            tamanho--;
            return true;
        }

        if (tabela.buscar(indexLista).tamanho() > 1) {       
                if (tabela.buscar(indexLista).tamanho() >= indice) {
                    tabela.buscar(indexLista).remover(indice);
                    tamanho--;
                    return true;
                }
            
        }
        return false;
    }

    public int tamanho() {
       return this.tamanho;
    }

    public boolean vazio() {
        return (tamanho == 0);
    }

    public void limpar() {
        tabela.limpar();
        tamanho = 0;
    }

    public boolean contem(V valor) {
        for (ListaDuplamenteEncadeada<Entrada<K, V>> lista : tabela) {
            for (Entrada<K,V> busca : lista) {
                if(busca.valor.equals(valor)) {
                    return true;
                }
            }
    }
        return false;
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

        ListaVetor<ListaDuplamenteEncadeada<Entrada<K, V>>> lista = tabela;
        int indice = 0;

        @Override
        public boolean hasNext() {
            return indice < tamanho && lista.buscar(indice) != null;
        }

        @Override
        public V next() {
            return lista.buscar(indice++).buscarDoInicio().valor;
        }
    }

}
