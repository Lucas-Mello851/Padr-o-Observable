package br.com.hamburgueria.factory;

import br.com.hamburgueria.component.Lanche;
import br.com.hamburgueria.concretecomponent.HamburguerClassico;

public class ClassicoFactory implements LancheFactory {
    @Override
    public Lanche criar() {
        return new HamburguerClassico();
    }
}
