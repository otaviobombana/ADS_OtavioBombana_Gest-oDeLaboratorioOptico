
package br.com.laboratoriooptico.controller;

import br.com.laboratoriooptico.facade.LenteFacade;
import br.com.laboratoriooptico.model.Lente;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class LenteController implements Serializable {

    @EJB
    private LenteFacade facade;

    private Lente lente;
    private List<Lente> lentes;
    private String pesquisa;

    @PostConstruct
    public void iniciar() {
        novo();
        atualizarLista();
    }

    public void salvar() {
        try {
            facade.salvar(lente);

            adicionarMensagem(
                    FacesMessage.SEVERITY_INFO,
                    "Sucesso",
                    "Lente salva com sucesso!"
            );

            novo();
            atualizarLista();

        } catch (Exception e) {
            adicionarMensagem(
                    FacesMessage.SEVERITY_ERROR,
                    "Erro",
                    "Não foi possível salvar a lente: " + e.getMessage()
            );
        }
    }

    public void novo() {
        lente = new Lente();
        lente.setAtivo(true);
    }

    public void editar(Lente selecionada) {
        lente = facade.buscarPorId(selecionada.getId());
    }

    public void excluir(Lente selecionada) {
        try {
            facade.excluir(selecionada.getId());

            adicionarMensagem(
                    FacesMessage.SEVERITY_INFO,
                    "Sucesso",
                    "Lente excluída com sucesso!"
            );

            atualizarLista();

        } catch (Exception e) {
            adicionarMensagem(
                    FacesMessage.SEVERITY_ERROR,
                    "Erro",
                    "Não foi possível excluir a lente. Verifique se ela está vinculada a algum pedido."
            );
        }
    }

    public void pesquisar() {
        if (pesquisa == null || pesquisa.trim().isEmpty()) {
            atualizarLista();
        } else {
            lentes = facade.pesquisar(pesquisa.trim());
        }
    }

    public void atualizarLista() {
        lentes = facade.listarTodos();
    }

    private void adicionarMensagem(
            FacesMessage.Severity tipo,
            String titulo,
            String detalhe) {

        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(tipo, titulo, detalhe)
        );
    }

    public Lente getLente() {
        return lente;
    }

    public void setLente(Lente lente) {
        this.lente = lente;
    }

    public List<Lente> getLentes() {
        return lentes;
    }

    public void setLentes(List<Lente> lentes) {
        this.lentes = lentes;
    }

    public String getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
    }
}
