/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.fx;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author M. Hartgring
 */
@Entity
@Table(name = "characters")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Characters.findAll", query = "SELECT c FROM Characters c"),
    @NamedQuery(name = "Characters.findByName", query = "SELECT c FROM Characters c WHERE c.name = :name"),
    @NamedQuery(name = "Characters.findByClass1", query = "SELECT c FROM Characters c WHERE c.class1 = :class1"),
    @NamedQuery(name = "Characters.findByLevel", query = "SELECT c FROM Characters c WHERE c.level = :level"),
    @NamedQuery(name = "Characters.findByRace", query = "SELECT c FROM Characters c WHERE c.race = :race")})
public class Characters implements Serializable {
    @Column(name = "id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "characters")
    private Collection<Owns> ownsCollection;
    @JoinTable(name = "owns", joinColumns = {
        @JoinColumn(name = "name", referencedColumnName = "name")}, inverseJoinColumns = {
        @JoinColumn(name = "user_name", referencedColumnName = "user_name")})
    @ManyToMany
    private Collection<Users> usersCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "class")
    private String class1;
    @Column(name = "level")
    private Integer level;
    @Column(name = "race")
    private String race;

    public Characters() {
    }

    public Characters(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClass1() {
        return class1;
    }

    public void setClass1(String class1) {
        this.class1 = class1;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Characters)) {
            return false;
        }
        Characters other = (Characters) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dev.fx.Characters[ name=" + name + " ]";
    }

    public void persist(Object object) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DEV_FXPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @XmlTransient
    public Collection<Users> getUsersCollection() {
        return usersCollection;
    }

    public void setUsersCollection(Collection<Users> usersCollection) {
        this.usersCollection = usersCollection;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlTransient
    public Collection<Owns> getOwnsCollection() {
        return ownsCollection;
    }

    public void setOwnsCollection(Collection<Owns> ownsCollection) {
        this.ownsCollection = ownsCollection;
    }
    
    
}
