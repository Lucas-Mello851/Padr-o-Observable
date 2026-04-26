package br.com.hamburgueria.concretecomponent;

import br.com.hamburgueria.component.Lanche;

public class HamburguerVegano implements Lanche {

    @Override
    public String getDescricao() {
        return "Hambúrguer Vegano (Pão Integral + Blend Grão-de-Bico)";
    }

    @Override
    public double getPreco() {
        return 26.00;
    }
}
