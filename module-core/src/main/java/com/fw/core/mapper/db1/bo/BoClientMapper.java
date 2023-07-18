package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoClientPmDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoClientMapper {

    List<BoClientPmDTO> selectClientPmList(BoClientPmDTO boClientPmDTO);

    List<BoClientPmDTO> selectWefirmList(BoClientPmDTO boClientPmDTO);

}
