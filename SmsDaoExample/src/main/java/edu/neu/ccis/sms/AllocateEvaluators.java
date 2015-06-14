package edu.neu.ccis.sms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.neu.ccis.sms.dao.categories.MemberDao;
import edu.neu.ccis.sms.dao.categories.MemberDaoImpl;
import edu.neu.ccis.sms.dao.users.UserToReviewerMappingDao;
import edu.neu.ccis.sms.dao.users.UserToReviewerMappingDaoImpl;
import edu.neu.ccis.sms.entity.categories.Member;
import edu.neu.ccis.sms.entity.submissions.Document;
import edu.neu.ccis.sms.entity.users.User;
import edu.neu.ccis.sms.entity.users.UserToReviewerMapping;

public class AllocateEvaluators {
    public static void main(String[] args) {
        Long activeMemberId = new Long(2);

        // Get request parameter values - memberId and
        // numberOfEvaluatorsPerSub
        Long memberId = 3l;
        int numberOfEvaluatorsPerSubmission = 2;

        // DAO impls
        UserToReviewerMappingDao userToRevDao = new UserToReviewerMappingDaoImpl();
        MemberDao memberDao = new MemberDaoImpl();

        userToRevDao.deleteAllUserToReviewerMappingsForMember(memberId);

        /*
        List<User> evaluatorsList = new ArrayList<User>(memberDao.getEvaluatorsForMemberId(activeMemberId));
        System.out.println("Number of Evaluators - "+evaluatorsList.size());

        List<User> submittersList = new ArrayList<User>(memberDao.getSubmittersForMemberId(activeMemberId));
        System.out.println("Number of submitters - "+submittersList.size());

        Set<Member> allSubmittableMembers = memberDao.findAllSubmittableMembersByParentMemberId(activeMemberId);
        System.out.println("Number of submittable members for this course member - "+allSubmittableMembers.size());

        Member submittableToAllocateFor = memberDao.getMemberByIdWithSubmissions(memberId);
        System.out.println("Member to allocate for - "+submittableToAllocateFor.getName());

        Set<Document> submisions = submittableToAllocateFor.getSubmissions();
        System.out.println("Number of submissions already done for this member - "+submisions.size());

        // Sort by MemberId to get the index number of this submittable
        // member - for example, there are 10 assignments under a course,
        // then for each assignment we should try to allocate different
        // reviewers or evaluators for each submitters, but its not
        // mandatory, its an optimally random way
        List<Member> submittableList = new ArrayList<Member>(allSubmittableMembers);
        Collections.sort(submittableList);
        int submittableMemberPosition = submittableList.indexOf(submittableToAllocateFor);
        System.out.println("Submittable members position in submittableMembersList - "+submittableMemberPosition);

        // Check if there are enough evaluators to allocate from when
        // numberOfEvaluatorsPerSub is greater than the total available
        numberOfEvaluatorsPerSubmission = (numberOfEvaluatorsPerSubmission >= evaluatorsList.size()) ? evaluatorsList
                .size() : numberOfEvaluatorsPerSubmission;
        System.out.println("Final Number of evaluators per submission - "+numberOfEvaluatorsPerSubmission);

        int totalNoOfEvaluators = evaluatorsList.size();
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
        // Allocate the reviewer or evaluator for each Submitter user and
        // save their mapping into the database
        for (int i = 0; i < submittersList.size(); i++) {
            System.out.println("Allocating for submitter - "+submittersList.get(i).getEmail());

            // Generate the evalUserIndex for numberOfEvaluatorsPerSub times
            // i.e. When there are multiple reviewers to allocate per
            // submission for given member, then pick the nearly-randomized
            // Evluator User, and keep in a Set, continue choosing the
            // evaluator randomly until all required users found
            Set<User> allocatedEvaluators = new HashSet<User>();
            int allocatedCount = 0;
            while (allocatedCount < numberOfEvaluatorsPerSubmission) {
                int evalUserIndex = (i + submittableMemberPosition + allocatedCount) % totalNoOfEvaluators;
                User choosenEvaluator = evaluatorsList.get(evalUserIndex);
                if (allocatedEvaluators.add(choosenEvaluator)) {
                    allocatedCount++;
                }
            }
            System.out.println("Allocation finished for user - "+submittersList.get(i).getEmail());
            System.out.println("Allocated evaluators are - ");
            for(User eval : allocatedEvaluators){
                System.out.print("\t"+eval.getEmail());
            }
            System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++");

            User submitter = submittersList.get(i);

            for (User allocatedEvaluator : allocatedEvaluators) {
                // save this user to evaluator for given member id mapping
                // into database
                UserToReviewerMapping mapping = new UserToReviewerMapping();
                mapping.setEvaluator(allocatedEvaluator);
                mapping.setSubmitter(submitter);
                mapping.setEvaluationForMemberId(submittableToAllocateFor.getId());

                // Get the document id for this user's submission for this
                // member - if user has not submitted yet for this
                // submittable
                // member, then leave the field as null
                Document submissionDocBySubmitter = findSubmissionDocumentForUserFromAllSubmissions(submisions,
                        submitter);
                if (null != submissionDocBySubmitter) {
                    mapping.setEvaluateDocId(submissionDocBySubmitter.getId());
                }
                userToRevDao.saveUserToReviewerMapping(mapping);
            }
        }*/
    }
    
    public static void main1(String[] arg){
        List<String> t = new ArrayList<String>(null);
        System.out.println("List size - "+t.size());
    }

    private static Document findSubmissionDocumentForUserFromAllSubmissions(Set<Document> submisions, User submitter) {
        for (Document doc : submisions) {
            if (doc.getSubmittedBy().contains(submitter)) {
                return doc;
            }
        }
        return null;
    }

    public static void deleteUserToReviewerMappings(final Long memberId){
        //deleteAllUserToReviewerMappingsForMember(memberId);
    }
}
