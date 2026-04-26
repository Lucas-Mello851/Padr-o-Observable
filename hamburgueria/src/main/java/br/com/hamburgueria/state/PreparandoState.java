package br.com.hamburgueria.state;

import br.com.hamburgueria.pedido.Pedido;

public class PreparandoState implements PedidoState {

    @Override
    public void confirmar(Pedido pedido) {
        throw new IllegalStateException("Pedido já está sendo preparado.");
    }

    @Override
    public void preparar(Pedido pedido) {
        throw new IllegalStateException("Pedido já está sendo preparado.");
    }

    @Override
    public void ficarPronto(Pedido pedido) {
        System.out.println("[" + pedido.getId() + "] Pedido pronto! Aguardando retirada...");
        pedido.setState(new ProntoState());
    }

    @Override
    public void entregar(Pedido pedido) {
        throw new IllegalStateException("Pedido ainda não está pronto.");
    }

    @Override
    public void cancelar(Pedido pedido) {
        System.out.println("[" + pedido.getId() + "] Pedido cancelado durante o preparo.");
        pedido.setState(new CanceladoState());
    }

    @Override
    public String getNome() {
        return "Preparando";
    }
}
