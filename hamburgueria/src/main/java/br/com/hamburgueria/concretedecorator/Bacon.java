package br.com.hamburgueria.concretedecorator;

import br.com.hamburgueria.component.Lanche;
import br.com.hamburgueria.decorator.AdicionalDecorator;

public class Bacon extends AdicionalDecorator {
    public Bacon(Lanche lanche) { super(lanche); }

    @Override
    public String getDescricao() { return lanche.getDescricao() + " + Bacon Crocante"; }

    @Override
    public double getPreco() { return lanche.getPreco() + 4.00; }
}
