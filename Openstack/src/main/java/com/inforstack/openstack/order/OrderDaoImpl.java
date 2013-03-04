package com.inforstack.openstack.order;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.controller.model.PaginationModel;
import com.inforstack.openstack.log.Logger;

@Repository
public class OrderDaoImpl extends BasicDaoImpl<Order> implements OrderDao {

	private static final Logger log = new Logger(OrderDaoImpl.class);
	
	@Override
	public CursorResult<Order> find(Integer tenantId, Integer status) {
		log.debug("Find all order(s) by tenant id : " + tenantId + ", status : " + status);
		try {
			List<Criterion> criterions = new ArrayList<Criterion>();
			if(tenantId != null){
				criterions.add(
						Restrictions.eq("tenant.id", tenantId)
				);
			}
			if(status != null){
				criterions.add(
						Restrictions.eq("status", status)
				);
			}
			
			Criterion criterion = null;
			for (Criterion c : criterions) {
				if(criterion == null){
					criterion = c;
				}else{
					criterion = Restrictions.and(criterion, c);
				}
			}
			
			Session session = ((Session) em.getDelegate())
					.getSessionFactory().getCurrentSession();
			Criteria criteria = session.createCriteria(Order.class);
			if(criterion != null){
				criteria.add(criterion);
			}
			
			ScrollableResults results = criteria.scroll();
			log.debug("Find successful");
			return new CursorResult<Order>(results, session);
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			throw re;
		}
	}

	@Override
	public PaginationModel<Order> findWithCreator(int pageIndex, int pageSize, Integer tenantId,
			Integer status) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Order> criteria = builder
				.createQuery(Order.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		Root<Order> root = criteria.from(Order.class);
		if(tenantId != null){
			predicates.add(
					builder.equal(root.get("tenant").get("id"), tenantId)
			);
		}
		if(status != null){
			predicates.add(
					builder.equal(root.get("status"), status)
			);
		}
		Predicate predicate =  builder.and(predicates.toArray(new Predicate[predicates.size()]));
		javax.persistence.criteria.Order[] orders = new javax.persistence.criteria.Order[]{
				builder.desc(root.get("createTime"))	
		};
		criteria.where(predicate);
		criteria.orderBy(orders);
		
		return this.getSelf().pagination(pageIndex, pageSize, criteria);
	}

	@Override
	public PaginationModel<Order> findAllWithoutSubOrder(int pageIndex, int pageSize) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Order> criteria = builder
				.createQuery(Order.class);
		Root<Order> root = criteria.from(Order.class);
		javax.persistence.criteria.Order[] orders = new javax.persistence.criteria.Order[]{
				builder.desc(root.get("createTime"))	
		};
		criteria.orderBy(orders);
		
		return this.getSelf().pagination(pageIndex, pageSize, criteria);
	}
	
}
