package vn.azteam.tabasupport.core.object.model;

import vn.azteam.tabasupport.util.PropertiesParserUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * package vn.azteam.tabasupport.core.object.model
 * created 12/27/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public class BasePagination {
	private long total;
	private int limit;
	private int offset;
	private List<? extends BaseModel> objects;

	public BasePagination(long total, int limit, int offset, List<? extends BaseModel> objects) {
		this.total = total;
		this.limit = limit;
		this.offset = offset;
		this.objects = objects;
	}

	public BasePagination(List<? extends BaseModel> objects) {
		this.objects = objects;
		this.total = objects.size();
		this.limit = PropertiesParserUtil.getIntProperty("default.paging.limit");
		this.offset = 0;
	}

	public long getTotal() {
		return total;
	}

	public BasePagination setTotal(long total) {
		this.total = total;
		return this;
	}

	public int getLimit() {
		return limit;
	}

	public BasePagination setLimit(int limit) {
		this.limit = limit;
		return this;
	}

	public int getOffset() {
		return offset;
	}

	public BasePagination setOffset(int offset) {
		this.offset = offset;
		return this;
	}

	public List<? extends BaseModel> getObjects() {
		if (offset < objects.size()) {
			if (offset + limit > objects.size()) {
				return objects.subList(offset, objects.size());
			}
			return objects.subList(offset, offset + limit);
		}
		return new ArrayList<>();
	}

	public BasePagination setObjects(List<? extends BaseModel> objects) {
		this.objects = objects;
		return this;
	}

	@Override
	public String toString() {
		return "BasePagination{" +
				"total=" + total +
				", limit=" + limit +
				", offset=" + offset +
				", objects=" + objects +
				'}';
	}
}
