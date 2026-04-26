package br.com.hamburgueria.state;

import br.com.hamburgueria.pedido.Pedido;

public interface PedidoState {


    void confirmar(Pedido pedido);


    void preparar(Pedido pedido);


    void ficarPronto(Pedido pedido);


    void entregar(Pedido pedido);


    void cancelar(Pedido pedido);


    String getNome();
}
