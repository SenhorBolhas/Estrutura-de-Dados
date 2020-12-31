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
public interface iFila<TipoGenerico> {
    public void enfileirar(TipoGenerico valor);
    public TipoGenerico desenfileirar();
    public TipoGenerico primeiro();
    public int tamanho();
    public boolean vazio();
}
