
package br.com.laboratoriooptico.facade;

import br.com.laboratoriooptico.model.Otica;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Stateless
public class OticaFacade {

    @PersistenceContext(unitName = "LaboratorioPU")
    private EntityManager em;

    public void salvar(Otica otica) {
        if (otica.getId() == null) {
            em.persist(otica);
        } else {
            em.merge(otica);
        }
    }

    public Otica buscarPorId(Long id) {
        return em.find(Otica.class, id);
    }

    public List<Otica> listarTodos() {
        return em.createQuery(
                "SELECT o FROM Otica o ORDER BY o.nome",
                Otica.class
        ).getResultList();
    }

    public List<Otica> pesquisar(String nome) {
        TypedQuery<Otica> query = em.createQuery(
                "SELECT o FROM Otica o "
                + "WHERE LOWER(o.nome) LIKE LOWER(:nome) "
                + "ORDER BY o.nome",
                Otica.class
        );

        query.setParameter("nome", "%" + nome.trim() + "%");

        return query.getResultList();
    }

    public void excluir(Long id) {
        Otica otica = em.find(Otica.class, id);

        if (otica != null) {
            em.remove(otica);
        }
    }
}
