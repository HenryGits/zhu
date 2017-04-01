package vrbaidu.top.redisCache.dao;

import org.springframework.stereotype.Repository;
import vrbaidu.top.redisCache.bean.*;
import java.util.List;
import java.util.Map;

@Repository
public interface CommonMapper {

	int saveArea(ConsultConfigArea area);
	
	int deleteArea(Map param);
	
	int deleteAreaAll();
	
	int updateArea(ConsultConfigArea area);
	
	List<ConsultConfigArea> queryAreaByAreaCode(Map param);
	
	List<ConsultRecord> queryConsultRecords(Map param);
	
	List<Map> queryUserByPsptId(Map param);
	
	int saveUser(ConsultIdCardInfo record);
	
	int saveRecord(ConsultRecord record);
	
	int saveRecordCount(ConsultRecordCount recordCount);
	
	List<ConsultRecord> queryRecords(Map param);
	
	List<ConsultRecord> queryRecordshaveH(Map param);
	
	List<ConsultContent> queryContent(Map param);
	
	int updateRecords(Map param);
	
	List<ConsultRecordCount> queryRecordCount(Map param);
	
	int updateRecordCount(Map param);
	
	List<ConsultConfigArea> qryArea(Map param);
	
	List<ConsultContract> qryContracts(Map param);
	
	int saveContracts(List<ConsultContract> contracts);
}
