package br.com.hamburgueria.concretedecorator;

import br.com.hamburgueria.component.Lanche;
import br.com.hamburgueria.decorator.AdicionalDecorator;

public class Tomate extends AdicionalDecorator {
    public Tomate(Lanche lanche) { super(lanche); }

    @Override
    public String getDescricao() { return lanche.getDescricao() + " + Tomate"; }

    @Override
    public double getPreco() { return lanche.getPreco() + 1.00; }
}
