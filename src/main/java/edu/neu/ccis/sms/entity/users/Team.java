package edu.neu.ccis.sms.entity.users;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Team", uniqueConstraints = { @UniqueConstraint(columnNames = "TEAM_ID") })
public class Team implements Serializable {
    private static final long serialVersionUID = -5099640508401022233L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEAM_ID", unique = true, nullable = false)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
    private Set<User> teammembers = new HashSet<User>();

    @Column(name = "NAME", length = 255)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getTeamMembers() {
        return this.teammembers;
    }

    public void setTeammembers(Set<User> teammembers) {
        this.teammembers = teammembers;
    }

    public boolean addTeammembers(User teammember) {
        return this.teammembers.add(teammember);
    }
}