package br.com.hamburgueria.observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClienteObserver implements Observer {

    private final String nome;
    private final List<String> historico = new ArrayList<>();

    public ClienteObserver(String nome) {
        this.nome = nome;
    }

    @Override
    public void atualizar(String origem, String evento) {
        String msg = "[CLIENTE " + nome + "] Pedido " + origem + " → " + evento;
        historico.add(msg);
        System.out.println(msg);
    }

    public String getNome() {
        return nome;
    }

    public List<String> getHistorico() {
        return Collections.unmodifiableList(historico);
    }
}
