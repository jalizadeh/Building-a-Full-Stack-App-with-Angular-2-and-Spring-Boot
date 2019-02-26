package com.linkedin.learning.rest;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linkedin.learning.converter.REtRRConverter;
import com.linkedin.learning.entity.RoomEntity;
import com.linkedin.learning.model.request.ReservationRequest;
import com.linkedin.learning.model.response.ReservationResponse;
import com.linkedin.learning.repository.PageableRoomRepository;
import com.linkedin.learning.repository.RoomRepository;

@RestController
@RequestMapping(ResourceConstants.ROOM_RESERVATION_V1)
public class ReservationResource {
	
	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	PageableRoomRepository pageableRoomRepository;
	
	@Autowired
	private REtRRConverter REtResrvRespConverter;
	
	@RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Page<ReservationResponse> getAvailableRooms(
			@RequestParam("checkin")
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			LocalDate checkin,
			@RequestParam("checkout")
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			LocalDate chekout,
			Pageable pageable
			){
		
		Page<RoomEntity> roomEntityList = pageableRoomRepository.findAll(pageable);
		
		return roomEntityList.map(REtResrvRespConverter::convert);
	}
	
	@RequestMapping(path = "/{roomId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<RoomEntity> getRoomById(
			@PathVariable
			Long roomId) {
		//RoomEntity roomEntity = roomRepository.findById(roomId).orElse(null);
		RoomEntity roomEntity = roomRepository.getById(roomId);
		return new ResponseEntity<>(roomEntity, HttpStatus.OK);
	}
	
	
	 @RequestMapping(path = "", method = RequestMethod.POST, 
			 produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
	         consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseEntity<ReservationResponse> createReservation(
	        @RequestBody
	        ReservationRequest reservationRequest) {

		 return new ResponseEntity<>(new ReservationResponse(), HttpStatus.CREATED);
	 }
	 
	 
	 @RequestMapping(path = "", method = RequestMethod.PUT, 
			 produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
	         consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseEntity<ReservationResponse> updateReservation(
	        @RequestBody
	        ReservationRequest reservationRequest) {

		 return new ResponseEntity<>(new ReservationResponse(), HttpStatus.OK);
	 }
	 
	 
	 @RequestMapping(path = "/{reservationId}",
			 method = RequestMethod.DELETE)
	 public ResponseEntity<Void> deleteReservation(
	        @PathVariable
	        long reservationId) {

		 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	 }
}
