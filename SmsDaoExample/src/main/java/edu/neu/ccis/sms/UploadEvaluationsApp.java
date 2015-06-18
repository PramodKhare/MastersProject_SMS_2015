package edu.neu.ccis.sms;

import java.util.Date;
import java.util.Set;

import edu.neu.ccis.sms.dao.categories.MemberDao;
import edu.neu.ccis.sms.dao.categories.MemberDaoImpl;
import edu.neu.ccis.sms.dao.submissions.DocumentDao;
import edu.neu.ccis.sms.dao.submissions.DocumentDaoImpl;
import edu.neu.ccis.sms.dao.submissions.EvaluationDao;
import edu.neu.ccis.sms.dao.submissions.EvaluationDaoImpl;
import edu.neu.ccis.sms.dao.users.UserDao;
import edu.neu.ccis.sms.dao.users.UserDaoImpl;
import edu.neu.ccis.sms.entity.categories.Member;
import edu.neu.ccis.sms.entity.submissions.Document;
import edu.neu.ccis.sms.entity.submissions.Evaluation;
import edu.neu.ccis.sms.entity.users.User;

public class UploadEvaluationsApp {

    public static void main(String[] arg){
        EvaluationDao evalDao = new EvaluationDaoImpl();
        /*Evaluation eval = evalDao.getEvaluation(new Long(1));
        eval.setEvaluationFor(null);
        evalDao.updateEvaluation(eval);*/
        
        Evaluation e = new Evaluation();
        e.setComments("dfdfdfdfdfddfd");
        e.setEvaluatedBy(new UserDaoImpl().getUser(new Long(1)));
        e.setOutOfTotal(100f);
        e.setResult(89f);
        
        evalDao.saveEvaluation(e);
    }

    /**
     * @param args
     */
    public static void main1(String[] args) {

        Long evaluatorId = new Long(1);

        // DAOs
        DocumentDao docDao = new DocumentDaoImpl();
        UserDao userDao = new UserDaoImpl();
        MemberDao memberDao = new MemberDaoImpl();
        EvaluationDao evalDao = new EvaluationDaoImpl();

        Long submittableMemberId = new Long(3);
        Float maxGrades = new Float(100);

        User evaluatedBy = userDao.getUserByIdWithSubmittersToEvaluateMappings(evaluatorId);
        Set<User> submittersToEvaluate = evaluatedBy.getSubmittersToEvaluateForMemberId(submittableMemberId);

        Member submittableMember = memberDao.getMember(submittableMemberId);

        int count = submittersToEvaluate.size();
        // Get all the input params from request and create Evaluation
        // objects and store them into backend db

        String strSubmitterUserId = "6";
        try {
            Long submitterUserId = Long.parseLong(strSubmitterUserId);
            Float gradesReceived = new Float(97.4);
            String comments = "comments";

            Document submittedDoc = userDao.getSubmissionDocumentForMemberIdByUserId(submitterUserId,
                    submittableMemberId);

            if (submittedDoc != null) {
                // Check if this document is already evaluated by this
                // Evaluator - and this is not re-evaluation
                submittedDoc = docDao.getDocumentByIdWithEvaluations(submittedDoc.getId());
                Set<Evaluation> evaluations = submittedDoc.getEvaluations();
                Evaluation eval = getOldEvaluationByThisEvaluator(evaluations, evaluatorId);

                if (eval == null) {
                    // Create a Evaluation object and store the
                    // evaluation
                    eval = new Evaluation();
                    eval.setComments(comments);
                    eval.setEvaluatedBy(evaluatedBy);
                    eval.setOutOfTotal(maxGrades);
                    eval.setResult(gradesReceived);
                    eval.setEvaluationFor(submittedDoc);
                    evalDao.saveEvaluation(eval);
                } else {
                    // Update the evaluation details and save the object
                    // again
                    eval.setComments(comments);
                    eval.setOutOfTotal(maxGrades);
                    eval.setResult(gradesReceived);
                    eval.setEvaluatedOnTimestamp(new Date());
                    evalDao.updateEvaluation(eval);
                }

                // TODO - calculate the final evaluations if
                // disseminate_evaluations process is already done
                // if (submittableMember.isFinalEvaluated()) {
                // calculateAndSaveFinalEvaluations(submittableMember,
                // submittedDoc, evaluatedBy);
                // }

                System.out.println("Successfully Submitted evaluation for document - "
                        + submittedDoc.getCmsDocumentPath() + " - by userId - " + submitterUserId);
            } else {
                System.out.println("This user hasnot yet submitted the submission document - ignoring..");
            }
        } catch (final Exception e) {
            System.out.println("Unable to persist evaluation for UserId - " + strSubmitterUserId);
            e.printStackTrace();
        }

        System.out.println("Successfully uploaded the evaluations for Member! - " + submittableMemberId);

    }
    

    /**
     * Check if there is any old evaluation by this Evaluator
     * 
     * @Note = there can be only one evaluation by one unique evaluator per
     *       document
     * @param evaluations
     * @param evaluatorId
     * @return previous Evaluation object
     */
    private static Evaluation getOldEvaluationByThisEvaluator(Set<Evaluation> evaluations, Long evaluatorId) {
        // Go over each evaluation and find out the evaluation done by this
        // user-i.e. EVALUATOR
        for (Evaluation eval : evaluations) {
            if (eval.getEvaluatedBy().getId().equals(evaluatorId)) {
                return eval;
            }
        }
        return null;
    }

}
