package br.com.hamburgueria.observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GerenciaObserver implements Observer {

    private final List<String> historico = new ArrayList<>();

    @Override
    public void atualizar(String origem, String evento) {
        String msg = "[GERÊNCIA] " + origem + " → " + evento;
        historico.add(msg);
        System.out.println(msg);
    }

    public List<String> getHistorico() {
        return Collections.unmodifiableList(historico);
    }
}
