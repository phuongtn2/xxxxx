package vn.azteam.tabasupport.modules.task.object.model;

import org.springframework.validation.Errors;
import vn.azteam.tabasupport.core.object.model.BaseModel;

import java.util.Date;

/**
 * Package vn.azteam.tabasupport.modules.task.object.model
 * Created 2/14/2017
 *
 * @author daivp
 * @version 1.0.0
 * @link http://azteam.vn
 * @see
 * @since 1.0.0
 */
public class TaskReport extends BaseModel {

	private long id;
	private long taskId;
	private long registrationId;
	private long actionId;
	private String phase;
	private String licensePlate;
	private int startSpeedometer;
	private int endSpeedometer;
	private Date moveStartDate;
	private Date moveDueDate;
	private String explanation;

	public long getId() {
		return id;
	}

	public TaskReport setId(long id) {
		this.id = id;
		return this;
	}

	public long getTaskId() {
		return taskId;
	}

	public TaskReport setTaskId(long taskId) {
		this.taskId = taskId;
		return this;
	}

	public long getRegistrationId() {
		return registrationId;
	}

	public TaskReport setRegistrationId(long registrationId) {
		this.registrationId = registrationId;
		return this;
	}

	public long getActionId() {
		return actionId;
	}

	public TaskReport setActionId(long actionId) {
		this.actionId = actionId;
		return this;
	}

	public String getPhase() {
		return phase;
	}

	public TaskReport setPhase(String phase) {
		this.phase = phase;
		return this;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public TaskReport setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
		return this;
	}

	public int getStartSpeedometer() {
		return startSpeedometer;
	}

	public TaskReport setStartSpeedometer(int startSpeedometer) {
		this.startSpeedometer = startSpeedometer;
		return this;
	}

	public int getEndSpeedometer() {
		return endSpeedometer;
	}

	public TaskReport setEndSpeedometer(int endSpeedometer) {
		this.endSpeedometer = endSpeedometer;
		return this;
	}

	public Date getMoveStartDate() {
		return moveStartDate;
	}

	public TaskReport setMoveStartDate(Date moveStartDate) {
		this.moveStartDate = moveStartDate;
		return this;
	}

	public Date getMoveDueDate() {
		return moveDueDate;
	}

	public TaskReport setMoveDueDate(Date moveDueDate) {
		this.moveDueDate = moveDueDate;
		return this;
	}

	public String getExplanation() {
		return explanation;
	}

	public TaskReport setExplanation(String explanation) {
		this.explanation = explanation;
		return this;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return getClass().equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

	}
}
