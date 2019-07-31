package com.cegeka.academy.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserChallenge.class)
public abstract class UserChallenge_ {

	public static volatile SingularAttribute<UserChallenge, Long> challengeId;
	public static volatile SingularAttribute<UserChallenge, Date> startTime;
	public static volatile SingularAttribute<UserChallenge, Long> id;
	public static volatile SingularAttribute<UserChallenge, Long> invitationId;
	public static volatile SingularAttribute<UserChallenge, Date> endTime;
	public static volatile SingularAttribute<UserChallenge, Long> userId;
	public static volatile SingularAttribute<UserChallenge, String> status;
	public static volatile SingularAttribute<UserChallenge, Double> points;

	public static final String CHALLENGE_ID = "challengeId";
	public static final String START_TIME = "startTime";
	public static final String ID = "id";
	public static final String INVITATION_ID = "invitationId";
	public static final String END_TIME = "endTime";
	public static final String USER_ID = "userId";
	public static final String STATUS = "status";
	public static final String POINTS = "points";

}
