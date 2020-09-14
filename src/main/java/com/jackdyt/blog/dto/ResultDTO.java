package com.jackdyt.blog.dto;

import com.jackdyt.blog.exception.CustomizeErrorCode;
import com.jackdyt.blog.exception.CustomizeException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @description: Error result
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;

    public static ResultDTO errorCausedBy(Integer code, String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorCausedBy(CustomizeErrorCode errorCode) {
        return errorCausedBy(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultDTO errorCausedBy(CustomizeException ex) {
        return errorCausedBy(ex.getCode(),ex.getMessage());
    }

    public static ResultDTO success(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(4599);
        resultDTO.setMessage("successful");
        return resultDTO;
    }
    public static <T> ResultDTO  success(T t){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(4599);
        resultDTO.setMessage("successful");
        resultDTO.setData(t);
        return resultDTO;
    }



}
