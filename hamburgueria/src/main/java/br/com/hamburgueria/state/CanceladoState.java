package br.com.hamburgueria.state;

import br.com.hamburgueria.pedido.Pedido;

public class CanceladoState implements PedidoState {

    @Override
    public void confirmar(Pedido pedido) {
        throw new IllegalStateException("Pedido está cancelado.");
    }

    @Override
    public void preparar(Pedido pedido) {
        throw new IllegalStateException("Pedido está cancelado.");
    }

    @Override
    public void ficarPronto(Pedido pedido) {
        throw new IllegalStateException("Pedido está cancelado.");
    }

    @Override
    public void entregar(Pedido pedido) {
        throw new IllegalStateException("Pedido está cancelado.");
    }

    @Override
    public void cancelar(Pedido pedido) {
        throw new IllegalStateException("Pedido já está cancelado.");
    }

    @Override
    public String getNome() {
        return "Cancelado";
    }
}
