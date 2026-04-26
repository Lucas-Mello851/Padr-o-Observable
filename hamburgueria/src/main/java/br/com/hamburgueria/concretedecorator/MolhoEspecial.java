package br.com.hamburgueria.concretedecorator;

import br.com.hamburgueria.component.Lanche;
import br.com.hamburgueria.decorator.AdicionalDecorator;

public class MolhoEspecial extends AdicionalDecorator {
    public MolhoEspecial(Lanche lanche) { super(lanche); }

    @Override
    public String getDescricao() { return lanche.getDescricao() + " + Molho Especial"; }

    @Override
    public double getPreco() { return lanche.getPreco() + 2.00; }
}
