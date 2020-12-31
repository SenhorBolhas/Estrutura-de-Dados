/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listaVetor;

/**
 *
 * @author 11645
 * @param <TipoGenerico>
 */

public interface Lista<TipoGenerico> extends Iterable<TipoGenerico>{
    public void adicionarNoInicio(TipoGenerico elemento);
    public void adicionar(TipoGenerico elemento);
    public void adicionar(TipoGenerico elemento, int indice);
    
    public TipoGenerico removerDoFim();
    public TipoGenerico removerDoInicio();
    public TipoGenerico remover(int indice);
    public boolean  remover(TipoGenerico elemento);
    
    public boolean buscar(TipoGenerico elemento);
    public TipoGenerico buscar(int indice);
    public TipoGenerico buscarDoInicio();
    public TipoGenerico buscarDoFim();
    
    public TipoGenerico alterar(TipoGenerico elemento, int indice);
    public boolean alterar(TipoGenerico elementoNovo, TipoGenerico elementoAntigo);
    
    public boolean vazio();
    
    public int tamanho();
    
    public void limpar();
}
