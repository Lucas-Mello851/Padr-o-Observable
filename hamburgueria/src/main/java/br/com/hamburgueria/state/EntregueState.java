package br.com.hamburgueria.state;

import br.com.hamburgueria.pedido.Pedido;

public class EntregueState implements PedidoState {

    @Override
    public void confirmar(Pedido pedido) {
        throw new IllegalStateException("Pedido já foi entregue.");
    }

    @Override
    public void preparar(Pedido pedido) {
        throw new IllegalStateException("Pedido já foi entregue.");
    }

    @Override
    public void ficarPronto(Pedido pedido) {
        throw new IllegalStateException("Pedido já foi entregue.");
    }

    @Override
    public void entregar(Pedido pedido) {
        throw new IllegalStateException("Pedido já foi entregue.");
    }

    @Override
    public void cancelar(Pedido pedido) {
        throw new IllegalStateException("Pedido já foi entregue — não pode ser cancelado.");
    }

    @Override
    public String getNome() {
        return "Entregue";
    }
}
