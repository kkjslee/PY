package com.inforstack.openstack.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.basic.BasicDaoImpl.CursorResult;
import com.inforstack.openstack.controller.model.PaginationModel;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.utils.CollectionUtil;

@Repository
public class OrderDaoImpl extends BasicDaoImpl<Order> implements OrderDao {

	private static final Logger log = new Logger(OrderDaoImpl.class);
	
	@Override
	public CursorResult<Integer> find(Integer tenantId, Date billingDate,
			Integer status){
		log.debug("Find all order(s) by tenant id : " + tenantId + ", billing date : "
			+ billingDate + ", status : " + status);
		try {
			List<Criterion> criterions = new ArrayList<Criterion>();
			if(tenantId != null){
				criterions.add(
						Restrictions.eq("o.tenant.id", tenantId)
				);
			}
			if(status != null){
				criterions.add(
						Restrictions.eq("o.status", status)
				);
			}
			if(billingDate != null){
				criterions.add(
						Restrictions.lt("s.nextBillingDate", billingDate)
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
			Criteria criteria = session.createCriteria(Order.class, "o");
			if(billingDate != null){
				criteria = criteria.createCriteria("o.subOrders", "s", JoinType.INNER_JOIN);
			}
			if(criterion != null){
				criteria.add(criterion);
			}
			criteria.setProjection(Projections.distinct(Projections.property("o.id")));
			
			ScrollableResults results = criteria.scroll();
			log.debug("Find successful");
			return new CursorResult<Integer>(results, session);
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			throw re;
		}
	}

	@Override
	public CursorResult<Integer> find(List<Integer> orderPeriods,
			Date billingDate, Integer status){
		log.debug("Find all order(s) by order period, billingDate, status");
		try {
			List<Criterion> criterions = new ArrayList<Criterion>();
			if(status != null){
				criterions.add(
						Restrictions.eq("o.status", status)
				);
			}
			if(billingDate != null){
				criterions.add(
						Restrictions.lt("s.nextBillingDate", billingDate)
				);
			}
			if(!CollectionUtil.isNullOrEmpty(orderPeriods)){
				criterions.add(
						Restrictions.in("s.orderPeriod.id", orderPeriods)
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
			Criteria criteria = session.createCriteria(Order.class, "o");
			if(billingDate != null  || !CollectionUtil.isNullOrEmpty(orderPeriods)){
				criteria = criteria.createCriteria("o.subOrders", "s", JoinType.INNER_JOIN);
			}
			if(criterion != null){
				criteria.add(criterion);
			}
			criteria.setProjection(Projections.distinct(Projections.property("o.id")));
			
			ScrollableResults results = criteria.scroll();
			log.debug("Find successful");
			return new CursorResult<Integer>(results, session);
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
		
		return this.pagination(pageIndex, pageSize, criteria);
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
		
		return this.pagination(pageIndex, pageSize, criteria);
	}
	
}
