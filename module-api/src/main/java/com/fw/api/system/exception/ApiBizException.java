package com.fw.api.system.exception;

import com.fw.core.code.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Business Exception
 * @author sjpaik
 * @since 22.08.21
 */
@Getter
@AllArgsConstructor
public class ApiBizException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final ResponseCode responseCode;

}
