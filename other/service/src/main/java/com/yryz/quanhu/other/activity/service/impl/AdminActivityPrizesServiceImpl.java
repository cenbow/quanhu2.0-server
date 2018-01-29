package com.yryz.quanhu.other.activity.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yryz.common.response.PageList;
import com.yryz.quanhu.other.activity.dao.ActivityUserPrizesDao;
import com.yryz.quanhu.other.activity.service.AdminActivityPrizesService;
import com.yryz.quanhu.other.activity.vo.AdminInActivityUserPrizes;
import com.yryz.quanhu.other.activity.vo.AdminOutActivityUsrePrizes;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AdminActivityPrizesServiceImpl implements AdminActivityPrizesService {

	@Autowired
	ActivityUserPrizesDao	ActivityUserPrizesDao;
	/*@Autowired
	CustAPI custAPI;
	@Autowired
 	UserSearchAPI	userSearchAPI;*/
	
	/**
	 * 奖品列表
	 * @param dto
	 * @return
	 */
	@Override
	public PageList<AdminOutActivityUsrePrizes> listPrizes(AdminInActivityUserPrizes dto) {
		
//		PageHelper.startPage(dto.getPageNo(), dto.getPageSize());
//		Logger.println("奖品列表入参:--------");
		if(true==dto.getPage()){
			Integer	pageNo=(dto.getPageNo()-1)*dto.getPageSize();
			dto.setPageNo(NumberUtils.toInt(pageNo.toString()));
		}
		List<AdminOutActivityUsrePrizes> list=ActivityUserPrizesDao.listPrizesByConditionAndPage(dto);
		if(CollectionUtils.isEmpty(list)){
			return new PageList(dto.getPageNo(), dto.getPageSize(), list, 0L);
		}
		for (AdminOutActivityUsrePrizes outActivityPrizes : list) {
			/*TODO CustInfo custInfo = custAPI.getCustInfo(outActivityPrizes.getCreateUserId());
			if(null!=custInfo){
				outActivityPrizes.setCustName(custInfo.getCustNname()!=null?custInfo.getCustNname():"");
				outActivityPrizes.setCustPhone(custInfo.getCustPhone()!=null?custInfo.getCustPhone():"");
			}*/
		}
		Integer count = ActivityUserPrizesDao.listPrizesByConditionAndPageCount(dto);
		return new PageList(dto.getPageNo(), dto.getPageSize(), list, (long)count);
	}
	
	/**
	 * 批量修改奖品已使用
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateBatchUsed(Long[] ids) throws Exception {
//		Logger.println("批量修改奖品已使用入参:ids="+ids);
		if(0==ids.length){
			return	0;
		}
		return ActivityUserPrizesDao.updateBatchUsed(ids, (byte) 2);//2已使用
	}

	
	 /**
	   * 读取Excel里面唯一编码
	   * @param wb
	   * @return
	   */
	/*@Override
	public List<String> readExcelValue(Workbook wb){
	      //得到第一个shell  
	       Sheet sheet=wb.getSheetAt(0);

	      //得到Excel的行数
	       int	totalRows=sheet.getPhysicalNumberOfRows();
	       
	      //得到Excel的列数(前提是有行数)
	       int	totalCells=0;
	       if(totalRows>=1 && sheet.getRow(0) != null){
	            totalCells=sheet.getRow(0).getPhysicalNumberOfCells();
	       }
	       
	       List<String> onlyCodeList=new ArrayList<String>();
	       String onlyCode;            
	      //循环Excel行数,从第二行开始。标题不入库
	       for(int r=1;r<totalRows;r++){
	           Row row = sheet.getRow(r);
	           if (row == null) continue;
	           onlyCode = new String();
	           
	           //循环Excel的列
	           for(int c = 0; c <totalCells; c++){    
	               Cell cell = row.getCell(c);
	               if (null != cell){
	                   if(c==0){//第一列不读
	                   }else if(c==1){//客户名称
//	                       customer.setcName(cell.getStringCellValue());
	                   }else if(c==2){
//	                       onlyCode=cell.getStringCellValue()+"2";//唯一编码
	                   }else if(c==3){
	                       onlyCode=StringUtils.isNotBlank(cell.getStringCellValue())?cell.getStringCellValue():"";//唯一编码
	                   }else if(c==4){
//	                       customer.setSource(cell.getStringCellValue());//客户来源
	                   }else if(c==5){
//	                	   onlyCode=cell.getStringCellValue();//唯一编码
	                   }
	               }
	           }
	           //添加客户
	           onlyCodeList.add(onlyCode);
	       }
	       return onlyCodeList;
	  }
*/

}
