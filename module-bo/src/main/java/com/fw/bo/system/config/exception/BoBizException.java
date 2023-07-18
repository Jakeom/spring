package com.fw.bo.system.config.exception;

import com.fw.core.code.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Business Exception
 * @author sjpaik
 */
@Getter
@AllArgsConstructor
public class BoBizException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final ResponseCode responseCode;

}
