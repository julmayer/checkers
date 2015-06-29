package de.htwg.checkers.persistence.hibernate;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;

import de.htwg.checkers.models.Cell;
import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;
import de.htwg.checkers.models.GameState;
import de.htwg.checkers.persistence.IDAO;
import de.htwg.checkers.persistence.PersistContainer;

public class HibernateDAO implements IDAO {

    @Override
    public void savePersistContainer(PersistContainer container) {
        Session session = null;
        Transaction tx = null;
        
        try {
            session = HibernateUtil.getInstance().getCurrentSession();
            tx = session.beginTransaction();
                        
            PersistentPersistContainer pc = new PersistentPersistContainer(container);
            
            session.save(pc);
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        }
    }

    @Override
    public PersistContainer getPersistContainerBy(String name) {
        PersistentPersistContainer ppc = getPPContainerBy(name);
        return generateContainer(ppc);
    }
    
    private PersistentPersistContainer getPPContainerBy(String name) {
        Session session = null;
        Transaction tx = null;
        PersistentPersistContainer ppContainer;
        try {
            session = HibernateUtil.getInstance().getCurrentSession();
            tx = session.beginTransaction();

            Criteria query = session.createCriteria(PersistentPersistContainer.class).
                    add(Restrictions.eq("name", name));
            ppContainer = (PersistentPersistContainer) query.uniqueResult();
            Hibernate.initialize(ppContainer.getField());
            Hibernate.initialize(ppContainer.getState());
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        }
        return ppContainer;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PersistContainer> getAllPersistContainers() {
        Session session = null;
        Transaction tx = null;
        List<PersistentPersistContainer> tmpResult;
        List<PersistContainer> result = new LinkedList<PersistContainer>();
        try {
            session = HibernateUtil.getInstance().getCurrentSession();
            tx = session.beginTransaction();
            tmpResult = session.createCriteria(PersistentPersistContainer.class).list();
            for (PersistentPersistContainer ppc : tmpResult) {
                Hibernate.initialize(ppc.getField());
                Hibernate.initialize(ppc.getState());
                PersistContainer pc = generateContainer(ppc);
                result.add(pc);
            }
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        }
        
        return result;
    }

    @Override
    public void updatePersistContainer(PersistContainer container) {
        Session session = null;
        Transaction tx = null;
        
        try {
            session = HibernateUtil.getInstance().getCurrentSession();
            tx = session.beginTransaction();
                        
            PersistentPersistContainer pc = getPPContainerBy(container.getName());
            
            session.update(pc);
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        }

    }

    @Override
    public void deletePersistContainer(PersistContainer container) {
        Session session = null;
        Transaction tx = null;
        
        try {
            session = HibernateUtil.getInstance().getCurrentSession();
            tx = session.beginTransaction();
                        
            PersistentPersistContainer pc = getPPContainerBy(container.getName());
            
            session.delete(pc);
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        }
    }

    private PersistContainer generateContainer(PersistentPersistContainer ppc) {
        PersistentField pField = ppc.getField();
        PersistentGameState pGameState = ppc.getState();
        GameState gameState = new GameState(pGameState.isBlackTurn(), pGameState.getMoveCount(),
                pGameState.isHasMoreKills(), pGameState.getBot(), pGameState.getCurrentState());
        Field field = new Field(pField.getSize());
        
        for (PersistentFigure pFigure : pField.getFigures()) {
            Cell cell = field.getCellByCoordinates(pFigure.getXPosition(), pFigure.getYPosition());
            Figure figure = new Figure(cell, pFigure.isBlack());
            figure.setCrowned(pFigure.isCrowned());
        }
        
        return new PersistContainer(ppc.getName(), field, gameState);
    }
}
