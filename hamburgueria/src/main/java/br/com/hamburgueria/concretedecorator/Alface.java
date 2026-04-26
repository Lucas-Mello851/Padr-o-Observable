package br.com.hamburgueria.concretedecorator;

import br.com.hamburgueria.component.Lanche;
import br.com.hamburgueria.decorator.AdicionalDecorator;

public class Alface extends AdicionalDecorator {
    public Alface(Lanche lanche) { super(lanche); }

    @Override
    public String getDescricao() { return lanche.getDescricao() + " + Alface"; }

    @Override
    public double getPreco() { return lanche.getPreco() + 1.00; }
}
