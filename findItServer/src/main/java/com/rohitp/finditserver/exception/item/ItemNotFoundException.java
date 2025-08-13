package com.rohitp.finditserver.exception.item;

import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Item not found")
@NoArgsConstructor
public class ItemNotFoundException extends EntityNotFoundException {

}
