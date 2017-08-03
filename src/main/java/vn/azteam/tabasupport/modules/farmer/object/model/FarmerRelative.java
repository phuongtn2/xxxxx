package vn.azteam.tabasupport.modules.farmer.object.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.BeansException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.object.model.Account;
import vn.azteam.tabasupport.core.object.model.User;
import vn.azteam.tabasupport.enums.DataBaseScenario;
import vn.azteam.tabasupport.modules.farmer.service.FarmerService;
import vn.azteam.tabasupport.modules.farmer.service.impl.FarmerServiceImpl;
import vn.azteam.tabasupport.provider.ApplicationContextProvider;
import vn.azteam.tabasupport.util.PropertiesParserUtil;

/**
 * package vn.azteam.tabasupport.modules.farmer.object.model
 * created 3/10/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see User
 * @since 1.0.0
 */
@JsonIgnoreProperties({"province", "district", "ward"})
public class FarmerRelative extends User {
	private long id;
	private long uId;
	private long farmerId;
	private String relationship;
	private int delFlg;

	public long getId() {
		return id;
	}

	public FarmerRelative setId(long id) {
		this.id = id;
		return this;
	}

	public long getuId() {
		return uId;
	}

	public FarmerRelative setuId(long uId) {
		this.uId = uId;
		return this;
	}

	public long getFarmerId() {
		return farmerId;
	}

	public FarmerRelative setFarmerId(long farmerId) {
		this.farmerId = farmerId;
		return this;
	}

	public String getRelationship() {
		return relationship;
	}

	public FarmerRelative setRelationship(String relationship) {
		this.relationship = relationship;
		return this;
	}

	public int getDelFlg() {
		return delFlg;
	}

	public FarmerRelative setDelFlg(int delFlg) {
		this.delFlg = delFlg;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return getClass().equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		super.validate(obj, errors);
		FarmerRelative target = (FarmerRelative) obj;

		// check empty
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "relationship",
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.code", "FarmerRelative", "relationship"),
				PropertiesParserUtil.parsePropertyByFormat("validation.model.default.msg.empty", "relationship"));
	}

	@Override
	public String toString() {
		return "FarmerRelative{" +
				"id=" + id +
				", uId=" + uId +
				", farmerId=" + farmerId +
				", relationship='" + relationship + '\'' +
				", delFlg=" + delFlg +
				'}';
	}

	/**
	 * Save data to database.
	 *
	 * @param account {@link Account} obj.
	 * @throws NullPointerException
	 * @throws BeansException
	 * @throws ValidationException
	 */
	public void save(Account account) throws NullPointerException, BeansException, ValidationException {
		final FarmerService service = ApplicationContextProvider.getApplicationContext().getBean(FarmerServiceImpl.class);

		switch (getDataScenario()) {
			case INSERT:
				updateId = createId = account.getId();
				insert(service, account);
				break;
			case UPDATE:
				updateId = account.getId();
				update(service, account);
				break;
		}
	}

	private void update(FarmerService service, Account account) throws ValidationException {
		if (!getErrors().isEmpty())
			throw new ValidationException(getErrors().get(0).getCode(), getErrors().get(0).getDefaultMessage());

		service.updateRelative(this, true);
	}

	private void insert(FarmerService service, Account account) throws ValidationException {
		if (!getErrors().isEmpty())
			throw new ValidationException("cultivation_error_code", getErrors().get(0).getDefaultMessage());

		service.createRelative(this, true);
	}

	private DataBaseScenario getDataScenario() {
		return getId() < 1 ? DataBaseScenario.INSERT : DataBaseScenario.UPDATE;
	}
}
