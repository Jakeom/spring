package com.fw.core.mapper.db1.batch;

import com.fw.core.dto.batch.PushHistBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/***
 * @author wsh
 */
@Mapper
public interface BatchPushMapper {

	List<PushHistBean> selectTargetPushHistList(PushHistBean bean);

    int updatePushHist(PushHistBean bean);
    void updatePushHistPhoneTokenANDPhoneTypeIsNull();

    int insertPushHist(PushHistBean bean);

    int insertApPositionAlert(PushHistBean bean);
    

	
}
