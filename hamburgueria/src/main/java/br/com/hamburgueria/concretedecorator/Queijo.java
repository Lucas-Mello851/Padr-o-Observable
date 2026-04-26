package br.com.hamburgueria.concretedecorator;

import br.com.hamburgueria.component.Lanche;
import br.com.hamburgueria.decorator.AdicionalDecorator;

public class Queijo extends AdicionalDecorator {
    public Queijo(Lanche lanche) { super(lanche); }

    @Override
    public String getDescricao() { return lanche.getDescricao() + " + Queijo Cheddar"; }

    @Override
    public double getPreco() { return lanche.getPreco() + 3.00; }
}
