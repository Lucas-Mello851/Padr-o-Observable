package br.com.hamburgueria.main;

import br.com.hamburgueria.cardapio.Cardapio;
import br.com.hamburgueria.component.Lanche;
import br.com.hamburgueria.concretedecorator.*;
import br.com.hamburgueria.concretecomponent.HamburguerSmash;
import br.com.hamburgueria.factory.SmashFactory;
import br.com.hamburgueria.observer.*;
import br.com.hamburgueria.pedido.Pedido;

public class Main {

    public static void main(String[] args) {


        Cardapio cardapio = Cardapio.getInstance();
        GerenciaObserver gerencia = new GerenciaObserver();
        cardapio.adicionarObserver(gerencia);

        cardapio.exibirCardapio();

        System.out.println("══════════════════════════════════════════");
        System.out.println("  SIMULAÇÃO DE PEDIDOS COM OBSERVERS");
        System.out.println("══════════════════════════════════════════\n");


        Lanche lanche1 = cardapio.getFabrica("Clássico").criar();
        lanche1 = new Queijo(lanche1);
        lanche1 = new Bacon(lanche1);
        lanche1 = new MolhoEspecial(lanche1);

        Pedido p1 = new Pedido("P001", lanche1);
        ClienteObserver cliente1 = new ClienteObserver("Ana");
        CozinhaObserver cozinha  = new CozinhaObserver();
        p1.adicionarObserver(cliente1);
        p1.adicionarObserver(cozinha);

        p1.confirmar();
        p1.ficarPronto();
        p1.entregar();

        System.out.println();


        Lanche lanche2 = cardapio.getFabrica("Vegano").criar();
        lanche2 = new Alface(lanche2);
        lanche2 = new Tomate(lanche2);

        Pedido p2 = new Pedido("P002", lanche2);
        ClienteObserver cliente2 = new ClienteObserver("Bruno");
        p2.adicionarObserver(cliente2);
        p2.adicionarObserver(cozinha);

        p2.confirmar();
        p2.cancelar();

        System.out.println();


        System.out.println("── Gerência adicionando novo lanche ao cardápio ──");
        cardapio.adicionarFabrica("Frango Crispy", new SmashFactory());
        cardapio.adicionarAdicional("Cebola Caramelizada", 2.50);

        System.out.println();


        System.out.println("── Cliente Ana remove observer do P001 ──");
        Lanche lanche3 = cardapio.getFabrica("Smash").criar();
        lanche3 = new Queijo(lanche3);
        Pedido p3 = new Pedido("P003", lanche3);
        p3.adicionarObserver(cliente1);
        p3.adicionarObserver(cozinha);
        p3.confirmar();

        System.out.println("  [removendo cliente1 do P003]");
        p3.removerObserver(cliente1);
        p3.ficarPronto();
        p3.entregar();

        System.out.println("\n══════════════════════════════════════════");
    }
}
