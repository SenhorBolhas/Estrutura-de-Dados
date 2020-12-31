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
import java.util.Iterator;

public class Fila<TipoGenerico> implements iFila<TipoGenerico> {
    /** Tamanho maximo */
    private int tamMax;
    /** Vetor da fila */
    private TipoGenerico[] filaVetor;
    /** Frente da fila */
    private int frente;
    /** Fundo da fila */
    private int fundo;
    /** Numero de itens */
    private int nItens;

    /**
     *
     * @param tamanho Tamanho da nova fila
     */
    public Fila(int tamanho){
        tamMax = tamanho;
        filaVetor = (TipoGenerico[]) new Object[tamanho];
        frente = 0;
        fundo = -1;
        nItens = 0;
    }

    public Fila(){
        this(10);
    }

    /**
     * Insere um elemento ao fundo da fila
     *
     * @param elemento Elemento para ser adicionado
     * @return True se o elemento foi adicionado
     */
    @Override
    public void enfileirar(TipoGenerico elemento) {
        if(cheio())
            redimencionar(tamMax);
        if(fundo == tamMax-1)//Se o fundo da fila é o fim do vetor
        {
            fundo=-1;
        }
            redimencionar(tamMax*2);
        fundo++;
        filaVetor[fundo] = elemento;
        nItens++;
    }

    /**
     * Remove um elemento da frente da fila
     *
     * @return True se foi removido com sucesso
     */
    @Override
    public TipoGenerico desenfileirar() {
        if(vazio()){
            throw new NullPointerException("Fila esta vazia");
        }
        TipoGenerico temp = filaVetor[frente];
        filaVetor[frente] = null;
        frente++;
        if(frente == tamMax) //Wrap-Around
            frente = 0;
        nItens--;
        return temp;
    }

    public TipoGenerico olhar() {
        return primeiro();
    }

    /**
     * Olha para o primeiro elemento da fila
     *
     * @return Primeiro elemento da fila
     */
    @Override
    public TipoGenerico primeiro() {
        return filaVetor[frente];
    }

    /**
     * Olha para o ultimo elemento da fila
     *
     * @return Ultimo elemento da fila
     */
    public TipoGenerico ultimo() {
        return filaVetor[fundo];
    }

    /**
     *
     * @return Numero de itens
     */
    @Override
    public int tamanho() {
        return nItens;
    }

    /**
     *
     * @return true se fila vazia
     */
    @Override
    public boolean vazio() {
        return (nItens==0);
    }

    /**
     *
     * @return true se fila cheia
     */
    public boolean cheio(){
        return (nItens == tamMax);
    }

    /**
     * Verifica se o elemento existe na fila
     *
     * @param elemento Elemento a verificar
     * @return True se o elemento existe na fila
     */
    public boolean contem(TipoGenerico elemento) {
        for (TipoGenerico aFilaVetor : filaVetor) {
            if (aFilaVetor.equals(elemento))
                return true;
        }
        return false;
    }

    /**
     * Todas as posições da fila assumem null
     */
    public void limpar() {
        filaVetor = (TipoGenerico[]) new Object[tamMax];
        frente = 0;
        fundo = -1;
        nItens = 0;
    }

    /**
     * Redefine o tamanho maximo da fila
     * @param tamanhoMaximo Tamanho desejado
     */
    public void setTamanhoMaximo(int tamanhoMaximo) {
        tamMax = tamanhoMaximo;
    }

    private void redimencionar(int novoTamanho){
        TipoGenerico[] vetorTemp = (TipoGenerico[]) new Object[novoTamanho];
        for(int i = 0; i < filaVetor.length; i++){
            vetorTemp[i] = filaVetor[i];
            filaVetor = vetorTemp;
        }
        tamMax = novoTamanho;
    }

    public Iterator<TipoGenerico> iterator() {
        return new IteratorFila();
    }

    private class IteratorFila implements Iterator<TipoGenerico> {

        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < filaVetor.length && filaVetor[currentIndex] != null;
        }

        @Override
        public TipoGenerico next() {
            return filaVetor[currentIndex++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}