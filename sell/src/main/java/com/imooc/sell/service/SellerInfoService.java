package com.imooc.sell.service;

import com.imooc.sell.dataobject.SellerInfo;

public interface SellerInfoService {

    /**
     * 通过openid查询卖家用户信息
     *
     * @param openid
     * @return
     */
    SellerInfo findSellerInfoByOpenid(String openid);

    /**
     * 通过username和password查询卖家商户信息
     *
     * @param username
     * @param password
     * @return
     */
    SellerInfo findByUsernameAndPassword(String username, String password);
}
