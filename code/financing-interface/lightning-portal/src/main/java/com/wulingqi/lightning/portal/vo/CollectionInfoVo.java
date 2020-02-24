package com.wulingqi.lightning.portal.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("公司收款信息返回实体")
public class CollectionInfoVo {
	
	@ApiModelProperty(value = "收款ID")
	private String collectionId;
	
	@ApiModelProperty(value = "银行名称")
	private String bankName;
	
	@ApiModelProperty(value = "银行账户名")
	private String bankAccount;
	
	@ApiModelProperty(value = "银行卡卡号")
	private String bankCardNo;
	
	@ApiModelProperty(value = "收款二维码")
	private String qrCode;
	
	@ApiModelProperty(value = "USDT地址")
	private String usdtAddress;

}
