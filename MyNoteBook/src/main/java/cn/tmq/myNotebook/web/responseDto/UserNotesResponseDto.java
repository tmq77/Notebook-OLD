package cn.tmq.myNotebook.web.responseDto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotesResponseDto implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5592494883293510871L;

	@SuppressWarnings("unused")
	private String id;
	
	@SuppressWarnings("unused")
	private String title;
	
}
