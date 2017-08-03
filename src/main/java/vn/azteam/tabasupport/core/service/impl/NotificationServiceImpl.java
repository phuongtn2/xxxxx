package vn.azteam.tabasupport.core.service.impl;

import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import vn.azteam.tabasupport.core.object.dao.PushNotificationDao;
import vn.azteam.tabasupport.core.object.model.*;
import vn.azteam.tabasupport.core.service.CompanyService;
import vn.azteam.tabasupport.core.service.ConfigService;
import vn.azteam.tabasupport.core.service.NotificationService;
import vn.azteam.tabasupport.modules.cultivation.object.model.CultivationHarvest;
import vn.azteam.tabasupport.modules.cultivation.object.model.FieldPlot;
import vn.azteam.tabasupport.modules.cultivation.service.CultivationService;
import vn.azteam.tabasupport.modules.nursery.object.model.Nursery;
import vn.azteam.tabasupport.modules.nursery.service.NurseryService;
import vn.azteam.tabasupport.plugin.push.AndroidPushNotificationService;
import vn.azteam.tabasupport.plugin.push.IOSPushNotificationService;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * package vn.azteam.tabasupport.core.service.impl
 * created 12/30/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see BaseServiceImpl
 * @see NotificationService
 * @since 1.0.0
 */
@Service
public class NotificationServiceImpl extends BaseServiceImpl implements NotificationService {

	@Autowired
	private ConfigService configService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private PushNotificationDao pushNotificationDao;
	@Autowired
	private CultivationService cultivationService;
	@Autowired
	private NurseryService nurseryService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendMail(String mailDomain, String username, String password, String mailFrom, String mailTo, String subject, String content) {

		final MailConfig config = configService.getMailConfig(mailDomain);

		if (config == null) throw new NullPointerException(String.format("No mail config for domain %s", mailDomain));

		final JavaMailSenderImpl mailSender = config.getMailSender(username, password);

		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom(mailFrom);
			helper.setTo(mailTo);
			helper.setSubject(subject);
			helper.setText(content, true);
		} catch (MessagingException e) {
			logger.error(e);
		}

		mailSender.send(message);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendMailByCompanyNotifyMail(long companyId, String mailTo, String subject, String content) {
		final Company company = companyService.getCompanyById(companyId);
		if (company == null) throw new NullPointerException("No company found.");
		final String mailDomain = company.getNotifyMail().substring(company.getNotifyMail().lastIndexOf('@') + 1);
		sendMail(mailDomain, company.getNotifyMail(), company.getNotifyMailPassword(), company.getNotifyMail(), mailTo, subject, content);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendNotifyMailToAccount(Account account, String subject, String content) {
		sendMailByCompanyNotifyMail(account.getCompanyId(), account.getEmail(), subject, content);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pushNotification() {
		if (getAllNotification() != null) {
			final AndroidPushNotificationService service = ApplicationContextProvider.getApplicationContext().getBean(AndroidPushNotificationService.class);
			IOSPushNotificationService iosPushNotificationService = new IOSPushNotificationService();
			for (PushNotification pushNotification : getAllNotification()) {
				if (pushNotification.getDeviceType().equalsIgnoreCase("ANDROID")) {
					boolean isPushed = service.pushNotification(pushNotification);
					if (isPushed) {
						pushNotificationDao.deletePushNotification(pushNotification);
					}
				} else if (pushNotification.getDeviceType().equalsIgnoreCase("IOS")) {
					//Push for IOS
					iosPushNotificationService.pushIos(pushNotification.getPushKey(), pushNotification.getTitlePush());
					pushNotificationDao.deletePushNotification(pushNotification);
				} else {
					//Don't need push notification
				}
			}
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertAllRecordPushNeededToDay() {
		List<RegistrationPush> registrationPushes = pushNotificationDao.findAllRegistrationPush();

		for (RegistrationPush registrationPush : registrationPushes) {
			try {
				//Get nursery for push notification
				List<Nursery> nurseries = nurseryService.getAllNurseryByRegistrationId(registrationPush.getRegistrationId());
				insertAllNurseryForPush(registrationPush, nurseries);

				// Get field plot for push notification
				List<FieldPlot> fieldPlots = cultivationService.getAllFieldPlotByRegistrationId(registrationPush.getRegistrationId());
				insertCultivationForPush(registrationPush, fieldPlots);

				List<CultivationHarvest> harvests = cultivationService.getAllHarvestByRegistrationId(registrationPush.getRegistrationId());
				insertAllHarvestForPush(registrationPush, harvests);

			} catch (NullPointerException | NoSuchElementException | BindingException e) {
				logger.error(e);
			}
		}
	}

	private void insertAllNurseryForPush(RegistrationPush registrationPush, List<Nursery> nurseries) throws NullPointerException, NoSuchElementException, BindingException {
		Date today = new Date();
		long toDayMillisecond = today.getTime();
		long oneDayMillisecond = (1000 * 60 * 60 * 24);
		for (Nursery nursery : nurseries) {
			long seedingDayMilliseconds = nursery.getSeedingDate().getTime();
			long pushDay = (toDayMillisecond - seedingDayMilliseconds);
			String farmerName = registrationPush.getFarmerName();
			PushNotification pushNotification = new PushNotification();
			pushNotification.setTitlePush("Taba Support")
					.setPushKey(registrationPush.getPushKey())
					.setDeviceType(registrationPush.getDeviceType())
					.setNurseryId(nursery.getId())
					.setCultivationId(0)
					.setRegistrationId(registrationPush.getRegistrationId())
					.setFarmerId(registrationPush.getFarmerId())
					.setResponsibilityEmployeeId(registrationPush.getEmployeeId())
					.getErrors();

			//Push in 7 day
			if (7 * oneDayMillisecond <= pushDay && pushDay < 8 * oneDayMillisecond) {
				pushNotification.setBodyPush(farmerName + " cần phun thuốc phòng bệnh lần 1");
				pushNotificationDao.insertPushNotification(pushNotification);
			}
			if (10 * oneDayMillisecond <= pushDay && pushDay < 11 * oneDayMillisecond) {
				pushNotification.setBodyPush(farmerName + " cần phun thuốc trị sâu chích hút lần 1 phòng bệnh vi rút");
				pushNotificationDao.insertPushNotification(pushNotification);
			}
			if (17 * oneDayMillisecond <= pushDay && pushDay < 18 * oneDayMillisecond) {
				pushNotification.setBodyPush(farmerName + " cần phun thuốc phòng bệnh lần 2");
				pushNotificationDao.insertPushNotification(pushNotification);
			}
			if (27 * oneDayMillisecond <= pushDay && pushDay < 28 * oneDayMillisecond) {
				pushNotification.setBodyPush(farmerName + " cần phun thuốc trị sâu chích hút lần 2 phòng bệnh vi rút");
				pushNotificationDao.insertPushNotification(pushNotification);
			}
			if (30 * oneDayMillisecond <= pushDay && pushDay < 31 * oneDayMillisecond) {
				pushNotification.setBodyPush(farmerName + " cần phun thuốc phòng bệnh lần 3");
				pushNotificationDao.insertPushNotification(pushNotification);
			}
			if (42 * oneDayMillisecond <= pushDay && pushDay < 43 * oneDayMillisecond) {
				pushNotification.setBodyPush(farmerName + " cần phun thuốc trị sâu chích hút lần 3 phòng bệnh vi rút");
				pushNotificationDao.insertPushNotification(pushNotification);
			}
		}
	}

	private void insertCultivationForPush(RegistrationPush registrationPush, List<FieldPlot> fieldPlots) throws NullPointerException, NoSuchElementException, BindingException {
		Date today = new Date();
		long toDayMillisecond = today.getTime();
		long oneDayMillisecond = (1000 * 60 * 60 * 24);

		for (FieldPlot fieldPlot : fieldPlots) {
			long seedingDayMilliseconds = fieldPlot.getCultivationDate().getTime();
			long pushDay = (toDayMillisecond - seedingDayMilliseconds);
			String farmerName = registrationPush.getFarmerName();
			PushNotification pushNotification = new PushNotification();
			pushNotification.setTitlePush("Taba Support")
					.setPushKey(registrationPush.getPushKey())
					.setDeviceType(registrationPush.getDeviceType())
					.setNurseryId(0)
					.setCultivationId(fieldPlot.getCultivationId())
					.setRegistrationId(registrationPush.getRegistrationId())
					.setFarmerId(registrationPush.getFarmerId())
					.setResponsibilityEmployeeId(registrationPush.getEmployeeId())
					.getErrors();

			//Push in 10 day
			if (10 * oneDayMillisecond <= pushDay && pushDay < 11 * oneDayMillisecond) {
				pushNotification.setBodyPush(farmerName + " cần phun thuốc trị sâu chích hút lần 1 phòng bệnh vi rút");
				pushNotificationDao.insertPushNotification(pushNotification);
			}
			if (15 * oneDayMillisecond <= pushDay && pushDay < 16 * oneDayMillisecond) {
				pushNotification.setBodyPush(farmerName + " cần phun thuốc phòng bệnh lần 1");
				pushNotificationDao.insertPushNotification(pushNotification);
			}
			if (21 * oneDayMillisecond <= pushDay && pushDay < 22 * oneDayMillisecond) {
				pushNotification.setBodyPush(farmerName + " cần phun thuốc trị sâu chích hút lần 2 phòng bệnh vi rút");
				pushNotificationDao.insertPushNotification(pushNotification);
			}
			if (57 * oneDayMillisecond <= pushDay && pushDay < 58 * oneDayMillisecond) {
				pushNotification.setBodyPush(farmerName + " cần loại bỏ triệt để hoa và chồi trên đồng");
				pushNotificationDao.insertPushNotification(pushNotification);
			}
			if (77 * oneDayMillisecond <= pushDay && pushDay < 78 * oneDayMillisecond) {
				pushNotification.setBodyPush(farmerName + " hoa chồi đã tái xuất hiện cần loại bỏ gấp");
				pushNotificationDao.insertPushNotification(pushNotification);
			}

		}
	}

	private void insertAllHarvestForPush(RegistrationPush registrationPush, List<CultivationHarvest> harvests) throws NullPointerException, NoSuchElementException, BindingException {
		Date today = new Date();
		long toDayMillisecond = today.getTime();
		long oneDayMillisecond = (1000 * 60 * 60 * 24);
		for (CultivationHarvest harvest : harvests) {
			if (harvest.getHarvestDate() != null && !harvest.isHarvestLast()) {
				long seedingDayMilliseconds = harvest.getHarvestDate().getTime();
				long pushDay = (toDayMillisecond - seedingDayMilliseconds);
				String farmerName = registrationPush.getFarmerName();
				PushNotification pushNotification = new PushNotification();
				pushNotification.setTitlePush("Taba Support")
						.setPushKey(registrationPush.getPushKey())
						.setDeviceType(registrationPush.getDeviceType())
						.setNurseryId(0)
						.setCultivationId(harvest.getCultivationId())
						.setRegistrationId(registrationPush.getRegistrationId())
						.setFarmerId(registrationPush.getFarmerId())
						.setResponsibilityEmployeeId(registrationPush.getEmployeeId())
						.getErrors();

				//Push in 7 day
				if (5 * oneDayMillisecond <= pushDay && pushDay < 6 * oneDayMillisecond) {
					pushNotification.setBodyPush(farmerName + " lần thu hoạch tiếp chỉ bẻ lá đúng chín");
					pushNotificationDao.insertPushNotification(pushNotification);
				}
				if (10 * oneDayMillisecond <= pushDay && pushDay < 11 * oneDayMillisecond) {
					pushNotification.setBodyPush(farmerName + " vẫn còn nhiều lá xanh cần thu hoạch lá đúng chín");
					pushNotificationDao.insertPushNotification(pushNotification);
				}
				if (15 * oneDayMillisecond <= pushDay && pushDay < 16 * oneDayMillisecond) {
					pushNotification.setBodyPush(farmerName + " lần sấy trước lá chết xanh nhiều, cần thu hoạch lá đúng chín");
					pushNotificationDao.insertPushNotification(pushNotification);
				}
				if (20 * oneDayMillisecond <= pushDay && pushDay < 21 * oneDayMillisecond) {
					pushNotification.setBodyPush(farmerName + " vẫn còn nhiều lá xanh cần thu hoạch lá đúng chín");
					pushNotificationDao.insertPushNotification(pushNotification);
				}
				if (25 * oneDayMillisecond <= pushDay && pushDay < 26 * oneDayMillisecond) {
					pushNotification.setBodyPush(farmerName + " tuyệt đối chỉ thu hoạch lá đúng chín đợt này");
					pushNotificationDao.insertPushNotification(pushNotification);
				}
				if (30 * oneDayMillisecond <= pushDay && pushDay < 31 * oneDayMillisecond) {
					pushNotification.setBodyPush(farmerName + " tuyệt đối chỉ thu hoạch lá đúng chín đợt này");
					pushNotificationDao.insertPushNotification(pushNotification);
				}
			}
		}
	}

	private List<PushNotification> getAllNotification() {
		return pushNotificationDao.findAll();
	}
}
