
package br.com.laboratoriooptico.controller;

import br.com.laboratoriooptico.facade.OticaFacade;
import br.com.laboratoriooptico.model.Otica;
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
public class OticaController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private OticaFacade facade;

    private Otica otica;
    private List<Otica> oticas;
    private String pesquisa;

    @PostConstruct
    public void iniciar() {
        otica = new Otica();
        atualizarLista();
    }

    public void salvar() {
        try {
            facade.salvar(otica);

            adicionarMensagem(
                    FacesMessage.SEVERITY_INFO,
                    "Sucesso",
                    "Ótica salva com sucesso."
            );

            otica = new Otica();
            atualizarLista();

        } catch (Exception e) {
            adicionarMensagem(
                    FacesMessage.SEVERITY_ERROR,
                    "Erro",
                    "Não foi possível salvar a ótica."
            );
        }
    }

    public void novo() {
        otica = new Otica();
    }

    public void editar(Otica selecionada) {
        otica = facade.buscarPorId(selecionada.getId());
    }

    public void excluir(Otica selecionada) {
        try {
            facade.excluir(selecionada.getId());
            atualizarLista();

            adicionarMensagem(
                    FacesMessage.SEVERITY_INFO,
                    "Sucesso",
                    "Ótica excluída."
            );

        } catch (Exception e) {
            adicionarMensagem(
                    FacesMessage.SEVERITY_ERROR,
                    "Erro",
                    "Não foi possível excluir a ótica."
            );
        }
    }

    public void pesquisar() {
        if (pesquisa == null || pesquisa.isBlank()) {
            atualizarLista();
        } else {
            oticas = facade.pesquisar(pesquisa);
        }
    }

    public void atualizarLista() {
        oticas = facade.listarTodos();
    }

    private void adicionarMensagem(
            FacesMessage.Severity severidade,
            String titulo,
            String detalhe) {

        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(severidade, titulo, detalhe)
        );
    }

    public Otica getOtica() {
        return otica;
    }

    public void setOtica(Otica otica) {
        this.otica = otica;
    }

    public List<Otica> getOticas() {
        return oticas;
    }

    public String getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
    }
}
