package com.infosense.ibilling.server.util.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@TableGenerator(
        name = "ibilling_constant_GEN", 
        table = "ibilling_seqs", 
        pkColumnName = "name", 
        valueColumnName = "next_id", 
        pkColumnValue = "ibilling_constant", 
        allocationSize = 100)
@Table(name="ibilling_constant"
    , uniqueConstraints = @UniqueConstraint(columnNames="name") 
)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IbillingConstant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
    private String name;
    private String content;
    
    public IbillingConstant() {
    }

	public IbillingConstant(int id, String name, String content) {
		this.id = id;
		this.name = name;
		this.content = content;
	}
	
	@Id 
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ibilling_constant_GEN")
    @Column(name="id", unique=true, nullable=false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="name", unique=true, nullable=false, length=30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="content", unique=true, nullable=false, length=100)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
