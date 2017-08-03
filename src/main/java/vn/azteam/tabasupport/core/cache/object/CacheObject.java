package vn.azteam.tabasupport.core.cache.object;

/**
 * package vn.azteam.tabasupport.core.cache.object
 * created 12/27/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
public class CacheObject {
	private Object object;
	private long cacheAt;

	public CacheObject(Object object, long cacheAt) {
		this.object = object;
		this.cacheAt = cacheAt;
	}

	public Object getObject() {
		return object;
	}

	public CacheObject setObject(Object object) {
		this.object = object;
		return this;
	}

	public long getCacheAt() {
		return cacheAt;
	}

	public CacheObject setCacheAt(long cacheAt) {
		this.cacheAt = cacheAt;
		return this;
	}

	@Override
	public String toString() {
		return "CacheObject{" +
				"object=" + object +
				", cacheAt=" + cacheAt +
				'}';
	}
}
