package br.com.hamburgueria.factory;

import br.com.hamburgueria.component.Lanche;
import br.com.hamburgueria.concretecomponent.HamburguerSmash;

public class SmashFactory implements LancheFactory {
    @Override
    public Lanche criar() {
        return new HamburguerSmash();
    }
}
