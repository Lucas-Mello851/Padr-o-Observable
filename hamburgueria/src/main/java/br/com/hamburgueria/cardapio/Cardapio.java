package br.com.hamburgueria.cardapio;

import br.com.hamburgueria.factory.*;
import br.com.hamburgueria.observer.Observable;
import br.com.hamburgueria.observer.Observer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Cardapio implements Observable {


    private static final Cardapio INSTANCIA = new Cardapio();

    private Cardapio() {
        registrarFabricas();
        registrarAdicionais();
    }

    public static Cardapio getInstance() {
        return INSTANCIA;
    }


    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void adicionarObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removerObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notificarObservers(String evento) {
        for (Observer o : observers) {
            o.atualizar("Cardápio", evento);
        }
    }


    private final Map<String, LancheFactory> fabricas = new LinkedHashMap<>();

    private void registrarFabricas() {
        fabricas.put("Clássico", new ClassicoFactory());
        fabricas.put("Vegano",   new VeganoFactory());
        fabricas.put("Smash",    new SmashFactory());
    }

    public void adicionarFabrica(String tipo, LancheFactory factory) {
        fabricas.put(tipo, factory);
        notificarObservers("Novo lanche adicionado ao cardápio: " + tipo
            + " — R$ " + String.format("%.2f", factory.criar().getPreco()));
    }

    public LancheFactory getFabrica(String tipo) {
        LancheFactory f = fabricas.get(tipo);
        if (f == null) throw new IllegalArgumentException("Tipo desconhecido: " + tipo);
        return f;
    }

    public Map<String, LancheFactory> getFabricas() {
        return fabricas;
    }


    private final Map<String, Double> adicionais = new LinkedHashMap<>();

    private void registrarAdicionais() {
        adicionais.put("Queijo Cheddar",  3.00);
        adicionais.put("Bacon Crocante",  4.00);
        adicionais.put("Alface",          1.00);
        adicionais.put("Tomate",          1.00);
        adicionais.put("Molho Especial",  2.00);
    }

    public void adicionarAdicional(String nome, double preco) {
        adicionais.put(nome, preco);
        notificarObservers("Novo adicional no cardápio: " + nome
            + " — R$ " + String.format("%.2f", preco));
    }

    public Map<String, Double> getAdicionais() {
        return adicionais;
    }


    public void exibirCardapio() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         CARDÁPIO — HAMBURGUERIA      ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  LANCHES BASE                        ║");
        fabricas.forEach((nome, fab) ->
            System.out.printf("║  %-22s R$ %5.2f       ║%n",
                nome, fab.criar().getPreco()));
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  ADICIONAIS                          ║");
        adicionais.forEach((nome, preco) ->
            System.out.printf("║  %-22s R$ %5.2f       ║%n", nome, preco));
        System.out.println("╚══════════════════════════════════════╝\n");
    }
}
