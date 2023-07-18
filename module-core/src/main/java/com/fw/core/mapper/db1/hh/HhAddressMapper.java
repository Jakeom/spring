package com.fw.core.mapper.db1.hh;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.hh.HhContactsDTO;
import com.fw.core.dto.hh.HhContactsGroupDTO;


import java.util.List;

@Mapper
public interface HhAddressMapper {

    int selectHhContactsListCount(HhContactsDTO hhContactsDTO);
    List<HhContactsDTO> selectHhContactsList(HhContactsDTO hhContactsDTO);

    int insertContactgroup(HhContactsGroupDTO hhContactsGroupDTO);

    int updateContactgroup(HhContactsGroupDTO hhContactsGroupDTO);

    List<HhContactsGroupDTO> selectContractGroup(HhContactsGroupDTO hhContactsGroupDTO);

    int insertContact(HhContactsDTO hhContactsDTO);
    int updateContact(HhContactsDTO hhContactsDTO);

}
