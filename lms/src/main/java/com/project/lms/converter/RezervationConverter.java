package com.project.lms.converter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.project.lms.dto.RezervationDto;
import com.project.lms.model.BookEntity;
import com.project.lms.model.RezervationEntity;
import com.project.lms.model.UserEntity;

public class RezervationConverter {

	private RezervationConverter() {
	}

	public static RezervationDto toDto(RezervationEntity rezervation) {
		RezervationDto rezervationToReturn = new RezervationDto();
		rezervationToReturn.setId(rezervation.getId());
		rezervationToReturn.setStudentUsername(rezervation.getStudent().getUsername());
		rezervationToReturn.setBookTitle(rezervation.getBook().getTitle());
		rezervationToReturn.setRezervationDate(rezervation.getRezervationDate());
		rezervationToReturn.setReturnDate(rezervation.getReturnDate());
		return rezervationToReturn;
	}

	public static List<RezervationDto> toListDto(List<RezervationEntity> rezervations) {
		List<RezervationDto> rezervationsToReturn = new ArrayList<>();
		for (RezervationEntity re : rezervations) {
			rezervationsToReturn.add(toDto(re));
		}
		return rezervationsToReturn;
	}

	public static RezervationEntity toEntityCreate(BookEntity book,	UserEntity student) {
		RezervationEntity rezervationToReturn = new RezervationEntity();
		rezervationToReturn.setBook(book);
		rezervationToReturn.setStudent(student);
		rezervationToReturn.setRezervationDate(LocalDateTime.now());
		rezervationToReturn.setReturnDate(null);
		return rezervationToReturn;
	}

	public static RezervationEntity toEntityUpdate(RezervationEntity rezervationToUpdate ,
												   BookEntity book,
												   UserEntity student) {
		rezervationToUpdate.setBook(book);
		rezervationToUpdate.setStudent(student);
		return rezervationToUpdate;
	}
	
	public static RezervationEntity toEntityReturn(RezervationEntity rezervationToClose) {
		rezervationToClose.setReturnDate(LocalDateTime.now());
		return rezervationToClose;
	}

}
