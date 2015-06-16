package edu.neu.ccis.sms;

import edu.neu.ccis.sms.entity.submissions.EvalType;

public class EvalTypeCheck {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        EvalType evalType = EvalType.valueOf("MEAAN");
        System.out.println("EvalType - " + evalType);
    }

}
