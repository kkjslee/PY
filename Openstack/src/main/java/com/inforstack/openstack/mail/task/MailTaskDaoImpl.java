package com.inforstack.openstack.mail.task;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.basic.BasicDaoImpl.CursorResult;
import com.inforstack.openstack.order.Order;

@Repository
public class MailTaskDaoImpl extends BasicDaoImpl<MailTask> implements MailTaskDao {

	@Override
	public CursorResult<MailTask> findAll() {
		log.debug("Find all MailTask");
		try {
			Session session = ((Session) em.getDelegate())
					.getSessionFactory().getCurrentSession();
			Criteria criteria = session.createCriteria(MailTask.class);
			criteria.add(Restrictions.eq("deleted", false));
			criteria.addOrder(org.hibernate.criterion.Order.asc("priority"));
			
			ScrollableResults results = criteria.scroll();
			log.debug("Find successful");
			return new CursorResult<MailTask>(results, session);
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			throw re;
		}
	}

}
