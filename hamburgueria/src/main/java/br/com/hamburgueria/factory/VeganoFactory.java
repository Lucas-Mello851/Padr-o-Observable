package br.com.hamburgueria.factory;

import br.com.hamburgueria.component.Lanche;
import br.com.hamburgueria.concretecomponent.HamburguerVegano;

public class VeganoFactory implements LancheFactory {
    @Override
    public Lanche criar() {
        return new HamburguerVegano();
    }
}
