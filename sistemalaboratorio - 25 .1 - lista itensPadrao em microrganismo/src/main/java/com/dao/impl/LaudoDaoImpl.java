package com.dao.impl;

import com.dao.ILaudoDao;
import com.model.Laudo;
import org.hibernate.Criteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;

/**
 * Created by LeoLana on 10/04/2016.
 */
public class LaudoDaoImpl extends Dao implements ILaudoDao, Serializable {

    @Override
    public Laudo laudoById(Integer id) {
        Criteria criteria = getSession().createCriteria(Laudo.class);
        criteria.add(Property.forName("idLaudo").eq(id));
        //criteria.add(Restrictions.eq("idLaudo", id));
        return (Laudo) criteria.uniqueResult();
    }
}
