package com.wani.gym.member.entity;

import com.wani.gym.member.entity.info.*;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class CustomerInfo {

    @Enumerated(EnumType.STRING)
    private CustomerCareer customerCareer;

    @Enumerated(EnumType.STRING)
    private RoundingProblem roundingProblem;

    @Enumerated(EnumType.STRING)
    private Lessons lessons;

    @Enumerated(EnumType.STRING)
    private Satisfaction satisfaction;

    @Enumerated(EnumType.STRING)
    private ExercisePlan exercisePlan;

    @Enumerated(EnumType.STRING)
    private PeriodExercisePlan periodExercisePlan;


}
