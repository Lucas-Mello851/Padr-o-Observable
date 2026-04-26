package br.com.hamburgueria.state;

import br.com.hamburgueria.pedido.Pedido;

public class RecebidoState implements PedidoState {

    @Override
    public void confirmar(Pedido pedido) {
        System.out.println("[" + pedido.getId() + "] Pedido confirmado! Indo para a cozinha...");
        pedido.setState(new PreparandoState());
    }

    @Override
    public void preparar(Pedido pedido) {
        throw new IllegalStateException("Pedido ainda não foi confirmado.");
    }

    @Override
    public void ficarPronto(Pedido pedido) {
        throw new IllegalStateException("Pedido ainda não foi confirmado.");
    }

    @Override
    public void entregar(Pedido pedido) {
        throw new IllegalStateException("Pedido ainda não está pronto.");
    }

    @Override
    public void cancelar(Pedido pedido) {
        System.out.println("[" + pedido.getId() + "] Pedido cancelado antes da confirmação.");
        pedido.setState(new CanceladoState());
    }

    @Override
    public String getNome() {
        return "Recebido";
    }
}
