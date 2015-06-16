package edu.neu.ccis.sms.entity.submissions;

/**
 * Decides what are final evaluation types to calculate the final grades for a
 * submitted-document - e.g. As there can be multiple evaluations for a given
 * submission, how to calculate the final grades for document. They can be 1)
 * AVERAGE - average out all evaluation results, 2) MAXIMUM - Take the maximum
 * result from available evaluations, 3) MINIMUM - Take the minimum result from
 * available evaluations, 4) MEAN - Take the mean of all available results
 * 
 * @Default value - will be always AVERAGE
 * @author Pramod R. Khare
 * @date 14-June-2015
 * @lastUpdate 15-June-2015
 */
public enum EvalType {
    AVERAGE, MAXIMUM, MINIMUM, MEAN
}
