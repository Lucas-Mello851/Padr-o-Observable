package br.com.hamburgueria;

import br.com.hamburgueria.cardapio.Cardapio;
import br.com.hamburgueria.component.Lanche;
import br.com.hamburgueria.concretecomponent.*;
import br.com.hamburgueria.concretedecorator.*;
import br.com.hamburgueria.factory.*;
import br.com.hamburgueria.observer.*;
import br.com.hamburgueria.pedido.Pedido;
import br.com.hamburgueria.state.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes — Hamburgueria (Singleton + Factory + Decorator + State + Observer)")
class HamburgueriaTest {



    @Test
    @DisplayName("Singleton: duas chamadas getInstance() retornam a mesma instância")
    void testSingletonMesmaInstancia() {
        assertSame(Cardapio.getInstance(), Cardapio.getInstance());
    }

    @Test
    @DisplayName("Singleton: cardápio contém as 3 fábricas registradas")
    void testSingletonFabricasRegistradas() {
        Cardapio c = Cardapio.getInstance();
        assertTrue(c.getFabricas().containsKey("Clássico"));
        assertTrue(c.getFabricas().containsKey("Vegano"));
        assertTrue(c.getFabricas().containsKey("Smash"));
    }

    @Test
    @DisplayName("Singleton: cardápio contém os 5 adicionais")
    void testSingletonAdicionaisRegistrados() {
        assertTrue(Cardapio.getInstance().getAdicionais().size() >= 5);
    }

    @Test
    @DisplayName("Singleton: tipo inválido lança IllegalArgumentException")
    void testSingletonTipoInvalido() {
        assertThrows(IllegalArgumentException.class,
            () -> Cardapio.getInstance().getFabrica("Inexistente"));
    }



    @Test
    @DisplayName("Factory: ClassicoFactory cria HamburguerClassico com preço R$22,00")
    void testFactoryClassico() {
        Lanche l = new ClassicoFactory().criar();
        assertInstanceOf(HamburguerClassico.class, l);
        assertEquals(22.00, l.getPreco(), 0.001);
    }

    @Test
    @DisplayName("Factory: VeganoFactory cria HamburguerVegano com preço R$26,00")
    void testFactoryVegano() {
        Lanche l = new VeganoFactory().criar();
        assertInstanceOf(HamburguerVegano.class, l);
        assertEquals(26.00, l.getPreco(), 0.001);
    }

    @Test
    @DisplayName("Factory: SmashFactory cria HamburguerSmash com preço R$28,00")
    void testFactorySmash() {
        Lanche l = new SmashFactory().criar();
        assertInstanceOf(HamburguerSmash.class, l);
        assertEquals(28.00, l.getPreco(), 0.001);
    }

    @Test
    @DisplayName("Factory via Cardápio: getFabrica cria o lanche correto")
    void testFactoryViaCardapio() {
        assertEquals(22.00, Cardapio.getInstance().getFabrica("Clássico").criar().getPreco(), 0.001);
    }



    @Test
    @DisplayName("Decorator: Queijo adiciona +R$3,00 e altera descrição")
    void testDecoratorQueijo() {
        Lanche l = new Queijo(new HamburguerClassico());
        assertEquals(25.00, l.getPreco(), 0.001);
        assertTrue(l.getDescricao().contains("Queijo Cheddar"));
    }

    @Test
    @DisplayName("Decorator: Bacon adiciona +R$4,00")
    void testDecoratorBacon() {
        assertEquals(26.00, new Bacon(new HamburguerClassico()).getPreco(), 0.001);
    }

    @Test
    @DisplayName("Decorator: Alface adiciona +R$1,00")
    void testDecoratorAlface() {
        assertEquals(23.00, new Alface(new HamburguerClassico()).getPreco(), 0.001);
    }

    @Test
    @DisplayName("Decorator: Tomate adiciona +R$1,00")
    void testDecoratorTomate() {
        assertEquals(23.00, new Tomate(new HamburguerClassico()).getPreco(), 0.001);
    }

    @Test
    @DisplayName("Decorator: MolhoEspecial adiciona +R$2,00")
    void testDecoratorMolho() {
        assertEquals(24.00, new MolhoEspecial(new HamburguerClassico()).getPreco(), 0.001);
    }

    @Test
    @DisplayName("Decorator: Smash completo = R$39,00")
    void testSmashCompleto() {
        Lanche l = new MolhoEspecial(new Tomate(new Alface(new Bacon(new Queijo(new HamburguerSmash())))));
        assertEquals(39.00, l.getPreco(), 0.001);
    }

    @Test
    @DisplayName("Decorator: duplo queijo soma duas vezes")
    void testDuploQueijo() {
        assertEquals(28.00, new Queijo(new Queijo(new HamburguerClassico())).getPreco(), 0.001);
    }

    @Test
    @DisplayName("Decorator: objeto original não é modificado")
    void testOriginalImutavel() {
        Lanche base = new HamburguerClassico();
        double precoOriginal = base.getPreco();
        new Queijo(base).getPreco();
        assertEquals(precoOriginal, base.getPreco(), 0.001);
    }



    @Test
    @DisplayName("State: pedido inicia no estado Recebido")
    void testStateInicial() {
        Pedido p = new Pedido("T001", new HamburguerClassico());
        assertInstanceOf(RecebidoState.class, p.getState());
        assertEquals("Recebido", p.getState().getNome());
    }

    @Test
    @DisplayName("State: confirmar move para Preparando")
    void testStateConfirmar() {
        Pedido p = new Pedido("T002", new HamburguerClassico());
        p.confirmar();
        assertInstanceOf(PreparandoState.class, p.getState());
    }

    @Test
    @DisplayName("State: ficarPronto move para Pronto")
    void testStateFicarPronto() {
        Pedido p = new Pedido("T003", new HamburguerClassico());
        p.confirmar();
        p.ficarPronto();
        assertInstanceOf(ProntoState.class, p.getState());
    }

    @Test
    @DisplayName("State: entregar move para Entregue")
    void testStateEntregar() {
        Pedido p = new Pedido("T004", new HamburguerClassico());
        p.confirmar();
        p.ficarPronto();
        p.entregar();
        assertInstanceOf(EntregueState.class, p.getState());
        assertEquals("Entregue", p.getState().getNome());
    }

    @Test
    @DisplayName("State: cancelar em Recebido move para Cancelado")
    void testStateCancelarRecebido() {
        Pedido p = new Pedido("T005", new HamburguerClassico());
        p.cancelar();
        assertInstanceOf(CanceladoState.class, p.getState());
    }

    @Test
    @DisplayName("State: cancelar em Preparando move para Cancelado")
    void testStateCancelarPreparando() {
        Pedido p = new Pedido("T006", new HamburguerClassico());
        p.confirmar();
        p.cancelar();
        assertInstanceOf(CanceladoState.class, p.getState());
    }



    @Test
    @DisplayName("State: preparar em Recebido lança exceção")
    void testStateInvalidoPreparar() {
        Pedido p = new Pedido("T007", new HamburguerClassico());
        assertThrows(IllegalStateException.class, p::preparar);
    }

    @Test
    @DisplayName("State: entregar em Recebido lança exceção")
    void testStateInvalidoEntregarRecebido() {
        Pedido p = new Pedido("T008", new HamburguerClassico());
        assertThrows(IllegalStateException.class, p::entregar);
    }

    @Test
    @DisplayName("State: cancelar em Pronto lança exceção")
    void testStateInvalidoCancelarPronto() {
        Pedido p = new Pedido("T009", new HamburguerClassico());
        p.confirmar();
        p.ficarPronto();
        assertThrows(IllegalStateException.class, p::cancelar);
    }

    @Test
    @DisplayName("State: qualquer ação em Entregue lança exceção")
    void testStateInvalidoEntregue() {
        Pedido p = new Pedido("T010", new HamburguerClassico());
        p.confirmar();
        p.ficarPronto();
        p.entregar();
        assertThrows(IllegalStateException.class, p::confirmar);
        assertThrows(IllegalStateException.class, p::cancelar);
        assertThrows(IllegalStateException.class, p::entregar);
    }

    @Test
    @DisplayName("State: qualquer ação em Cancelado lança exceção")
    void testStateInvalidoCancelado() {
        Pedido p = new Pedido("T011", new HamburguerClassico());
        p.cancelar();
        assertThrows(IllegalStateException.class, p::confirmar);
        assertThrows(IllegalStateException.class, p::cancelar);
        assertThrows(IllegalStateException.class, p::entregar);
    }



    @Test
    @DisplayName("Observer: cliente recebe notificação ao criar pedido")
    void testObserverNotificacaoCriacao() {
        Pedido p = new Pedido("O001", new HamburguerClassico());
        ClienteObserver cliente = new ClienteObserver("Ana");
        p.adicionarObserver(cliente);
        p.confirmar();
        assertFalse(cliente.getHistorico().isEmpty());
        assertTrue(cliente.getHistorico().stream()
            .anyMatch(h -> h.contains("Preparando")));
    }

    @Test
    @DisplayName("Observer: cozinha recebe notificação ao confirmar pedido")
    void testObserverCozinhaConfirmacao() {
        Pedido p = new Pedido("O002", new HamburguerClassico());
        CozinhaObserver cozinha = new CozinhaObserver();
        p.adicionarObserver(cozinha);
        p.confirmar();
        assertTrue(cozinha.getHistorico().stream()
            .anyMatch(h -> h.contains("Preparando")));
    }

    @Test
    @DisplayName("Observer: múltiplos observers recebem a mesma notificação")
    void testObserverMultiplos() {
        Pedido p = new Pedido("O003", new HamburguerClassico());
        ClienteObserver cliente = new ClienteObserver("Bruno");
        CozinhaObserver cozinha = new CozinhaObserver();
        p.adicionarObserver(cliente);
        p.adicionarObserver(cozinha);
        p.confirmar();
        assertTrue(cliente.getHistorico().stream().anyMatch(h -> h.contains("Preparando")));
        assertTrue(cozinha.getHistorico().stream().anyMatch(h -> h.contains("Preparando")));
    }

    @Test
    @DisplayName("Observer: observer removido não recebe notificações posteriores")
    void testObserverRemovido() {
        Pedido p = new Pedido("O004", new HamburguerClassico());
        ClienteObserver cliente = new ClienteObserver("Carlos");
        p.adicionarObserver(cliente);
        p.confirmar();
        int qtdAposConfirmar = cliente.getHistorico().size();

        p.removerObserver(cliente);
        p.ficarPronto();
        p.entregar();

        assertEquals(qtdAposConfirmar, cliente.getHistorico().size());
    }

    @Test
    @DisplayName("Observer: fluxo completo gera notificações em cada transição")
    void testObserverFluxoCompleto() {
        Pedido p = new Pedido("O005", new HamburguerClassico());
        ClienteObserver cliente = new ClienteObserver("Diana");


        p.adicionarObserver(cliente);

        p.confirmar();
        p.ficarPronto();
        p.entregar();

        assertEquals(3, cliente.getHistorico().size());
        assertTrue(cliente.getHistorico().get(0).contains("Preparando"));
        assertTrue(cliente.getHistorico().get(1).contains("Pronto"));
        assertTrue(cliente.getHistorico().get(2).contains("Entregue"));
    }

    @Test
    @DisplayName("Observer: cancelamento notifica todos os observers")
    void testObserverCancelamento() {
        Pedido p = new Pedido("O006", new HamburguerClassico());
        ClienteObserver cliente = new ClienteObserver("Eduardo");
        CozinhaObserver cozinha = new CozinhaObserver();
        p.adicionarObserver(cliente);
        p.adicionarObserver(cozinha);
        p.confirmar();
        p.cancelar();
        assertTrue(cliente.getHistorico().stream().anyMatch(h -> h.contains("Cancelado")));
        assertTrue(cozinha.getHistorico().stream().anyMatch(h -> h.contains("Cancelado")));
    }



    @Test
    @DisplayName("Observer: gerência notificada ao adicionar novo lanche")
    void testObserverCardapioNovoLanche() {
        Cardapio cardapio = Cardapio.getInstance();
        GerenciaObserver gerencia = new GerenciaObserver();
        cardapio.adicionarObserver(gerencia);
        cardapio.adicionarFabrica("X-Burguer Test", new ClassicoFactory());
        assertTrue(gerencia.getHistorico().stream()
            .anyMatch(h -> h.contains("X-Burguer Test")));
        cardapio.removerObserver(gerencia);
    }

    @Test
    @DisplayName("Observer: gerência notificada ao adicionar novo adicional")
    void testObserverCardapioNovoAdicional() {
        Cardapio cardapio = Cardapio.getInstance();
        GerenciaObserver gerencia = new GerenciaObserver();
        cardapio.adicionarObserver(gerencia);
        cardapio.adicionarAdicional("Ovo Frito Test", 1.50);
        assertTrue(gerencia.getHistorico().stream()
            .anyMatch(h -> h.contains("Ovo Frito Test")));
        cardapio.removerObserver(gerencia);
    }

    @Test
    @DisplayName("Observer: gerência removida não recebe notificações posteriores")
    void testObserverGerenciaRemovida() {
        Cardapio cardapio = Cardapio.getInstance();
        GerenciaObserver gerencia = new GerenciaObserver();
        cardapio.adicionarObserver(gerencia);
        cardapio.adicionarAdicional("Item Temp", 0.50);
        int qtdAntes = gerencia.getHistorico().size();

        cardapio.removerObserver(gerencia);
        cardapio.adicionarAdicional("Item Pos Remocao", 0.50);
        assertEquals(qtdAntes, gerencia.getHistorico().size());
    }



    @Test
    @DisplayName("Integração: Singleton + Factory + Decorator + State + Observer")
    void testIntegracaoCompleta() {

        Lanche l = Cardapio.getInstance().getFabrica("Smash").criar();

        l = new Queijo(l);
        l = new Bacon(l);
        assertEquals(35.00, l.getPreco(), 0.001);


        Pedido p = new Pedido("INT01", l);
        ClienteObserver cliente = new ClienteObserver("Fulano");
        CozinhaObserver cozinha = new CozinhaObserver();
        p.adicionarObserver(cliente);
        p.adicionarObserver(cozinha);

        p.confirmar();
        assertEquals("Preparando", p.getState().getNome());
        p.ficarPronto();
        assertEquals("Pronto", p.getState().getNome());
        p.entregar();
        assertEquals("Entregue", p.getState().getNome());



        assertEquals(3, cliente.getHistorico().size());
        assertEquals(3, cozinha.getHistorico().size());
    }

    @Test
    @DisplayName("Integração: pedido cancelado — observers avisados, estado final correto")
    void testIntegracaoCancelamento() {
        Lanche l = Cardapio.getInstance().getFabrica("Vegano").criar();
        l = new Alface(l);
        l = new Tomate(l);
        l = new MolhoEspecial(l);
        assertEquals(30.00, l.getPreco(), 0.001);

        Pedido p = new Pedido("INT02", l);
        ClienteObserver cliente = new ClienteObserver("Ciclano");
        p.adicionarObserver(cliente);
        p.confirmar();
        p.cancelar();

        assertEquals("Cancelado", p.getState().getNome());
        assertTrue(cliente.getHistorico().stream().anyMatch(h -> h.contains("Cancelado")));
        assertThrows(IllegalStateException.class, p::confirmar);
    }
}
