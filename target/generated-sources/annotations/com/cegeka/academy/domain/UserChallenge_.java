package com.cegeka.academy.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserChallenge.class)
public abstract class UserChallenge_ {

	public static volatile SingularAttribute<UserChallenge, Invitation> invitation;
	public static volatile SingularAttribute<UserChallenge, Challenge> challenge;
	public static volatile SingularAttribute<UserChallenge, Date> startTime;
	public static volatile SingularAttribute<UserChallenge, Long> id;
	public static volatile SingularAttribute<UserChallenge, Date> endTime;
	public static volatile SingularAttribute<UserChallenge, User> user;
	public static volatile SingularAttribute<UserChallenge, String> status;
	public static volatile SingularAttribute<UserChallenge, Double> points;

	public static final String INVITATION = "invitation";
	public static final String CHALLENGE = "challenge";
	public static final String START_TIME = "startTime";
	public static final String ID = "id";
	public static final String END_TIME = "endTime";
	public static final String USER = "user";
	public static final String STATUS = "status";
	public static final String POINTS = "points";

}

