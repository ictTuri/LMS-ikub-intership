package com.project.lms.utils;

import java.time.LocalDateTime;

import com.project.lms.model.RezervationEntity;

public class RezervationUtil {

	public static RezervationEntity rezervationOne() {
		RezervationEntity rezervation = new RezervationEntity();
		rezervation.setBook(BookUtil.bookOne());
		rezervation.setStudent(UserUtil.userTest());
		rezervation.setRezervationDate(LocalDateTime.now());
		return rezervation;
	}
	public static RezervationEntity rezervationTwo() {
		RezervationEntity rezervation = new RezervationEntity();
		rezervation.setBook(BookUtil.bookTwo());
		rezervation.setStudent(UserUtil.userTest());
		rezervation.setRezervationDate(LocalDateTime.now());
		return rezervation;
	}
	public static RezervationEntity rezervationThree() {
		RezervationEntity rezervation = new RezervationEntity();
		rezervation.setBook(BookUtil.bookThree());
		rezervation.setStudent(UserUtil.userTest());
		rezervation.setRezervationDate(LocalDateTime.now());
		return rezervation;
	}
}
