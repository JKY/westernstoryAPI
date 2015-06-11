package com.westernstory.api.service;

import com.google.common.io.Files;
import com.westernstory.api.config.Config;
import com.westernstory.api.dao.TicketDao;
import com.westernstory.api.model.TicketModel;
import com.westernstory.api.model.UserTicketModel;
import com.westernstory.api.util.Md5;
import com.westernstory.api.util.ServiceException;
import com.westernstory.api.util.WsUtil;
import net.glxn.qrgen.javase.QRCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

// Created by fedor on 15/5/13.
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class TicketService {
    @Autowired
    private TicketDao ticketDao = null;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    /**
     * 优惠券列表
     * @param start start
     * @param limit limit
     * @return List
     * @throws ServiceException
     */
    public Map<String, Object> list(String keyword, Integer start, Integer limit) throws ServiceException {
        try {
            Map<String, Object> map = new HashMap<String, Object>();

            List<TicketModel> list = ticketDao.list(keyword, start, limit);
            for (TicketModel model : list) {
                // 缩略图
                if (!WsUtil.isEmpty(model.getThumbnail())) {
                    model.setThumbnail(Config.URL_STATIC + model.getThumbnail());
                }
                model.setTotal(model.getTotal() - model.getSum());
            }
            map.put("items", list);
            map.put("count", ticketDao.count(keyword));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 我的优惠券列表
     * @param userId userId
     * @param start userId
     * @param limit limit
     * @return
     */
    public  Map<String, Object> getMyList(Long userId, Integer start, Integer limit) throws ServiceException {
        try {
            Map<String, Object> map = new HashMap<String, Object>();

            List<TicketModel> list = ticketDao.getMyList(userId, start, limit);
            for (TicketModel model : list) {
                // 缩略图
                if (!WsUtil.isEmpty(model.getThumbnail())) {
                    model.setThumbnail(Config.URL_STATIC + model.getThumbnail());
                }
            }
            map.put("items", list);
            map.put("count", ticketDao.countByUser(userId));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 获取ticket
     * @param ticketId ticketId
     * @param userId userId
     * @throws ServiceException
     */
    public void addUserTicket(Long ticketId, Long userId) throws ServiceException {
        try {
            UserTicketModel userTicket = ticketDao.getUserTicket(ticketId, userId);
            if (userTicket != null) {
                throw new ServiceException("您已经领取了此优惠券");
            }

            TicketModel ticket = ticketDao.getById(ticketId);
            Integer count = ticketDao.getCount(ticketId);
            if(count >= ticket.getTotal()) {
                throw new ServiceException("优惠券已领完");
            }

            String qrURL = Config.URL_ROOT + "ticket/scan?" +
                    "tid=" + ticketId +
                    "&uid=" + userId +
                    "&token=" + Md5.toMD5(ticketId+"#"+userId+"#"+Config.WEB_KEY);
            File qrcodeFile = QRCode.from(qrURL).file();
            byte[] qrcodeData = Files.toByteArray(qrcodeFile);

            // generate QRCode file
            String savePath = Config.PATH_UPLOAD + "/";
            String saveUrl = Config.FOLDER_UPLOAD + "/";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String ymd = sdf.format(new Date());
            savePath += ymd + "/";
            saveUrl += ymd + "/";
            File dirFile = new File(savePath);
            if (!dirFile.exists()) {
                boolean is = dirFile.mkdirs();
                if (!is) {
                    throw new ServiceException("创建dir失败");
                }
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String fileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + ".jpg";

            FileOutputStream fos = new FileOutputStream(new File(savePath, fileName));
            fos.write(qrcodeData, 0, qrcodeData.length);
            fos.flush();
            fos.close();

            // 新增领取
            UserTicketModel userTicketModel = new UserTicketModel();
            userTicketModel.setTotal(1);
            userTicketModel.setUserId(userId);
            userTicketModel.setTicketId(ticketId);
            userTicketModel.setIsUsed(false);
            userTicketModel.setQrCode(saveUrl + fileName);

            ticketDao.addUserTicket(userTicketModel);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 获取我的优惠券详情
     * @param id id
     * @return UserTicketModel
     * @throws ServiceException
     */
    public UserTicketModel getMyTicketDetail(Long id) throws ServiceException {
        try {
            UserTicketModel m = ticketDao.getMyTicketDetail(id);
            if (m == null) {
                throw new ServiceException("未找到对应的优惠券");
            }
            if (m.getQrCode() != null) {
                if (!WsUtil.isEmpty(m.getQrCode())) {
                    m.setQrCode(Config.URL_STATIC + m.getQrCode());
                }
            }
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 获取我的优惠券详情，通过userid、ticketid
     * @param userId userId
     * @param ticketId ticketId
     * @return UserTicketModel
     * @throws ServiceException
     */
    public UserTicketModel getMyTicketDetail(Long userId, Long ticketId) throws ServiceException {
        try {
            UserTicketModel m = ticketDao.getMyTicketDetailByUserIdTicketId(userId, ticketId);
            if (m == null) {
                throw new ServiceException("未找到对应的优惠券");
            }
            if (m.getQrCode() != null) {
                if (!WsUtil.isEmpty(m.getQrCode())) {
                    m.setQrCode(Config.URL_STATIC + m.getQrCode());
                }
            }
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 过userId、ticketId获取用户的优惠券，并且更新为已经使用
     * @param ticketId ticketId
     * @param userId userId
     * @throws ServiceException
     */
    public void doIdentify(Long ticketId, Long userId) throws ServiceException {
        try {
            ticketDao.updateUseTicket(userId, ticketId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }

    /**
     * 通过优惠券id获取密码
     * @param ticketId ticketId
     * @return password
     * @throws ServiceException
     */
    public String getTicketPassword(Long ticketId) throws ServiceException {
        try {
            return ticketDao.getTicketPassword(ticketId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException(WsUtil.getServiceExceptionMessage(e));
        }
    }
}
