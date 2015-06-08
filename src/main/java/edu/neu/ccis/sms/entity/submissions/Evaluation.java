package edu.neu.ccis.sms.entity.submissions;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import edu.neu.ccis.sms.entity.users.User;

@Entity
@Table(name = "Evaluation", uniqueConstraints = { @UniqueConstraint(columnNames = "EVALUATION_ID") })
public class Evaluation implements Serializable {
    private static final long serialVersionUID = -8527463008215351943L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVALUATION_ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "RESULT", nullable = false)
    private Float result;

    @Column(name = "TOTAL", nullable = false)
    private Float outOfTotal;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EVALUATED_ON", nullable = false)
    private Date evaluatedOnTimestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User evaluatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENT_ID", nullable = false)
    private Document evaluationFor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getResult() {
        return result;
    }

    public void setResult(Float result) {
        this.result = result;
    }

    public Float getOutOfTotal() {
        return outOfTotal;
    }

    public void setOutOfTotal(Float outOfTotal) {
        this.outOfTotal = outOfTotal;
    }

    public Date getEvaluatedOnTimestamp() {
        return evaluatedOnTimestamp;
    }

    public void setEvaluatedOnTimestamp(Date evaluatedOnTimestamp) {
        this.evaluatedOnTimestamp = evaluatedOnTimestamp;
    }

    public User getEvaluatedBy() {
        return evaluatedBy;
    }

    public void setEvaluatedBy(User evaluatedBy) {
        this.evaluatedBy = evaluatedBy;
    }

    public Document getEvaluationFor() {
        return evaluationFor;
    }

    public void setEvaluationFor(Document evaluationFor) {
        this.evaluationFor = evaluationFor;
    }
}