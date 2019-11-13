package telran.java30.forum.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DatesDto {
	@JsonFormat(pattern = "dd-MM-yyyy")
	LocalDateTime from;
	@JsonFormat(pattern = "dd-MM-yyyy")
	LocalDateTime to;

}
