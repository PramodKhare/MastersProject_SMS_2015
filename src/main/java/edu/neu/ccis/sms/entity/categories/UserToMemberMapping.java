package edu.neu.ccis.sms.entity.categories;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import edu.neu.ccis.sms.entity.users.RoleType;
import edu.neu.ccis.sms.entity.users.User;

@Entity
@Table(name = "UserToMemberMapping")
public class UserToMemberMapping implements Serializable {
    private static final long serialVersionUID = -2755010302418223918L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REGISTERED_ON", nullable = false, updatable = false)
    private Date registeredOn;

    @Column(name = "ROLE", nullable = false)
    private RoleType role;

    @Column(name = "ISACTIVE", nullable = false)
    private boolean isActive = true;

    public UserToMemberMapping() {
        // Automatically set the registered on datetime
        registeredOn = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Date getRegisteredOn() {
        return registeredOn;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
