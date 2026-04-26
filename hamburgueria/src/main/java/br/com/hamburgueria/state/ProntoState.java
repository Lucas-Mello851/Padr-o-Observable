package br.com.hamburgueria.state;

import br.com.hamburgueria.pedido.Pedido;

public class ProntoState implements PedidoState {

    @Override
    public void confirmar(Pedido pedido) {
        throw new IllegalStateException("Pedido já está pronto.");
    }

    @Override
    public void preparar(Pedido pedido) {
        throw new IllegalStateException("Pedido já está pronto.");
    }

    @Override
    public void ficarPronto(Pedido pedido) {
        throw new IllegalStateException("Pedido já está pronto.");
    }

    @Override
    public void entregar(Pedido pedido) {
        System.out.println("[" + pedido.getId() + "] Pedido entregue ao cliente. Bom apetite!");
        pedido.setState(new EntregueState());
    }

    @Override
    public void cancelar(Pedido pedido) {
        throw new IllegalStateException("Pedido já está pronto — não pode ser cancelado.");
    }

    @Override
    public String getNome() {
        return "Pronto";
    }
}
