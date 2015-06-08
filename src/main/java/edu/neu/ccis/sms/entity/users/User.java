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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import edu.neu.ccis.sms.entity.categories.Category;
import edu.neu.ccis.sms.entity.categories.UserToMemberMapping;
import edu.neu.ccis.sms.entity.submissions.Document;

@Entity
@Table(name = "User", uniqueConstraints = {
        @UniqueConstraint(columnNames = "USER_ID"),
        @UniqueConstraint(columnNames = "EMAIL"),
        @UniqueConstraint(columnNames = "USERNAME") })
public class User implements Serializable {
    private static final long serialVersionUID = -4572727294954027970L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "FIRSTNAME", length = 255, nullable = false)
    private String firstname;

    @Column(name = "LASTNAME", length = 255)
    private String lastname;

    @Column(name = "EMAIL", nullable = false, length = 100)
    private String email;

    //TODO - Use MD5 hashing 
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "USERNAME", nullable = false, length = 100)
    private String username;

    /** Optional activation code. */
    @Column(name = "ACTIVATION", length = 100)
    private String activation;

    /** Optional expiration time of the activation code. */
    @Column(name = "EXPIRATION", length = 100)
    private Long expiration;

    /** Status of the person: nopass, waiting, active. */
    @Column(name = "STATUS", nullable = false)
    private StatusType status = StatusType.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID", nullable = true)
    private Team team;

    @ManyToMany(mappedBy = "submittedBy")
    private Set<Document> submissions = new HashSet<Document>();

    // TODO - Create a separate Topics entity with many-to-many relationship
    @Transient
    private Set<String> topicsOfInterest = new HashSet<String>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COI_WITH_USER_ID", nullable = true)
    private Category conflictOfInterestWithUsers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "conflictOfInterestWithUsers")
    private Set<User> conflictsOfInterest = new HashSet<User>();

    /** UserToMemberMapping many to one relationship */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<UserToMemberMapping> userToMemberMappings = new HashSet<UserToMemberMapping>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getActivation() {
        return activation;
    }

    public void setActivation(String activation) {
        this.activation = activation;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public Category getConflictOfInterestWithUsers() {
        return conflictOfInterestWithUsers;
    }

    public void setConflictOfInterestWithUsers(
            Category conflictOfInterestWithUsers) {
        this.conflictOfInterestWithUsers = conflictOfInterestWithUsers;
    }

    public Set<String> getTopicsOfInterest() {
        return topicsOfInterest;
    }

    public void setTopicsOfInterest(Set<String> topicsOfInterest) {
        this.topicsOfInterest = topicsOfInterest;
    }

    public boolean addTopicsOfInterest(String topicsOfInterest) {
        return this.topicsOfInterest.add(topicsOfInterest);
    }

    public Set<Document> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(Set<Document> submissions) {
        this.submissions = submissions;
    }

    public boolean addSubmission(Document submission) {
        return this.submissions.add(submission);
    }

    public Set<User> getConflictsOfInterest() {
        return conflictsOfInterest;
    }

    public boolean setConflictsOfInterestWithUsers(
            Set<User> conflictsOfInterestWithUsers) {
        return this.conflictsOfInterest.addAll(conflictsOfInterestWithUsers);
    }

    public boolean addConflictsOfInterestWithUser(
            User conflictsOfInterestWithUser) {
        return this.conflictsOfInterest.add(conflictsOfInterestWithUser);
    }

    public Set<UserToMemberMapping> getUserToMemberMappings() {
        return userToMemberMappings;
    }

    public void setUserToMemberMappings(
            Set<UserToMemberMapping> userToMemberMappings) {
        this.userToMemberMappings = userToMemberMappings;
    }

    public boolean addUserToMemberMapping(
            UserToMemberMapping userToMemberMapping) {
        return this.userToMemberMappings.add(userToMemberMapping);
    }

    /**
     * Validate the current user object
     * 
     * @return
     */
    public boolean isValidUser() {
        return false;
    }
}