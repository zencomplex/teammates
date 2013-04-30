package teammates.storage.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Extension;

import teammates.common.Common;

import com.google.gson.annotations.SerializedName;

/**
 * Evaluation represents an evaluation/feedback session.
 */
@PersistenceCapable
public class Evaluation {

	private static Logger log = Common.getLogger();

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	@SerializedName("course_id")
	private String courseID;

	@Persistent
	@SerializedName("name")
	private String name;

	@Persistent
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	@SerializedName("instr")
	private String instructions;

	@Persistent
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	@SerializedName("start_time")
	private Date startTime;

	@Persistent
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	@SerializedName("end_time")
	private Date endTime;

	@Persistent
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	@SerializedName("timezone")
	private double timeZone;

	/**
	 * The time (in minutes) the evaluation will stay open after the deadline.
	 */
	@Persistent
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	@SerializedName("grace")
	private int gracePeriod;

	/**
	 * Whether the evaluation allows peer-to-peer feedback.
	 */
	@Persistent
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	@SerializedName("comments_on")
	private boolean commentsEnabled;

	@Persistent
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private boolean published = false;

	/**
	 * Indicates if the evaluation has been 'activated'.
	 * Activated means anything that needs to be done at the point of opening 
	 * an evaluation (e.g., sending emails) has been done.
	 */
	@Persistent
	@Extension(vendorName = "datanucleus", key = "gae.unindexed", value = "true")
	private boolean activated = false;

	/**
	 * Constructs an Evaluation object.
	 * 
	 * @param courseId
	 * @param evaluationName
	 * @param instructions
	 * @param commentsEnabled
	 * @param start
	 * @param deadline
	 * @param gracePeriod
	 */
	public Evaluation(String courseId, String evaluationName,
			String instructions, boolean commentsEnabled, Date start,
			Date deadline, double timeZone, int gracePeriod) {
		this.setCourseId(courseId);
		this.setName(evaluationName);
		this.setInstructions(instructions);
		this.setCommentsEnabled(commentsEnabled);
		this.setStart(start);
		this.setDeadline(deadline);
		this.setGracePeriod(gracePeriod);
		this.setPublished(false);
		this.setTimeZone(timeZone);
	}

	public String getCourseId() {
		return courseID;
	}

	public void setCourseId(String courseId) {
		this.courseID = courseId.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String evaluationName) {
		this.name = evaluationName.trim();
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions.trim();
	}

	public Date getStart() {
		return startTime;
	}

	public void setStart(Date start) {
		this.startTime = start;
	}

	public Date getDeadline() {
		return endTime;
	}

	public void setDeadline(Date deadline) {
		this.endTime = deadline;
	}

	public int getGracePeriod() {
		return gracePeriod;
	}

	public void setGracePeriod(int gracePeriod) {
		this.gracePeriod = gracePeriod;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public boolean isCommentsEnabled() {
		return commentsEnabled;
	}

	public void setCommentsEnabled(boolean commentsEnabled) {
		this.commentsEnabled = commentsEnabled;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public double getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(double timeZone) {
		this.timeZone = timeZone;
	}

	/**
	 * @return True if the current time is between start time and deadline, but
	 *         the evaluation has not been activated yet.
	 */
	public boolean isReadyToActivate() {
		Calendar currentTimeInUserTimeZone = Common.convertToUserTimeZone(
				Calendar.getInstance(), timeZone);

		Calendar evalStartTime = Calendar.getInstance();
		evalStartTime.setTime(startTime);

		log.fine("current:"
				+ Common.calendarToString(currentTimeInUserTimeZone)
				+ "|start:" + Common.calendarToString(evalStartTime));

		if (currentTimeInUserTimeZone.before(evalStartTime)) {
			return false;
		} else {
			return (!activated);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("courseID: " + courseID);
		sb.append("\nname:" + name);
		sb.append("\ninstruction: " + instructions);
		sb.append("\nstarttime: " + startTime);
		sb.append("\nendtime: " + endTime);
		return sb.toString();
	}

}
