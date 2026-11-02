
package br.com.laboratoriooptico.facade;

import br.com.laboratoriooptico.model.Lente;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class LenteFacade {

    @PersistenceContext(unitName = "LaboratorioPU")
    private EntityManager em;

    public void salvar(Lente lente) {
        if (lente.getId() == null) {
            em.persist(lente);
        } else {
            em.merge(lente);
        }
    }

    public Lente buscarPorId(Long id) {
        return em.find(Lente.class, id);
    }

    public List<Lente> listarTodos() {
        return em.createQuery(
                "SELECT l FROM Lente l ORDER BY l.descricao",
                Lente.class
        ).getResultList();
    }

    public List<Lente> pesquisar(String pesquisa) {
        return em.createQuery(
                "SELECT l FROM Lente l "
                + "WHERE LOWER(l.codigo) LIKE :pesquisa "
                + "OR LOWER(l.descricao) LIKE :pesquisa "
                + "ORDER BY l.descricao",
                Lente.class
        )
        .setParameter("pesquisa", "%" + pesquisa.toLowerCase() + "%")
        .getResultList();
    }

    public void excluir(Long id) {
        Lente lente = buscarPorId(id);

        if (lente != null) {
            em.remove(lente);
        }
    }
}
