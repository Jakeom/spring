package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoMaindisplayDTO;

import java.util.List;

public interface BoMaindisplayMapper {

    List<BoMaindisplayDTO> selectMaindisplayList(BoMaindisplayDTO boMaindisplayDTO);

    BoMaindisplayDTO selectMaindisplay(BoMaindisplayDTO boMaindisplayDTO);

    void insertMaindisplay(BoMaindisplayDTO boMaindisplayDTO);

    void deleteMaindisplay(BoMaindisplayDTO boMaindisplayDTO);

    void updateMaindisplay(BoMaindisplayDTO boMaindisplayDTO);

}
